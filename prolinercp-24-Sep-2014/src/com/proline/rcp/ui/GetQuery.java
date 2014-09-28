package com.proline.rcp.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
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

import com.proline.models.TreeViewerModel;

public class GetQuery extends Dialog {

  private Text txtFirstName;

  private String savedQuery="";

//private String newQuery="";
	private TableViewer viewer = null;
	TreeViewerModel model;
	  private Object data;
	  protected int levelNo;

	  public GetQuery(Shell parentShell) {
	    super(parentShell);
	  }
	
	  public String getSavedQuery() {
			return savedQuery;
		}

		public void setSavedQuery(String savedQuery) {
			this.savedQuery = savedQuery;
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
   
   Button submitQuery=getButton(IDialogConstants.OK_ID);
   submitQuery.setText("SubmitQuery");
  /* submitQuery.addSelectionListener(new SelectionAdapter(){
	   public void widgetSelected(SelectionEvent e) {
		  
	   }
   });*/
   
   //getButton(IDialogConstants.YES_ID).setText("GetQuery");
   Button getQuery = getButton(IDialogConstants.YES_ID);
   //button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
   getQuery.setText(" GetTheQuery ");
   System.out.println(getQuery.getLocation()); 
   getQuery.addSelectionListener(new SelectionAdapter() {
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

  private void saveInput() {
   savedQuery=txtFirstName.getText();
   System.out.println("txtFirstName.getText() = "+txtFirstName.getText());
  // return savedQuery;
  }

  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

} 

