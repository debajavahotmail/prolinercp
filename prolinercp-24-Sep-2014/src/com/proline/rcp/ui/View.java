package com.proline.rcp.ui;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.ViewPart;

import com.proline.core.handler.ProlineDefinitionHandler;
import com.proline.defn.schema.models.ProlineDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level;
import com.proline.models.DBModel;
import com.proline.models.DBModelProvider;
import com.proline.models.ProlineModelBean;
import com.proline.models.TreeViewerModel;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.controller.ViewController;
import com.proline.rcp.util.ProlineLogger;

public class View extends ViewPart {

	public static final String ID = "prolinercp.view";
	private TableViewer viewer = null;
	private Object data;
	protected int levelNo;
	public static DBModelProvider modelProvider = null;
	private String newQuery;
	public String partName;
	public String name;
	public String getNewQuery() {
		return newQuery;
	}

	public void setNewQuery(String newQuery) {
		this.newQuery = newQuery;
	}

	public void createPartControl(Composite parent) {
		IViewPart part = getViewSite().getWorkbenchWindow().getActivePage()
				.findView("prolinercp.navigationView");
		ProlineDefinitionHandler proDefnHandler=null;
		Map<Integer,Level> levelMap=null;
		ProlineDefinition proDefn=null;
		NavigationView nv = null;
		ProlineModelBean pmb;
		TreeViewerModel model = null;
		if (part instanceof NavigationView) {
			nv = (NavigationView) part;
			data = nv.getData();
		}
		
		String treeName = null;
		if (data != null) {
			model = (TreeViewerModel) data;
			treeName = model.getChildTreeName();	
			System.out.println("View --> treename = "+treeName);
			ProlineLogger.logInfo("View --> treename = "+treeName);
			levelNo = model.getLevelNo();
			System.out.println("View --> levelNo = "+levelNo);
			proDefnHandler = new ProlineDefinitionHandler();
			proDefn = proDefnHandler.getProlineDefn(FileConstants.PROLINE_DEFN);
			levelMap = proDefnHandler.getSystemDetailsMap(proDefn, treeName);
			DBModel dbModel=model.getDbModel();
			Level getTabLevel = levelMap.get(levelNo);
			System.out.println("The Level no is :"+levelNo);
			//System.out.println("The Level no is :"+getTabLevel.getTabName());			
			String tabName = null;
			String tab="";
//			name=getTabLevel.getTabName();
//			if (!name.equals("")){
//				System.out.println("The Tab name is :"+name);
//				//level.getTabName();
//				int value=Integer.parseInt(name);		
//				System.out.println("The Tab name value is :"+name);
//				tabName = dbModel.getAllData().get(value);
//				System.out.println("The dbmodle the view actualvalue is :"+tabName);
//				tab=tabName+"-";
//			}		
			ProlineLogger.logInfo("View --> treename = "+treeName);		
			ProlineLogger.logInfo("View --> levelNo = "+levelNo);
			partName = tab+model.getChildTreeName()+"("+( (levelNo == 0) ? "Level-1" : String.valueOf(levelNo)+")" );
			System.out.println("View --> partname = "+partName);
			ProlineLogger.logInfo("View --> partname = "+partName);
			setPartName(partName);// Header to display
		}


		Composite top = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		top.setLayout(layout);
		// top banner
		Composite banner = new Composite(top, SWT.NONE);
		banner.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL,
				GridData.VERTICAL_ALIGN_BEGINNING, true, false));
		layout = new GridLayout();
		layout.marginHeight = 2;//5;
		layout.marginWidth = 10;
		layout.numColumns = 1;// 2 //5
		banner.setLayout(layout);

		/*ProlineDefinitionHandler proDefnHandler = new ProlineDefinitionHandler();
		ProlineDefinition proDefn = proDefnHandler.getProlineDefn(FileConstants.PROLINE_DEFN);
		Map<Integer,Level> levelMap = proDefnHandler.getSystemDetailsMap(proDefn, treeName);*/
		int totalLevelSize = levelMap.size();		

		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
		Label label = new Label(banner, SWT.NONE);
		label.setFont(boldFont);
		label.setText(treeName+" : level ("+levelNo+"/"+totalLevelSize+")");

		IWorkbenchPartSite wbPartSite = getSite();
		ViewController controller = new ViewController(wbPartSite, viewer,levelNo,this);
		controller.createToolBar(banner, treeName);
		

		DBModel currentDbModel = model.getDbModel();
		//DBModelProvider modelProvider = null;
		

		System.out.println("nv.parent = "+nv.parent);
		ProlineLogger.logInfo("nv.parent = "+nv.parent);
		System.out.println("View --> currentDbModel data = "+currentDbModel);
		ProlineLogger.logInfo("View --> currentDbModel data = "+currentDbModel);		

		if( currentDbModel != null ) {
			List<String> columnNames = model.getColumnNames();
			modelProvider = proDefnHandler.handle(proDefn,treeName,levelNo,currentDbModel,columnNames);

		}
		else {
			modelProvider = proDefnHandler.handle(proDefn,treeName,levelNo);
		}
		modelProvider.setPartName(partName);
		viewer = controller.createViewer(top, modelProvider);


	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}


	@Override
	public void dispose() {
		//		System.out.println("part name in dispose-------->"+getPartName());
		//		System.out.println("part is disposed..........");
		super.dispose();
	}

}
