package com.proline.rcp.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.proline.rcp.constants.FileConstants;

public class OpenViewAction extends Action {

	private final IWorkbenchWindow window;
	private static int instanceNum = 1;
	private final String viewId;
	private String label = null; 
	

	public OpenViewAction(IWorkbenchWindow window, String label, String viewId) {
		this.window = window;
		this.viewId = viewId;
		setText(label);
		this.label = label;
		setId(ICommandIds.CMD_OPEN);
		setActionDefinitionId(ICommandIds.CMD_OPEN);
		setImageDescriptor(FileConstants.PROLINE_LOGO_IMG); 
	}
	
	public void run()
	{
		run("INVALID");
	}


	public void run(String secondaryId) {
		if(secondaryId.equals("Invalid"))
			System.out.println("Invalid id specified");
		if (window != null) {
			try {
					System.out.println("OVA --> viewid = "+viewId);
					 IViewPart part = window.getActivePage().showView(viewId,secondaryId,IWorkbenchPage.VIEW_ACTIVATE);
					 /*if(View.modelProvider.getDbModelList().size()==0){ //Added if block --> sneha
						 part.getViewSite().getPage().hideView(part);
						 MessageDialog.openInformation(new Shell(),"DB Error","No Data Found/Records Available");
					 }*/
					
				}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	public boolean isOpened(String label) {
		boolean flag = false;
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewReference[] views = page.getViewReferences();
		  for (IViewReference view : views) {
			  System.out.println("Inside the isOpent()"+view.getTitle());
			  if( view.getTitle().equals(label)) {
				  flag = true;
				  return flag;
			  }
		  }
		return flag;
	}

}
