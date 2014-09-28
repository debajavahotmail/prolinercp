package com.proline.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSendTo {
	private String SMTP_PORT = "587";
	private String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	 // SUBSTITUTED ISP'S MAIL SERVER
	private String SMTP_HOST_NAME = "smtp.live.com"; 
	private String contentType = "text/html";	
	private Properties smtpProperties;
	public MailSendTo() {
		System.out.println("inside MailSendTo"); 
		initProperties();
	}	
	
	private void initProperties(){
		System.out.println("inside intiProp"); 
		smtpProperties = new Properties(); 
		
		smtpProperties.setProperty("mail.transport.protocol", "smtp");
		smtpProperties.setProperty("mail.host", "smtp.live.com");
		
		 // If using static Transport.send(), 
        // need to specify which host to send it to 
		smtpProperties.put("mail.smtp.host", SMTP_HOST_NAME); 
		// Enable the authentication by setting it to true 
		
		smtpProperties.put("mail.smtp.starttls.enable", "true");
		
		smtpProperties.put("mail.smtp.auth", "true");  
		// To see what is going on behind the scene 
		smtpProperties.put("mail.smtp.debug", "true");
		smtpProperties.put("mail.debug", "true"); 
		smtpProperties.put("mail.smtp.port", SMTP_PORT); 
		/*smtpProperties.put("mail.smtp.socketFactory.port", SMTP_PORT);   
		smtpProperties.put ("mail.smtp.socketFactory.class", SSL_FACTORY); */ 		
		smtpProperties.put("mail.smtp.socketFactory.fallback", "false");  
		
		
	}
	 public static String send(String to, final String from, final String pwd, String subject,String body) {
			  
		 MailSendTo mailSendTo = new MailSendTo();
	    try
	    {
	      Properties props = mailSendTo.getSmtpProperties() ;
	      // -- Attaching to default Session, or we could start a new one --
	      Session session = Session.getDefaultInstance(props,
	                new javax.mail.Authenticator() {
	 
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(from, pwd);
	            } 
	        });
	      /*
	       * 
	       * 
	       * 
	       */
	      // -- Create a new message --
	      Message msg = new MimeMessage(session);
	      // -- Set the FROM and TO fields --
	      msg.setFrom(new InternetAddress(from)); 
	      msg.setRecipients(Message.RecipientType.TO, 
	        InternetAddress.parse(to, false)); 
	      // -- Set the subject and body text --
	      msg.setSubject(subject);
	      msg.setText(body);
	      msg.setSentDate(new Date());
	      // -- Send the message --
	      Transport.send(msg);
	      System.out.println("Message sent OK.");
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	      return Constants.FAILURE_TO_SENDMAIL;
	    }
	    return Constants.SUCCESS; 
	  }
	public Properties getSmtpProperties() { 
		return smtpProperties;  
	}
	public void setSmtpProperties(Properties smtpProperties) {
		this.smtpProperties = smtpProperties;  
	}
}
