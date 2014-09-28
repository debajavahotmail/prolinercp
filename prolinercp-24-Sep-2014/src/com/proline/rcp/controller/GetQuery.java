package com.proline.rcp.controller;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.ViewPart;

import com.proline.core.handler.ProlineDefinitionHandler;
import com.proline.core.handler.QueryBuilder;
import com.proline.defn.schema.models.ProlineDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level.Condition;
import com.proline.models.DBModelProvider;
import com.proline.models.TreeViewerModel;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.controller.ViewController;

public class GetQuery extends Dialog {

  private Text txtFirstName;

  String savedQuery="";
  private String newQuery="";

public String getNewQuery() {
	return newQuery;
}

public void setNewQuery(String newQuery) {
	this.newQuery = newQuery;
}

TreeViewerModel model;
  private Object data;
  protected int levelNo;

  public GetQuery(Shell parentShell) {
    super(parentShell);
  }
  
  protected void configureShell(Shell newShell) {
	    super.configureShell(newShell);
	    newShell.setText("Get Query");
	  }

  @Override
  protected Control createDialogArea(final Composite parent) {
    Composite area = (Composite) super.createDialogArea(parent);
    final Composite container = new Composite(area, SWT.NONE);
    container.setSize(790, 255);
    container.setLayoutData(new GridData(GridData.FILL_BOTH));
    GridLayout layout = new GridLayout(5, false);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    container.setLayout(layout);

    textAndButton(container);
    new Label(container, SWT.NONE);
    new Label(container, SWT.NONE);
    new Label(container, SWT.NONE);
    new Label(container, SWT.NONE);
    return area;
  }
  
  public void textAndButton(final Composite container) {
    GridData dataFirstName = new GridData();
    dataFirstName.grabExcessHorizontalSpace = true;
    dataFirstName.grabExcessVerticalSpace = true;
    dataFirstName.horizontalAlignment = GridData.FILL;
    dataFirstName.verticalAlignment = GridData.FILL;
    
    txtFirstName = new Text(container, SWT.BORDER);
    txtFirstName.setLayoutData(dataFirstName);
  }
  
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
   super.createButtonsForButtonBar(parent);
   Button button_1 = createButton(parent, IDialogConstants.YES_ID,
           IDialogConstants.OK_LABEL, true);
   getButton(IDialogConstants.OK_ID).setText("SubmitQuery");
   getButton(IDialogConstants.YES_ID).setText("GetQuery");
   Button button = getButton(IDialogConstants.YES_ID);
   //button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
   button.setText(" GetTheQuery ");
   System.out.println(button.getLocation()); 
   button.addSelectionListener(new SelectionAdapter() {
     @Override
     public void widgetSelected(SelectionEvent e) {
   	  System.out.println("savedQuery in GQ inside button = "+savedQuery);
   	  txtFirstName.setText(savedQuery);
   	  txtFirstName.setVisible(true);
     }
   });

  }

  @Override
  protected Button createButton(Composite parent, int id,
	        String label, boolean defaultButton) {
	    if (id == IDialogConstants.CANCEL_ID) return null;
	    return super.createButton(parent, id, label, defaultButton);
	}
  
  @Override
  protected Point getInitialSize() {
      return new Point(790, 255);
  }
  
  @Override
  protected boolean isResizable() {
    return true;
  }

  public String saveInput() {
   newQuery=txtFirstName.getText();
   System.out.println("txtFirstName.getText() = "+txtFirstName.getText());
   return newQuery;
  }

  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

} 

