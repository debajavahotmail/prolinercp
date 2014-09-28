package com.proline.rcp.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.proline.models.DBModel;
import com.proline.models.DBModelProvider;
import com.proline.models.DBSystemModel;
import com.proline.models.dashboards.ChartType;
import com.proline.models.dashboards.DashBoardChart;
import com.proline.rcp.ui.Activator;
import com.proline.util.database.DatabaseUtil;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ExtendedDashboardView extends ViewPart {

	public static final String ID = "prolinercp.extendedDashboardView";
	private TableViewer viewer = null;
	private String dashBoardKey = null;
	private DashBoardChart dashboardChartModel;
	private DBSystemModel dbSystemModel;

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		dashBoardKey = (String) site.getShell().getData("dashBoardDataKey1");
		dashboardChartModel = (DashBoardChart) site.getShell().getData("dashBoardDataKey2");
		dbSystemModel = (DBSystemModel) site.getShell().getData("dashBoardDataKey3");
	}

	@Override
	public void createPartControl(Composite parent) {
		setPartName("Extended Chart View");
		setTitleImage(Activator.getImageDescriptor(ChartType.BAR.getImage()).createImage());
		Composite top = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		top.setLayout(layout);

		// top banner
		Composite banner = new Composite(top, SWT.NONE);
		banner.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL,
				GridData.VERTICAL_ALIGN_BEGINNING, true, false));
		layout = new GridLayout();
		layout.marginHeight = 2;// 5;
		layout.marginWidth = 10;
		layout.numColumns = 1;// 2 //5
		banner.setLayout(layout);

		//Get the data from database
		String host = dbSystemModel.getHostName();
		String userName = dbSystemModel.getUserName();
		String pwd = dbSystemModel.getPassword();
		String schema = dbSystemModel.getSchemaName();
		String query = dashboardChartModel.getDbQuery();
		List<String> dataList = new ArrayList<String>();
		dataList.add(dashBoardKey);
		DBModelProvider modelProvider = DatabaseUtil.getDBModelProvider(host, userName, pwd, schema, query,dataList);
		createViewer(top, modelProvider);
	}

	public TableViewer createViewer(Composite parent,DBModelProvider modelProvider) {
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
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true; 
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
		return viewer;
	}

	private void createTableColumns(final Composite parent,final TableViewer viewer, String[] titles) {
		for (int i = 0; i < titles.length; i++) {
			final int counter = i;
			TableViewerColumn col = createTableViewerColumn(titles[i], 100, 1);//here column data is added 1st onwards
			col.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					DBModel model = (DBModel) element;
					return model.getData(counter);
				}
			});
		}
	}

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

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
