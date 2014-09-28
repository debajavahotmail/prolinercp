package com.proline.rcp.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.eclipse.core.internal.registry.osgi.OSGIUtils;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.container.builders.OSGiManifestBuilderFactory;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.proline.rcp.ui.Activator;
import com.proline.rcp.util.PluginUtil;
import com.proline.rcp.util.ProlineLogger;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) { 
		Display display = PlatformUI.createDisplay();
		try {
			String subStrOfLogFileLocation="workspace/.metadata/.log";
			File file=new File(PluginUtil.getHomePath()+subStrOfLogFileLocation);
			if(file.exists()){
				/*file.delete();*/
				PrintWriter writer;
				try {
					writer = new PrintWriter(file);
					writer.print("");
					writer.close();
					ProlineLogger.logInfo("Clear the content in the file :"+file);
					System.out.println("Clear the content in the file :"+file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			else{
				System.out.println("The file is not exits");
				ProlineLogger.logInfo("The log file is not exits");
			}			
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			
			}
		});
	}
	
}
