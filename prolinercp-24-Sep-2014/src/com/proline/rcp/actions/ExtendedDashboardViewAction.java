package com.proline.rcp.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.proline.models.DBSystemModel;
import com.proline.models.dashboards.DashBoardChart;
import com.proline.rcp.views.ExtendedDashboardView;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ExtendedDashboardViewAction extends Action {

	private final IWorkbenchWindow window;
	private static int instanceNum = 0;
	private final String viewId;
	private String label = null;
	private DashBoardChart dashboardChartModel;
	private DBSystemModel dbSystemModel;

	public ExtendedDashboardViewAction(IWorkbenchWindow window, String label,
			DashBoardChart dashboardChartModel, DBSystemModel dbSystemModel,
			String viewId) {
		this.window = window;
		this.viewId = viewId;
		this.label = label;
		this.dashboardChartModel = dashboardChartModel;
		this.dbSystemModel = dbSystemModel;
		setText(label);
		//		setImageDescriptor(Activator.getImageDescriptor("/icons/sample2.gif"));
	}

	public void run() {
		if(window != null) {	
			try {
				IViewSite site = (IViewSite) window.getActivePage().getActivePart().getSite();
				site.getShell().setData("dashBoardDataKey1",label);
				site.getShell().setData("dashBoardDataKey2",dashboardChartModel);
				site.getShell().setData("dashBoardDataKey3",dbSystemModel);
				ExtendedDashboardView view = (ExtendedDashboardView) window
						.getActivePage().showView(viewId,
								Integer.toString(instanceNum++),
								IWorkbenchPage.VIEW_ACTIVATE);
				view.init(site);

			} catch (Exception e) {
				MessageDialog.openError(window.getShell(), "Error", "Error opening view:" + e.getMessage());
			}
		}
	}

}
