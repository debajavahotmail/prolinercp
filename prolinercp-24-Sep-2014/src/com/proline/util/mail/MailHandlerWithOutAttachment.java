package com.proline.util.mail;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailHandlerWithOutAttachment {

	public static void sendForgetPwdMail(String empname, String emailId, String password)throws Exception {
		Properties props = null;
		if (props == null) {
			props = new Properties();
			props.put("mail.smtp.host", "smtp.live.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.user", "test@hotmail.com");
			props.put("mail.smtp.pwd", "password");
		}
		Session session = Session.getInstance(props, null);
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("test@hotmail.com"));
		msg.setSubject("Auto Generated Mail");
		msg.setText("Testing Mail");
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress("jatinkansagara@gmail.com"));
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.live.com", 587, "test@hotmail.com", "password");
		transport.sendMessage(msg, msg.getAllRecipients());
		System.out.println("Mail sent successfully at "+emailId);
		transport.close();
	}

	public static void main(String[] args) {
		try {
			MailHandlerWithOutAttachment.sendForgetPwdMail("Test", "to@gmail.com", "Testing");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

