package com.proline.rcp.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.proline.models.ProlineModelBean;
import com.proline.models.ProlineTagType;
import com.proline.models.TreeViewerModel;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.util.ProlineLogger;
import com.proline.util.parsers.XMLDOMParser;

public class NavigationView extends ViewPart {

	private Object data;
    public String parent="";

	public static final String ID = "prolinercp.navigationView";
	private TreeViewer viewer;

	class TreeObject {

		private String type;
		private String name;
		private TreeParent parent;

		public TreeObject(String name) { 
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setParent(TreeParent parent) {
			this.parent = parent;
		}

		public TreeParent getParent() {
			return parent;
		}

		public String toString() {
			return getName();
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	class TreeParent extends TreeObject {

		private ArrayList<TreeObject> children;

		public TreeParent(String name) {
			super(name);
			children = new ArrayList<TreeObject>();
		}

		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}

		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}

		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(new TreeObject[children
			                                                      .size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}
	}

	class ViewContentProvider implements IStructuredContentProvider,
	ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
			return false;
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(imageKey);
		}
	}

	public TreeParent populateTree(ProlineModelBean pModelBean) {
		TreeParent parent = null;
		if (pModelBean.getTagType() == ProlineTagType.PARENT) {
			parent = new TreeParent(pModelBean.getTagName());
		}
		polulateInnerTree(pModelBean, parent);

		return parent;
	}

	public void polulateInnerTree(ProlineModelBean pModelBean, TreeParent parent) {
		TreeObject to = null;
		TreeParent parent1 = null;
		List<ProlineModelBean> pModelList = pModelBean.getChildren();
		for (int i = 0; i < pModelList.size(); i++) {
			ProlineModelBean pModelBean1 = pModelList.get(i);
			if (pModelBean1.getTagType() == ProlineTagType.PARENT) {
				parent1 = new TreeParent(pModelBean1.getTagName());
				parent.addChild(parent1);
			} else {
				// to = new TreeObject(pModelBean1.getTagName());
				to = new TreeObject(pModelBean1.getTagValue());
				to.setType("leaf");
				parent.addChild(to);
			}
			polulateInnerTree(pModelBean1, parent1);
		}
	}

	/**
	 * We will set up a model to initialize tree hierarchy. In real code, you
	 * will connect to a real model and expose its hierarchy.
	 */
	private TreeObject createProlineModel() {
		TreeParent root = null;
		try {
			XMLDOMParser parser = new XMLDOMParser();
			ProlineModelBean pModelBean = parser
					.getProlineModelBeanList(FileConstants.PROLINE);
			TreeParent parent = populateTree(pModelBean);
			root = new TreeParent("");
			root.addChild(parent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return root;
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		// viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL |
		// SWT.V_SCROLL | SWT.BORDER);
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(createProlineModel());
		dragAndDropTree();
		addDoubleClickListener();
	}

	private void addDoubleClickListener() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Date startTime=new Date();
				System.out.println("The query starting time is @ Navigation :"+startTime.getTime());
				ProlineLogger.logInfo("The query starting time is @ Navigation :"+startTime.getTime());
				System.out.println("You double clicked the tree @ Navigation ....");
				IStructuredSelection isSelection = (IStructuredSelection) event
						.getSelection();
				Object object = isSelection.getFirstElement();
				
				System.out.println("NV --> Object = "+object);
				if (object instanceof TreeObject) {
					TreeObject to = (TreeObject) object;
					System.out.println("NV --> to = "+to);
					
					if (to.getType() != null && to.getType().equals("leaf")) {
						System.out.println("to.gettype() = "+to.getType());
						System.out.println("to.getParent() = "+to.getParent().getParent());
						parent=to.getParent().getParent().toString();
						TreeViewerModel model = new TreeViewerModel();
						model.setChildTreeName(to.getName());
						model.setTreeViewer(viewer);
						model.setLevelNo(1);
						data = model;
						setData(model);						
						System.out.println("the data is in navigationView Class :"+model.getTreeViewer().getData(getTitle()));
						OpenViewAction ova = new OpenViewAction(getSite().getWorkbenchWindow(), to.getName(), View.ID);
						final String title=to.getName()+"(1)";
						System.out.println(title);
						ova.run(title);	
						Date endTime=new Date();
						long duration  = startTime.getTime() - endTime.getTime();
						
						long diffInMilliSeconds =TimeUnit.MILLISECONDS.toMillis(duration);
						long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
						long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
						long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
						ProlineLogger.logInfo("The qury executing taking time is :"+diffInMinutes+"Minutes"+diffInSeconds+"Seconds"+diffInMilliSeconds+"MilliSeconds");
						
						System.out.println("The qury executing taking time is :"+diffInMinutes+"Minutes"+diffInSeconds+"Seconds");
					}
				}//~~~
			}
		});
	}

	private void dragAndDropTree() {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { TextTransfer.getInstance() };
		DragSource source = new DragSource(viewer.getTree(), operations);
		source.setTransfer(transferTypes);
		source.addDragListener(new DragSourceListener() {

			@Override
			public void dragStart(DragSourceEvent event) {
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				IStructuredSelection isSelection = (IStructuredSelection) viewer
						.getSelection();
				Object object = isSelection.getFirstElement();
				
				
//				if (object instanceof TreeObject) {
//					TreeObject to = (TreeObject) object;
//					if (to.getType() != null && to.getType().equals("leaf")) {
//						TreeViewerModel model = new TreeViewerModel();
//						model.setChildTreeName(to.getName());
//						model.setTreeViewer(viewer);
//						model.setLevelNo(1);
//						data = model;
//						setData(model);
//						OpenViewAction ova = new OpenViewAction(getSite()
//								.getWorkbenchWindow(), to.getName(), View.ID);
//						ova.run();
//					}
//				}//~~~
				
				System.out.println("NV --> Object = "+object);
				if (object instanceof TreeObject) {
					TreeObject to = (TreeObject) object;
					System.out.println("NV --> to = "+to);
					
					if (to.getType() != null && to.getType().equals("leaf")) {
						System.out.println("to.gettype() = "+to.getType());
						System.out.println("to.getParent() = "+to.getParent().getParent());
						parent=to.getParent().getParent().toString();
						TreeViewerModel model = new TreeViewerModel();
						model.setChildTreeName(to.getName());
						model.setTreeViewer(viewer);
						model.setLevelNo(1);
						data = model;
						setData(model);						
						System.out.println("the data is in navigationView Class :"+model.getTreeViewer().getData(getTitle()));
						OpenViewAction ova = new OpenViewAction(getSite().getWorkbenchWindow(), to.getName(), View.ID);
						final String title=to.getName()+"(1)";
						System.out.println(title);
						ova.run(title);	
					}
				}//~~~
				
				
				
				
			}
		});
	}

	
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
		System.out.println("the Data Set is in Navigatin Calss :"+data);
	}

}