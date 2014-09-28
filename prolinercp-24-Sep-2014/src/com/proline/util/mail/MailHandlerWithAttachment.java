package com.proline.util.mail;


import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailHandlerWithAttachment {

	public static void sendForgetPwdMail(String fileName, String emailId, String password)throws Exception {
		Properties props = null;
		if (props == null) {
			props = new Properties();
			props.put("mail.smtp.host", "smtp.live.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.user", "yerriswamy.p@prosyssols.com");
			props.put("mail.smtp.pwd", "yerr1sw4m7");
		}
		Session session = Session.getInstance(props, null);
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("yerriswamy.p@prosyssols.com"));
		msg.setSubject("Auto Generated Mail From Project Systems & Solutions");
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailId));
		Transport transport = session.getTransport("smtp");
		BodyPart message1 = new MimeBodyPart();
		message1.setText("Testing Mail....:-)");

		MimeBodyPart message2 = new MimeBodyPart();

		DataSource source = new FileDataSource(fileName);
		message2.setDataHandler(new DataHandler(source));
		message2.setFileName(fileName);

		Multipart multipart = new MimeMultipart();

		multipart.addBodyPart(message1);
		multipart.addBodyPart(message2);

		msg.setContent(multipart);

		transport.connect("smtp.live.com", 587, "yerriswamy.p@prosyssols.com", "yerr1sw4m7");
		transport.sendMessage(msg, msg.getAllRecipients());
		System.out.println("Mail sent successfully at "+emailId);
		transport.close();
	}

	public static void main(String[] args) {
		try {
			MailHandlerWithAttachment.sendForgetPwdMail("E:\\Act_StaticIP_Req.pdf", "swamydba@gmail.com", "yerriswamy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
