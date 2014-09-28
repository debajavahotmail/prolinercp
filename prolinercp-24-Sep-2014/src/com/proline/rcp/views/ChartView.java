package com.proline.rcp.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.JFreeChart;

import com.proline.core.handler.ProlineDefinitionHandler;
import com.proline.defn.schema.models.ProlineDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level;
import com.proline.models.DBModel;
import com.proline.models.DBModelProvider;
import com.proline.models.DBSystemModel;
import com.proline.models.dashboards.ChartBuilder;
import com.proline.models.dashboards.ChartType;
import com.proline.models.dashboards.DashBoardChart;
import com.proline.rcp.actions.ExtendedDashboardViewAction;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.ui.Activator;
import com.proline.rcp.views.charts.ProlineChartComposite;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ChartView extends ViewPart {

	public static final String ID = "prolinercp.chartview";
	private ProlineChartComposite ch = null;

	private String charDataValue1 = null;
	private DashBoardChart dashboardChartModel;
	private Map<String, Number> dataMap;
	private DBSystemModel dbSystemModel;
	private Map<String, String> tooltipMap;

	@SuppressWarnings("unchecked")
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		Object chartDataKey1 = site.getShell().getData("chartDataKey1");
		charDataValue1 = (String) chartDataKey1;
		dashboardChartModel = (DashBoardChart) site.getShell().getData("chartDataKey2");

		Map<String,Object> dataObjectMap = (Map<String,Object>) site.getShell().getData("chartDataKey3");
		dataMap = (Map<String, Number>) dataObjectMap.get("chartDataDetails");
		dbSystemModel = (DBSystemModel) dataObjectMap.get("dbDetails");
		tooltipMap = (Map<String, String>) dataObjectMap.get("chartDataTooTipDetails");
	}

	private void createRefreshToolItem(ToolBar toolBar, final Composite top,
			final Composite parent) {
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(FileConstants.REFRESH_IMG);
		item.setToolTipText("Refresh");

		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ch.dispose();
				String batchName = dashboardChartModel.getBatchDetail();
				ProlineDefinitionHandler proDefnHandler =  new ProlineDefinitionHandler();
				ProlineDefinition proDefn = proDefnHandler.getProlineDefn(FileConstants.PROLINE_DEFN);
				//				System.out.println("Batch Name :::"+batchName);
				Map<Integer,Level> levelDetailsMap = proDefnHandler.getSystemDetailsMap(proDefn, batchName);
				//There will be only level
				Level level = levelDetailsMap.get(1);
				String levelName = level.getName();
				DBSystemModel dbSystemModel = proDefnHandler.getSpecificDBSystemModel(levelName);
				DBModelProvider modelProvider = proDefnHandler.handle(proDefn, batchName, 1);
				List<DBModel> models = modelProvider.getDbModelList();
				Map<String, Number> chartDetailsMap = new HashMap<String, Number>();
				for( DBModel model : models ) {
					Map<Integer,String> tempDataMap = model.getAllData();
					Number num = Double.parseDouble(tempDataMap.get(2));
					chartDetailsMap.put(tempDataMap.get(1), num);
				}
				showRefreshedChart(chartDetailsMap, top);
				top.layout();
			}
		});
	}

	public void createPartControl(Composite parent) {
		setPartName(charDataValue1);
		setTitleImage(Activator.getImageDescriptor(dashboardChartModel.getType().getImage()).createImage());
		final Composite top = new Composite(parent, SWT.BORDER);
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

		ToolBar toolBar = new ToolBar(banner, SWT.FLAT);
		createRefreshToolItem(toolBar, top, parent);
		showChart(top);
	}

	public void showChart(Composite top) {
		ChartType chartType = dashboardChartModel.getType();
//		Map<String,String> tootipMap = new HashMap<String,String>();//For Tool Tip
		ChartBuilder chartBuilder = new ChartBuilder().buildDataMap(dataMap)
				.buildColor(false).buildTooTipMap(tooltipMap)
				.buildTitle(dashboardChartModel.getTitle())
				.buildXAxisTitle(dashboardChartModel.getxAxis())
				.buildYAxisTitle(dashboardChartModel.getyAxis());
		JFreeChart chart = chartType.getChart(chartBuilder);
		ch = chartType.getChartComposite(top, SWT.NONE, chart, true);
		ch.setLayoutData(new GridData(800,500));
		addClickListener(ch, dataMap, tooltipMap);
	}

	public void showRefreshedChart(Map<String, Number> dataMap1,Composite top) {
		ChartType chartType = dashboardChartModel.getType();
//		Map<String,String> tootipMap = new HashMap<String,String>();//For Tool Tip
		ChartBuilder chartBuilder = new ChartBuilder().buildDataMap(dataMap1)
				.buildColor(false).buildTooTipMap(tooltipMap)
				.buildTitle(dashboardChartModel.getTitle())
				.buildXAxisTitle(dashboardChartModel.getxAxis())
				.buildYAxisTitle(dashboardChartModel.getyAxis());
		JFreeChart chart = chartType.getChart(chartBuilder);
		ch = chartType.getChartComposite(top, SWT.NONE, chart, true);
		ch.setLayoutData(new GridData(800,500));
		addClickListener(ch, dataMap1, tooltipMap);
	}

	public void addClickListener(ProlineChartComposite pcComposite,Map<String, Number> dataMap,Map<String, String> toolTipMap) {
		pcComposite.addSWTListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {}

			@Override
			public void mouseDown(MouseEvent e) {}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				String clickableInfo = ch.getCustomClickableValue(e);
				if( clickableInfo != null ) {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					ExtendedDashboardViewAction xtendedDashView = new ExtendedDashboardViewAction(
							window, clickableInfo, dashboardChartModel,
							dbSystemModel, ExtendedDashboardView.ID);
					xtendedDashView.run();
				}
			}
		});
	}

	public void setFocus() {

	}
}