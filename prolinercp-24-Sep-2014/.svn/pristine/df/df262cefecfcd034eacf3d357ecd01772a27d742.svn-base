package com.proline.util.mail;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.ParseException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

import com.proline.models.DBSystemModel;
import com.proline.models.DatabaseConnectionsModel;
import com.proline.models.MailConnectionsModel;
import com.proline.models.MailSystemModel;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.controller.ViewController;
import com.proline.rcp.ui.View;
import com.proline.rcp.util.ProlineLogger;
import com.proline.util.parsers.DBConnectionSystemParser;
import com.proline.util.parsers.MailConnectionSystemParser;

public class SendMail extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text textFrom;
	private Text textTo;
	private Text textCc;
	private Text textSubject;
	private Text textContent;
	private Text textAttached;
	public View view;
	public String partName;
	String hostName = null;
	String userName = null;
	String port = null;
	String password = null;
	String subject1 = null;
	String content1 = null;
	public ViewController vc;
	
	

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	
	

	  // Strings to show in the Buttons dropdown
	 /* private static final String[] BUTTONS = { "SWT.OK", "SWT.OK | SWT.CANCEL",
	      "SWT.YES | SWT.NO", "SWT.YES | SWT.NO | SWT.CANCEL",
	      "SWT.RETRY | SWT.CANCEL", "SWT.ABORT | SWT.RETRY | SWT.IGNORE"};*/
	
	
	public SendMail(Shell parent,int style) {
		super(parent,style);
		setText("Mail");
	}

	

	public Text getTextFrom() {
		return textFrom;
	}



	public void setTextFrom(Text textFrom) {
		this.textFrom = textFrom;
	}



	public Text getTextTo() {
		return textTo;
	}



	public void setTextTo(Text textTo) {
		this.textTo = textTo;
	}



	public Text getTextCc() {
		return textCc;
	}



	public void setTextCc(Text textCc) {
		this.textCc = textCc;
	}



	public Text getTextSubject() {
		return textSubject;
	}



	public void setTextSubject(Text textSubject) {
		this.textSubject = textSubject;
	}



	public Text getTextContent() {
		return textContent;
	}



	public void setTextContent(Text textContent) {
		this.textContent = textContent;
	}

	public Text getTextAttached() {
		return textAttached;
	}



	public void setTextAttached(Text textAttached) {
		this.textAttached = textAttached;
	}


	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		
		try {
			Map<String,MailSystemModel> mailMoldelMap = getMailConnectionsDetails();
			MailSystemModel mailSysModel = mailMoldelMap.get("proline");
			hostName = mailSysModel.getHostName();
			userName = mailSysModel.getUserName();
			password = mailSysModel.getPassword();
			port = mailSysModel.getPort();
			subject1 = mailSysModel.getSubject();
			content1 = mailSysModel.getContent();
			
			 
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			String exception=e.getMessage();
			MessageDialog.openConfirm(new Shell(), "Error", exception);
			ProlineLogger.logError("SendMail_createContents()",e);
		}
	
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE);
		shell.setSize(741, 457);
		shell.setText(getText());
		
		final Button send = new Button(shell, SWT.FLAT);
		send.setBounds(34, 26, 63, 80);
		send.setText("Send");
		send.addSelectionListener(new SelectionAdapter() {
		     @Override
		     public void widgetSelected(SelectionEvent e) {
		    	
		   	  //System.out.println("savedQuery in GQ inside button = "+savedQuery);
		   	//MailSendTo.send(textTo.getText(),textCc.getText(),userName,password,subject1, content1+""+textContent.getText());
		    try {
		    	//DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				//Date date = new Date();
			
		    
	    	    //sendMail.getParent().forceFocus();
		    	if(content1.equals("") && textContent.getText().equals("")){
		    		 MessageDialog.openInformation(new Shell(), "Confirm", "Send mail without text message  ?");
		    	 
		    	 
			         if(subject1.equals("") && textSubject.getText().equals("")){
				        MessageDialog.openInformation(new Shell(), "Confirm", "Send mail without Subject ?");
			        	//sendMail.getParent().forceFocus();
			         }
				 }
		    	new SendMailUsingAuthentication().postMail(textTo.getText(), textCc.getText(), textSubject.getText(), textContent.getText(), userName, password, vc.file.toString());    
		    	shell.close();		
			 			
			 		 
		        	//sendMail.getParent().forceFocus();
			    	
		    	
		    	
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				String exception=e1.getMessage();		
	        	MessageDialog.openError(new Shell(), "Error", exception);
				ProlineLogger.logInfo("Error"+exception);
			
			} catch (Exception e1){
				
				e1.printStackTrace();
				String exception=e1.getMessage();		
	        	MessageDialog.openError(new Shell(), "Error", exception);
				ProlineLogger.logInfo("Error"+exception);
			}
		    
		    			
		    //File file=new File("C:/MailFiles/pro.xlsx");
		    //file.delete();
		     }
		     
		   });
		
		textFrom = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		textFrom.setBounds(199, 13, 501, 21);
		textFrom.setText(userName);
		
		Label from = new Label(shell, SWT.NONE);
		from.setBounds(126, 16, 55, 15);
		from.setText("From");
		
		textTo = new Text(shell, SWT.BORDER);
		textTo.setTouchEnabled(true);
		textTo.setBounds(199, 50, 501, 21);
		String[] To = {textTo.getText()};
		System.out.println("SendMail--> To = "+To.toString());
		
		
		Label to = new Label(shell, SWT.NONE);
		to.setBounds(126, 53, 55, 15);
		to.setText("To");
		
		textCc = new Text(shell, SWT.BORDER);
		textCc.setBounds(199, 85, 501, 21);
		
		Label cc = new Label(shell, SWT.NONE);
		cc.setBounds(126, 88, 55, 15);
		cc.setText("Cc");
		
		textSubject = new Text(shell, SWT.BORDER);
		textSubject.setBounds(199, 120, 501, 21);
		System.out.println("SendMail --> partName = "+partName);
		textSubject.setText(subject1+" "+vc.file.toString().substring(13));
		textSubject.setVisible(true);
		
		Label subject = new Label(shell, SWT.NONE);
		subject.setBounds(126, 123, 55, 15);
		subject.setText("Subject");
		
		textContent = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		textContent.setBounds(10, 215, 713, 201);
		
		textContent.setText(content1);
		textContent.setVisible(true);
		
		
		Label AttachedFile = new Label(shell, SWT.NONE);
		AttachedFile.setBounds(126, 157, 55, 15);
		AttachedFile.setText("Attached");
		
		textAttached = new Text(shell, SWT.BORDER);
		textAttached.setBounds(199, 154, 501, 21);
		//textAttached.insert(FileConstants.XLS_IMG);
		textAttached.setText(vc.file.toString().substring(13));

	}

	private Map<String,MailSystemModel> getMailConnectionsDetails() {
		MailConnectionSystemParser mailConnParser = new MailConnectionSystemParser();
		MailConnectionsModel mailConnModel = null;
		try {
			ProlineLogger.logInfo("prolinedefinitionHandler class getMailConnectionsDetails()");
			mailConnModel = mailConnParser.getMailConnectionSystemModel(FileConstants.PROLINE_MAIL_CONN_DETAILS);
		} catch (Exception e) {
			e.printStackTrace();
			ProlineLogger.logError(" getMailConnectionsDetails( ) in ProlineDefinitionHandler class..",e);
		}
		return mailConnModel.getMailModelMap();
	}
}
