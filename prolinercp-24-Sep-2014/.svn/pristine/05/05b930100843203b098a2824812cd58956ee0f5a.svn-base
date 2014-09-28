package com.proline.rcp.ui;

import java.net.URL;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.proline.rcp.constants.TextConstants;

public class ProlineStatusLineContributionItem extends ContributionItem {
	
	public ProlineStatusLineContributionItem(String id) {
	    super(id);
	  }

	  public void fill(Composite parent) {
		  final Link link = new Link(parent, SWT.NONE);
			link.setText(TextConstants.PROLINE_TTILE);
			link.addSelectionListener(new SelectionAdapter() {    
				public void widgetSelected(SelectionEvent e) {
					URL url;
					try {
						url = new URL(TextConstants.PROLINE_URL);
						PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(url);
					} catch (Exception e1) {
						MessageDialog.openError(new Shell(), TextConstants.ERR_TXT, TextConstants.GENERIC_ERR_TXT);
						e1.printStackTrace(); 
					}
					
				}    
			});
	  }
}
