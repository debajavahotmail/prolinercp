package com.proline.rcp.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartSite;

import com.proline.core.handler.ProlineDefinitionHandler;
import com.proline.defn.schema.models.ProlineDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level;
import com.proline.models.DBModel;
import com.proline.models.DBModelProvider;
import com.proline.models.TreeViewerModel;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.constants.TextConstants;
import com.proline.rcp.ui.GetQuery;
import com.proline.rcp.ui.NavigationView;
import com.proline.rcp.ui.OpenViewAction;
import com.proline.rcp.ui.View;
import com.proline.rcp.util.ProlineLogger;
import com.proline.util.mail.SendMail;

public class ViewController extends ViewerComparator {

	private TableViewer viewer = null;
	private IWorkbenchPartSite wbPartSite = null;
	private String treeName = null;
	private int levelNo = 0; 
	private View tempView;
	public String tempPartName;

	public static final int ASC = 1;
	public static final int NONE = 0;
	public static final int DESC =-1; 

	private int direction = 0;
	private TableColumn column = null;
	private int columnIndex = 0;

	private String savedQuery = null;
	private String partName = null;
	DBModelProvider modelProvider=new DBModelProvider();
	int count;
	static int tot=0;
	int totalLevelSize;
	public static File file;

	public ViewController(){
		
	}
	
	public ViewController(IWorkbenchPartSite wbPartSite, TableViewer viewer,int levelNo,View tempView) {
		this.wbPartSite = wbPartSite;
		this.viewer = viewer;
		this.levelNo = levelNo;
		this.tempView = tempView;

	}
	public ViewController(TableViewer viewer) {
		System.out.println("this is ViewController(viewer) ");
		ProlineLogger.logInfo("this is ViewController(viewer) ");
		this.viewer = viewer;
		Assert.isTrue(this.viewer.getComparator() == null);
		viewer.setComparator(this);		
		for (TableColumn tableColumn : viewer.getTable().getColumns()) {
			tableColumn.addSelectionListener(selectionHandler);
		}
	} 
	public void createToolBar(Composite banner, final String treeName) {
		System.out.println("inside createToolBar");
		ProlineLogger.logInfo("inside createToolBar ");
		this.treeName = treeName;
		ToolBar toolBar = new ToolBar(banner, SWT.FLAT);
		createRefreshToolItem(toolBar);
		createExportToolItem(toolBar);
		createMailToolItem(toolBar);
		createGetQueryItem(toolBar,banner);
		createExportTXTToolItem(toolBar);
	}	

