package com.proline.rcp.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.proline.rcp.actions.ChartsMultiAction;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the
 * actions added to a workbench window. Each window will be populated with
 * new actions.
 */
/**
 * @author Debadatta Mishra
 *
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction newWindowAction; 
	private OpenViewAction openViewAction;
	private Action messagePopupAction;
	
	private Action restartAction;
	private ChartsMultiAction chartsMultiAction;
	
	private IWorkbenchAction showHelpAction;
	private IWorkbenchAction searchHelpAction;
	private IWorkbenchAction dynamicHelpAction; 


	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window) {
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

		//Help Contents
		showHelpAction=ActionFactory.HELP_CONTENTS.create(window);
		register(showHelpAction);
		
		//Help Search
		searchHelpAction=ActionFactory.HELP_SEARCH.create(window);
		register(searchHelpAction);
		
		//Dynamic Help
		dynamicHelpAction=ActionFactory.DYNAMIC_HELP.create(window);
		register(dynamicHelpAction);
		
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);
		
		newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
		register(newWindowAction);

		openViewAction = new OpenViewAction(window, "Open Another Message View", View.ID);
		register(openViewAction);

		messagePopupAction = new MessagePopupAction("Open Message", window);
		register(messagePopupAction);
		
		restartAction = new RestartAction("Restart", window);
		register(restartAction);	
		
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
	
		//Help Menu Options
		helpMenu.add(showHelpAction);
		helpMenu.add(searchHelpAction);
		helpMenu.add(dynamicHelpAction);
		helpMenu.add(new Separator());
		menuBar.add(fileMenu);
		// Add a group marker indicating where action set menus will appear.
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(helpMenu);

		// File
		fileMenu.add(newWindowAction);
		fileMenu.add(new Separator());
		        fileMenu.add(messagePopupAction);
		//        fileMenu.add(openViewAction);
		fileMenu.add(new Separator());
		fileMenu.add(exitAction);
		fileMenu.add(restartAction);
		// Help
		helpMenu.add(aboutAction);
	}

	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar, "main"));   
		toolbar.add(messagePopupAction);
		chartsMultiAction = new ChartsMultiAction();
        toolbar.add(chartsMultiAction);
		coolBar.add(chartsMultiAction);  

	}
	//    private StatusLineContributionItem statusItem;

	@Override
	protected void fillStatusLine(IStatusLineManager statusLine) {
		//    	statusItem = new StatusLineContributionItem("website");
		//      statusItem.setText("http://www.prosyssols.com");
		//      statusLine.add(statusItem);
		statusLine.add( new ProlineStatusLineContributionItem("sss"));
//		statusLine.update(true);

	}
}
