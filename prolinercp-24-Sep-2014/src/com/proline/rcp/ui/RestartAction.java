package com.proline.rcp.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.proline.rcp.constants.FileConstants;

public class RestartAction extends Action {

	private final IWorkbenchWindow window;

	RestartAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.RESTART_IDE);
		// Associate the action with a pre-defined command, to allow key
		// bindings.
//		setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
		setImageDescriptor(FileConstants.RESTART_IMG);
	}
 
	public void run() {
		PlatformUI.getWorkbench().restart();
	}
}