	private void createExportTXTToolItem(ToolBar toolBar) {
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(FileConstants.EXPORTFILE_IMG);
		item.setToolTipText(TextConstants.EXPORTFILE_TXT);
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				System.out.println("Inside the Export TEXT button....");
				FileDialog dialog = new FileDialog(new Shell(),SWT.SAVE);	
				//dialog.setFilterExtensions(new String[] { "*.xlsx","*.csv"});
				String fileDilogwindow=dialog.open();

				if(fileDilogwindow!=null){
					Date startTime=new Date();					
					File file = new File(fileDilogwindow);
					System.out.println("The Export File starting time is :"+startTime.getTime());
					ProlineLogger.logInfo("The Export File starting time is :"+startTime.getTime());
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

						final Table table = viewer.getTable();
						final int[] columnOrder = table.getColumnOrder();
						for(int columnOrderIndex = 0; columnOrderIndex < columnOrder.length; 
								columnOrderIndex++) {
							int columnIndex = columnOrder[columnOrderIndex];
							TableColumn tableColumn = table.getColumn(columnIndex);		                
							writer.write(tableColumn.getText());
							if(columnIndex!=columnOrder.length-1)
								writer.write(",");
						}
						writer.write("\n");

						final int itemCount = table.getItemCount();
						for(int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
							TableItem item = table.getItem(itemIndex);

							for(int columnOrderIndex = 0; 
									columnOrderIndex < columnOrder.length; 
									columnOrderIndex++) {
								int columnIndex = columnOrder[columnOrderIndex];

								writer.write(item.getText(columnIndex));
								if(columnIndex!=columnOrder.length-1)
									writer.write(",");
							}
							writer.write("\n");
						}
						Date endTime=new Date();
						long duration  = startTime.getTime() - endTime.getTime();

						long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
						long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
						long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

						System.out.println("The Export File taking time is :"+diffInSeconds+"Seconds");
						ProlineLogger.logInfo("The Export File taking time is :"+diffInSeconds+"Seconds");

					} catch(IOException ioe) {
						// TODO: add logic to inform the user of the problem
						System.err.println("trouble exporting table data to file");
						ioe.printStackTrace();
						ProlineLogger.logInfo("trouble exporting table data to file"+ioe);
					}

				}else{

				}

			}
		});


	}
	private void createMailToolItem(ToolBar toolBar) {
		ToolItem mailitem = new ToolItem(toolBar, SWT.PUSH);
		mailitem.setImage(FileConstants.EMAIL_IMG);
		mailitem.setToolTipText(TextConstants.SEND_MAIL_TXT);
		mailitem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				//dateFormat.format(date);
				createWorkbookFromTable(viewer);
				dumpWorkbookToAFile(createWorkbookFromTable(viewer),"C:/MailFiles/"+partName+"_"+dateFormat.format(date)+".xls");
				System.out.println("Inside the sentMail button....");
				//new SendEmail().execute();
				SendMail sendMail=new SendMail(new Shell(),SWT.DIALOG_TRIM);
				sendMail.partName=partName;
				OpenViewAction ova = new OpenViewAction(wbPartSite.getWorkbenchWindow(), treeName, View.ID);
				//new ViewController(wbPartSite, viewer, levelNo, tempView);
				//sendMail.setTextSubject(setText(modelProvider.getPartName().toString()));
				System.out.println("VC_createMailToolItem --> PartName() = "+partName);
				sendMail.open();
			}
		});

	}

	private void createRefreshToolItem(ToolBar toolBar) {
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(FileConstants.REFRESH_IMG);
		item.setToolTipText(TextConstants.REFRESH_TXT);

		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//				System.out.println("In refresh treeName------>"+treeName);
				//				System.out.println("In refresh levelNo------>"+levelNo);
				//				System.out.println("Saved Query---------->"+savedQuery);
				//Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

				Cursor waitCursor = Display.getDefault().getSystemCursor(SWT.CURSOR_WAIT);
				Display.getDefault().getActiveShell().setCursor(waitCursor);
				System.out.println("the query is :"+savedQuery);
				ProlineLogger.logInfo("the query is :"+savedQuery);
				DBModelProvider modelProvider = new ProlineDefinitionHandler().handle(treeName,levelNo,savedQuery);
				List<DBModel> models = modelProvider.getDbModelList();
				viewer.setInput(models);
				viewer.refresh();
				//Cursor currentCursor = Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW);
				Cursor currentCursor=Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW);
				Display.getDefault().getActiveShell().setCursor(currentCursor);



			}
		});
	}
	
	private void createGetQueryItem(final ToolBar toolBar,final Composite parent) {
		ToolItem getqueryitem = new ToolItem(toolBar, SWT.PUSH);
		getqueryitem.setImage(FileConstants.GQ_IMG);
		getqueryitem.setToolTipText(TextConstants.GET_QUERY_TXT);

		getqueryitem.addSelectionListener(new SelectionAdapter() {


			public void widgetSelected(SelectionEvent e) {

				String treeName1 ="";
				GetQuery dialog = new GetQuery(new Shell());
				dialog.setSavedQuery(savedQuery);
				dialog.create();

				if (dialog.open() == Window.OK) {
					//OpenViewAction ova = new OpenViewAction(wbPartSite.getWorkbenchWindow(), treeName, View.ID);
					//ova.run();

					savedQuery=dialog.getSavedQuery();	
					//newQuery=savedQuery;
					System.out.println("VC --> dialog.getNewQuery()= "+dialog.getSavedQuery());
					List<String> columnNamesold=getTableColumnNames();
					System.out.println(viewer.getTable().getItems().toString());
					System.out.println("old table --> "+getTableColumnNames());

					DBModelProvider modelProvider = new ProlineDefinitionHandler().handle(treeName,levelNo,savedQuery);

					//System.out.println("VC --> NewQuery()= "+newQuery);

					System.out.println("treename = "+treeName+" "+"savedquery = "+savedQuery);
					//viewer=createViewer(parent, modelProvider);
					viewer.getTable().setRedraw(false);
					while ( viewer.getTable().getColumnCount() > 0 ) {
						viewer.getTable().getColumns()[ 0 ].dispose();
					}
					List<DBModel> models = modelProvider.getDbModelList();
					String[] titles = modelProvider.getTitles1();
					for (int i = 0; i < titles.length; i++) {
						String title=titles[i];
						final int counter = i;
						final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
								SWT.NONE);
						final TableColumn column = viewerColumn.getColumn();
						column.setText(title);
						column.setWidth(100);
						column.setResizable(true);
						column.setMoveable(true);
						//viewer.getTable().setData(column);
						//return viewerColumn;
						viewerColumn .setLabelProvider(new ColumnLabelProvider() {
							@Override
							public String getText(Object element) {
								DBModel model = (DBModel) element;
								return model.getData(counter);
							}
						});

					}

					viewer.getTable().setRedraw(true);

					viewer.setInput(models);		


					//viewer.getTable().setVisible(true);

				}


			}


		});	

	}

	private void createExportToolItem(ToolBar toolBar) {

		System.out.println("inside the xl export()");
		ProlineLogger.logInfo("inside xl export()");
		ToolItem xlsitem = new ToolItem(toolBar, SWT.PUSH);
		xlsitem.setImage(FileConstants.XLS_IMG);
		xlsitem.setToolTipText(TextConstants.EXPORT_TO_XLS_TXT);		
		xlsitem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {


				Thread thr=new Thread(new SiThread());
				thr.run();
				/*Cursor waitCursor = Display.getDefault().getSystemCursor(SWT.CURSOR_WAIT);
				Display.getDefault().getActiveShell().setCursor(waitCursor);*/


				/*InputDialog dlg = new InputDialog(new Shell()Display.getCurrent().getActiveShell(),null, "Enter FileName ", "", null);
				if (dlg.open() == Window.OK) {	

					Cursor waitCursor1 = Display.getDefault().getSystemCursor(SWT.CURSOR_WAIT);
					Display.getDefault().getActiveShell().setCursor(waitCursor1);
					Thread print100 = new Thread(new SiThread(10000));
					// User clicked OK; update the label with the input
					System.out.println("the dialog msg is :"+dlg.getValue());
					ProlineLogger.logInfo("the dialog msg is :"+dlg.getValue());
					//Sstatrt
					Date startTime=new Date();
					System.out.println("The excel export starting time is :"+startTime.getTime());
					XSSFWorkbook wb=createWorkbookFromTable(viewer);
					String statusInfo[]=dumpWorkbookToAFile(wb, dlg.getValue());
					//ending
					Date endTime=new Date();
					long duration  = startTime.getTime() - endTime.getTime();

					long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
					long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
					long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

					System.out.println("The excel export taking time is :"+diffInSeconds+"Seconds");
					//Save Workbook Failed
					if(statusInfo[0].equals("Save Workbook Failed"))
					{

						MessageDialog.openError(new Shell(),statusInfo[0],statusInfo[1]);
					}
					else
					{
						MessageDialog.openInformation(new Shell(),statusInfo[0],statusInfo[1]);
					}
					Cursor currentCursor=Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW);
					Display.getDefault().getActiveShell().setCursor(currentCursor);
				}
				else{

					MessageDialog.openInformation(new Shell(),"Export Error","User Cancel the Excel Export");
				}	*/
				/*Cursor currentCursor=Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW);
				Display.getDefault().getActiveShell().setCursor(currentCursor);*/
			}
		});


	}

	public String[] dumpWorkbookToAFile(XSSFWorkbook wb, String filename) {
		try {

			
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			dateFormat.format(date);
		 
			//String flname1=FileConstants.PROLINEXLWRITER_PSS_FILE_PATH;
			//System.out.println("the file name is :"+filename+"the file path is :"+flname1);
			ProlineLogger.logInfo("the file name is :"+filename);
			String flname=filename;
			String flnm=/*dateFormat.format(date)+*/flname;
			file=new File(flnm);	    	
			if(file.exists()) 
			{
				//MessageDialog.openInformation(new Shell(),"File Error","File name alredey Exits" + file);
				ProlineLogger.logInfo("File Error File name alredey Exits");
				return new String[]{"File Error","File name alredey Exits" + file};
			}
			//file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();

			////MessageDialog.openInformation(new Shell(),//);
			ProlineLogger.logInfo("Save Workbook Successful Workbook saved to the file:\n\n" + file);
			return new String[]{"Save Workbook Successful","Workbook saved to the file:\n\n" + file};
		} catch (IOException ioe) {
			ioe.printStackTrace();
			String msg = ioe.getMessage();
			//MessageDialog.openError(new Shell(),,);
			ProlineLogger.logError(ioe);
			return new String[]{"Save Workbook Failed","Could not save workbook to the file:\n\n" + msg};

		}
	}
	
	public String[] dumpWorkbookToAFixedFile(XSSFWorkbook wb, String filename) {
		try {

			boolean success = false;
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			dateFormat.format(date);
			
			String dir = "C:/MailFiles/";
			File directory = new File(dir);
			
			if (directory.exists()) {
	            System.out.println("Directory already exists ...");

	        } else {
	            System.out.println("Directory not exists, creating now");
	            success = directory.mkdir();
	            if (success) {
	                System.out.printf("Successfully created new directory : %s%n", dir);
	                
	            } else {
	                System.out.printf("Failed to create new directory: %s%n", dir);
	            }
	        }

			
	           
			//String flname1=FileConstants.PROLINEXLWRITER_PSS_FILE_PATH;
			//System.out.println("the file name is :"+filename+"the file path is :"+flname1);
			ProlineLogger.logInfo("the file name is :"+filename);
			String flname=filename;
			String flnm=dateFormat.format(date)+flname+".xlsx";
			File file=new File("C:/MailFiles/"+flnm);	    	
			if(file.exists()) 
			{
				//MessageDialog.openInformation(new Shell(),"File Error","File name alredey Exits" + file);
				ProlineLogger.logInfo("File Error File name alredey Exits");
				return new String[]{"File Error","File name alredey Exits" + file};
			}
			//file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();

			////MessageDialog.openInformation(new Shell(),//);
			ProlineLogger.logInfo("Save Workbook Successful Workbook saved to the file:\n\n" + file);
			return new String[]{"Save Workbook Successful","Workbook saved to the file:\n\n" + file};
		} catch (IOException ioe) {
			ioe.printStackTrace();
			String msg = ioe.getMessage();
			//MessageDialog.openError(new Shell(),,);
			ProlineLogger.logError(ioe);
			return new String[]{"Save Workbook Failed","Could not save workbook to the file:\n\n" + msg};

		}
	}
	
	Composite tempParent=null;

	public TableViewer createViewer(Composite parent,DBModelProvider modelProvider) {
		tempParent=parent;
		savedQuery = modelProvider.getSavedQuery();
		partName = modelProvider.getPartName();
		System.out.println("VC_createViewer partname = "+ modelProvider.getPartName());
		List<DBModel> models = modelProvider.getDbModelList();
		String[] titles = modelProvider.getTitles1();
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createTableColumns(parent, viewer, titles);		
		final Table table = viewer.getTable();
		table.getColumn(0).setWidth(30);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);		
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(models);
		//System.out.println("In ViewController The table having data is1 :"+table.getItemCount());
		/*for (int i = 0; i < rowConunt; i++) {
			TableItem item = new TableItem(leftTable, SWT.NONE);
			item.setText(""+i);			
		}*/
		/*if(table.getItemCount()==0){		
			MessageDialog.openInformation(new Shell(),"DB Error","No Data Found/Records Available");
			tempView.dispose();	
			tempView.getViewSite().getPage().hideView(tempView);			
			//wbPartSite.getWorkbenchWindow().close();
		}else{*/
			wbPartSite.setSelectionProvider(viewer);
			System.out.println("In ViewController The table having data is2 :"+table.getItemCount());
			ProlineLogger.logInfo("In ViewController The table having data is2 :"+table.getItemCount());
			GridData gridData = new GridData();
			gridData.verticalAlignment = GridData.FILL;
			gridData.horizontalSpan = 2;
			gridData.grabExcessHorizontalSpace = true; 
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			System.out.println("In ViewController The table having data is3 :"+table.getItemCount());
			ProlineLogger.logInfo("In ViewController The table having data is3 :"+table.getItemCount());
			viewer.getControl().setLayoutData(gridData);
			addViewerSelectionListener();
			addNewMouseListener();
			new ViewController(viewer);
		//}

		return viewer;
	}

	private List<String> getTableColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		TableColumn[] columns = viewer.getTable().getColumns();
		for( TableColumn column : columns ) {
			columnNames.add(column.getText());
		}
		return columnNames;
	}

	private String getCellValueOnMouseHover(Table table,Point coords) {
		Map<Integer,Map<String,String>> indexKeyValueMap = new ProlineDefinitionHandler().getKeyValueMap(treeName, levelNo);
		int index = 0;
		String cellValue = null;
		TableItem item = table.getItem(coords);
		int columnCount = table.getColumnCount();
		try {
			for (int i = 0; i < columnCount; i++) {
				Rectangle rect = item.getBounds(i);
				if (rect.contains(coords)) {
					index = i;
				}
			}
			cellValue = item.getText(index);

			//Use the cellValue as key and find the Actual Description
		} catch (Exception e) {
			cellValue = null;
		}
		try {
			String finalValue = indexKeyValueMap.get(index).get(cellValue);
			//			System.out.println("Final Value :::"+finalValue);
			cellValue = (finalValue != null) ? finalValue : cellValue;
		} catch (Exception e) {

		}
		return cellValue;
	}

	private void addNewMouseListener() {
		viewer.getTable().addListener(SWT.MouseHover, new Listener() {

			@Override
			public void handleEvent(Event event) {
				Table table = viewer.getTable();
				Point coords = new Point(event.x, event.y);
				DefaultToolTip toolTip = new DefaultToolTip(viewer.getControl(),
						ToolTip.NO_RECREATE, false);
				String cellValue = getCellValueOnMouseHover(table, coords);
				if( cellValue != null)
					toolTip.setText(cellValue);
				toolTip.setBackgroundColor(viewer.getTable().getDisplay()
						.getSystemColor(SWT.COLOR_GREEN));
				toolTip.setShift(new Point(10, 5));
			}
		});
	}
	static String actualTreeName=null;
	private void addViewerSelectionListener() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {

				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();

				List<String> columnNames = getTableColumnNames();
				//				System.out.println("You double clicked on table ....."
				//						+ selection.getFirstElement());d
				DBModel model = (DBModel) selection.getFirstElement();

				//				System.out.println(model.getAllData().get(0) + "----"
				//						 + model.getAllData().get(1) + "----"
				//						 + model.getAllData().get(2));
				count=count+1;
				System.out.println("The data is now in viewController class :"+model.getAllData().size());
				ProlineLogger.logInfo("The data is now in viewController class :"+model.getAllData().size());
				
				partName = tempView.getPartName();/*+"-"+count;*/
				modelProvider.setPartName(partName);
				System.out.println("The Partname is viewcontroller :"+partName+"Count is :"+count);	
				actualTreeName = partName.substring(0, partName.indexOf("("));
				System.out.println("tHE actualTreeName NAME I S 3 : "+actualTreeName);			
				String level = partName.substring(partName.indexOf("(")+1, partName.indexOf(")"));			
				System.out.println("The level is viewcontroller:"+level+" :The Tree name value is : "+treeName);
				int currentLevelNo = Integer.parseInt(level);		
				ProlineDefinitionHandler proDefnHandler = new ProlineDefinitionHandler();
				ProlineDefinition proDefn = proDefnHandler.getProlineDefn(FileConstants.PROLINE_DEFN);
				Map<Integer,Level> levelMap = proDefnHandler.getSystemDetailsMap(proDefn, treeName);
				totalLevelSize = levelMap.size();
				if( currentLevelNo < totalLevelSize ) {
					//storeLevelAndTreeName(currentLevelNo, actualTreeName,model,columnNames);
					//System.out.println("Count is in toltal levelis  :"+count);
					storeLevelAndTreeName(currentLevelNo, /*actualTreeName*/treeName,model,columnNames);
					System.out.println("Count is in toltal levelis  :"+count);
					OpenViewAction ova = new OpenViewAction(wbPartSite.getWorkbenchWindow(), treeName, View.ID);
					//final String title=treeName+"("+(level+1)+")";
					//System.out.println(title);
					final String title=treeName+"("+(level)+")"+count;
					System.out.println("The Title is :"+title);
					ProlineLogger.logInfo(title);
					ova.run(title);
					//ova.run("");
				}
				else {
					MessageDialog.openError(new Shell(), "Error", "There are no further levels to display");
					ProlineLogger.logInfo("Error There are no further levels to display");
				}
			}
		});
	}
	
	private void storeLevelAndTreeName(int currentLevelNo,String actualTreeName,DBModel model,List<String> columnNames) {
		IViewPart part = wbPartSite.getWorkbenchWindow().getActivePage().findView("prolinercp.navigationView");
		NavigationView nv = null;
		if (part instanceof NavigationView) {
			nv = (NavigationView) part;
			Object data = nv.getData();
			TreeViewerModel model11 = (TreeViewerModel) data;
			model11.setLevelNo(++currentLevelNo);

			model11.setChildTreeName(actualTreeName);

			model11.setDbModel(model);
			model11.setColumnNames(columnNames);
			nv.setData(model11);
		}
	}

	private void createTableColumns(final Composite parent,final TableViewer viewer, String[] titles) {
		for (int i = 0; i < titles.length; i++) {
			final int counter = i;
			TableViewerColumn col = createTableViewerColumn(titles[i], 100, 1);//here column data is added 1st onwords
			col.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					DBModel model = (DBModel) element;
					return model.getData(counter);
				}
				//				@Override
				//				public Image getImage(Object element) {
				//					return PlatformUI.getWorkbench().getSharedImages()
				//					.getImage(ISharedImages.IMG_OBJ_FOLDER);
				//				}
			});
		}
	}

	// create the columns for the table
	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private XSSFWorkbook createWorkbookFromTable(TableViewer table) {


		// create a workbook
		XSSFWorkbook wb = new XSSFWorkbook();

		// add a worksheet
		XSSFSheet sheet = wb.createSheet(treeName+"-"+levelNo);

		// shade the background of the header row
		XSSFCellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		DBModelProvider modelProvider = new ProlineDefinitionHandler().handle(treeName,levelNo,savedQuery);
		List<DBModel> models = modelProvider.getDbModelList();
		/*modelProvider.get
		DBModel mdl=new DBModel();*/
		// add header row
		// Table table = table.getTable();
		TableColumn[] columns =viewer.getTable().getColumns();
		int rowIndex = 0;
		int cellIndex = 0;
		XSSFRow header = sheet.createRow((short) rowIndex++);
		for (TableColumn column : columns) {
			String columnName = column.getText();
			XSSFCell cell = header.createCell(cellIndex++);
			cell.setCellValue(column.getText());
			cell.setCellStyle(headerStyle);
		}
		// add data rows
		TableItem[] items = viewer.getTable().getItems();
		for (TableItem item : items) {
			// create a new row
			XSSFRow row = sheet.createRow((short) rowIndex++);
			cellIndex = 0;

			for (int i = 0; i < columns.length; i++) {
				// create a new cell
				// String columnName = tableColumnNames[i];
				XSSFCell cell = row.createCell(cellIndex++);

				// set the horizontal alignment (default to RIGHT)
				//  XSSFCellStyle cellStyle = wb.createCellStyle();
				// HorizontalAlignment ha = HorizontalAlignment.RIGHT;
				// cellStyle.setAlignment(ha);
				// cell.setCellStyle(cellStyle);

				// set the cell's value
				String text = item.getText(i);
				cell.setCellValue(text);
			}
		}

		// autofit the columns
		/* for (int i = 0; i < columns.length; i++) {
	        sheet.autoSizeColumn((short) i);
	    }*/

		return wb;
	}



	final private SelectionListener selectionHandler = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			System.out.println("this is selectionhangler ");
			ViewController sorter = (ViewController) ViewController.this.viewer.getComparator();
			Assert.isTrue(ViewController.this == sorter);
			TableColumn selectedColumn = (TableColumn) e.widget;
			System.out.println("the column txt is :"+selectedColumn.getText());
			if(selectedColumn.getText().equals(" ")){//remove the selection listener for the column 0(first column)

			}else{
				Assert.isTrue(ViewController.this.viewer.getTable() == selectedColumn.getParent());
				ViewController.this.setColumn(selectedColumn);
			}

		}
	};

	public void setColumn(TableColumn selectedColumn) {

		if (column == selectedColumn) {
			switch (direction) {
			case ASC:
				direction = DESC;
				break;
			case DESC:
				direction = ASC;
				break;
			default:
				direction = ASC;
				break;
			}
		} else {
			this.column = selectedColumn;
			this.direction = ASC;
		}

		Table table = viewer.getTable();
		String colName=table.getColumn(0).getText();
		if(!colName.equals("")){
			switch (direction) {
			case ASC:
				table.setSortColumn(selectedColumn);
				table.setSortDirection(SWT.UP);
				break;
			case DESC:
				table.setSortColumn(selectedColumn);
				table.setSortDirection(SWT.DOWN);
				break;
			default:
				table.setSortColumn(null);
				table.setSortDirection(SWT.NONE);
				break;
			}

			TableColumn[] columns = table.getColumns();
			for (int i = 0; i < columns.length; i++) {//remove the column 0 indes to sort
				TableColumn theColumn = columns[i];
				System.out.println("the column is :"+columns[i]);
				if (theColumn == this.column) columnIndex = i;
			}
			viewer.setComparator(null);
			viewer.setComparator(this);
		}
		else{
			System.out.println("the column is null");
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return direction * doCompare(viewer, e1, e2);
	}

	protected int doCompare(Viewer v, Object e1, Object e2) {
		Assert.isTrue(viewer == this.viewer);
		ILabelProvider labelProvider = (ILabelProvider) viewer.getLabelProvider(columnIndex);
		String t1 = labelProvider.getText(e1);
		String t2 = labelProvider.getText(e2);
		if (t1 == null) t1 = "";
		if (t2 == null) t2 = "";
		return t1.compareTo(t2);
	}


	class SiThread implements Runnable {

		public SiThread(){

		}

		public void run() {			
			FileDialog dialog = new FileDialog(new Shell(),SWT.SAVE);	
			dialog.setFilterExtensions(new String[] { "*.xlsx","*.csv"});
			String fileDilogwindow=dialog.open();
			if(fileDilogwindow!=null){
				System.out.println("the filedialog window test is to made is :"+fileDilogwindow);

				ProlineLogger.logInfo("the dialog file path is :"+fileDilogwindow);
				//Sstatrt
				Date startTime=new Date();
				System.out.println("The excel export starting time is :"+startTime.getTime());
				XSSFWorkbook wb=createWorkbookFromTable(viewer);
				String statusInfo[]=dumpWorkbookToAFile(wb, fileDilogwindow);
				//ending
				Date endTime=new Date();
				long duration  = startTime.getTime() - endTime.getTime();

				long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
				long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
				long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

				System.out.println("The excel export taking time is :"+diffInSeconds+"Seconds");
				ProlineLogger.logInfo("The excel export taking time is :"+diffInSeconds+"Seconds");
				//Save Workbook Failed
				if(statusInfo[0].equals("Save Workbook Failed"))
				{

					MessageDialog.openError(new Shell(),statusInfo[0],statusInfo[1]);
				}
				else
				{
					MessageDialog.openInformation(new Shell(),statusInfo[0],statusInfo[1]);
				}

			}
			else{
				System.out.println("The filedialog is cancled ");
				MessageDialog.openInformation(new Shell(),"Export Error","User Cancel the Excel Export");
				Cursor currentCursor=Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW);
				Display.getDefault().getActiveShell().setCursor(currentCursor);
			}



			//InputDialog dlg = new InputDialog(new Shell()/*Display.getCurrent().getActiveShell()*/,null, "Enter FileName ", "", null);
			/*if (dlg.open() == Window.OK) {	

				Cursor waitCursor1 = Display.getDefault().getSystemCursor(SWT.CURSOR_WAIT);
				Display.getDefault().getActiveShell().setCursor(waitCursor1);

				// User clicked OK; update the label with the input
				System.out.println("the dialog msg is :"+dlg.getValue());
				ProlineLogger.logInfo("the dialog msg is :"+dlg.getValue());
				//Sstatrt
				Date startTime=new Date();
				System.out.println("The excel export starting time is :"+startTime.getTime());
				XSSFWorkbook wb=createWorkbookFromTable(viewer);
				String statusInfo[]=dumpWorkbookToAFile(wb, dlg.getValue());
				//ending
				Date endTime=new Date();
				long duration  = startTime.getTime() - endTime.getTime();

				long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
				long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
				long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

				System.out.println("The excel export taking time is :"+diffInSeconds+"Seconds");
				ProlineLogger.logInfo("The excel export taking time is :"+diffInSeconds+"Seconds");
				//Save Workbook Failed
				if(statusInfo[0].equals("Save Workbook Failed"))
				{

					MessageDialog.openError(new Shell(),statusInfo[0],statusInfo[1]);
				}
				else
				{
					MessageDialog.openInformation(new Shell(),statusInfo[0],statusInfo[1]);
				}
				Cursor currentCursor=Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW);
				Display.getDefault().getActiveShell().setCursor(currentCursor);
			}
			else{

				MessageDialog.openInformation(new Shell(),"Export Error","User Cancel the Excel Export");
			}	*/
			Cursor currentCursor=Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW);
			Display.getDefault().getActiveShell().setCursor(currentCursor);

		}
	}



}


