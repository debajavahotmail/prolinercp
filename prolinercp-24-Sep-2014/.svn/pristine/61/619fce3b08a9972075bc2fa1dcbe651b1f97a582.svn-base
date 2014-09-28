package com.proline.rcp.util;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;

import com.proline.rcp.ui.Activator;

/**
 * @author Debadatta Mishra
 *
 */
public class PluginUtil {
	
	public static String getHomePath() {
		return Platform.getInstallLocation().getURL().getPath();
	}
	
	public static String getConfigPath() {
		return getHomePath()+File.separator+"config";
	}
	
	public static void showErrorInStatus(String errMsg) {
		
		IWorkbenchPartSite site = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage()
                .getActivePart().getSite();
		
		if (site instanceof IViewSite) {
			
			IStatusLineManager statusLineMgr = ((IViewSite) site).getActionBars().getStatusLineManager();
			statusLineMgr.setErrorMessage(errMsg);
			statusLineMgr.update(true);
        }
	}
	
	/**Utility method to show detailed error message.
	 * @param message
	 * @param ex
	 */
	public static void showErrorInfo( String message , Exception ex) {
		Status status = new Status(
				IStatus.ERROR,
				Activator.PLUGIN_ID,
				"UnExpected Exception, click Details button to see more information",
				ex);
		ErrorDialog.openError(new Shell(), "Error", message, status);
	}

}
