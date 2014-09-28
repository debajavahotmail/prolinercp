package com.proline.rcp.actions;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.proline.models.dashboards.DashBoardChart;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.ui.Activator;
import com.proline.rcp.util.ChartUtils;
import com.proline.rcp.views.ChartView;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ChartsMultiAction extends Action implements IMenuCreator,IWorkbenchAction {

	private Menu _menu;

	public ChartsMultiAction() {
		super();
		setMenuCreator(this);
		setImageDescriptor(FileConstants.CHART_LOGO_IMG);
		setToolTipText("Dashboard charts");
	}

	public void dispose() {
		if(_menu != null)
			_menu.dispose();
	}

	final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

	public Menu getMenu(Control parent) {
		_menu = new Menu(parent);

		List<DashBoardChart> chartsList = ChartUtils.getListOfCharts();	
		for(DashBoardChart chart : chartsList) {
			//			String formedTitle = chart.getType()+"-"+chart.getTitle()+"-"+chart.getBatchDetail();
			ChartsOpenViewAction popAction = new ChartsOpenViewAction(window, chart, ChartView.ID);
			popAction.setImageDescriptor(Activator.getImageDescriptor(chart.getType().getImage()));
			ActionContributionItem item= new ActionContributionItem(popAction);
			item.fill(_menu, -1);
		}
		return _menu;
	}

	public Menu getMenu(Menu menu) {
		return menu;
	}

	public void run() {

	}

}