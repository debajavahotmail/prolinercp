package com.proline.rcp.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.proline.core.handler.ChartsOpenViewActionHandler;
import com.proline.models.dashboards.DashBoardChart;
import com.proline.rcp.views.ChartView;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ChartsOpenViewAction extends Action {
	
	private final IWorkbenchWindow window;
	private static int instanceNum = 0;
	private final String viewId;
	private String label = null;
	private DashBoardChart dashboardChartModel;
	
	public ChartsOpenViewAction(IWorkbenchWindow window, DashBoardChart dashboardChartModel, String viewId) {
		this.window = window;
		this.viewId = viewId;
		this.dashboardChartModel = dashboardChartModel;
		this.label = dashboardChartModel.getType()+"-"+dashboardChartModel.getTitle()+"-"+dashboardChartModel.getBatchDetail();
        setText(label);
	}
	
	public void run() {
		if(window != null) {	
			try {
				ChartsOpenViewActionHandler handler = new ChartsOpenViewActionHandlerImpl();
				Object data = handler.handle(label);
				IViewSite site = (IViewSite) window.getActivePage().getActivePart().getSite();
				site.getShell().setData("chartDataKey1",label);
				site.getShell().setData("chartDataKey2",dashboardChartModel);
				site.getShell().setData("chartDataKey3",data);
				ChartView view = (ChartView) window.getActivePage().showView(viewId, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
				view.init(site);
				
			} catch (Exception e) {
				MessageDialog.openError(window.getShell(), "Error", "Error opening view:" + e.getMessage());
			}
		}
	}
	
}
