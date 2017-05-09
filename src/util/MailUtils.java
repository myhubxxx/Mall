package util;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import cn.itcast.mail.AttachBean;
import cn.itcast.mail.Mail;

public class MailUtils {
	public static Session createSession(String host, final String username, final String password) {
		Properties prop = new Properties();
		prop.setProperty("mail.host", host);
		prop.setProperty("mail.smtp.auth", "true");

		
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		
		
		return Session.getInstance(prop, auth);
	}

	public static void send(Session session, final Mail mail) throws MessagingException,
			IOException {

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mail.getFrom()));
		msg.addRecipients(RecipientType.TO, mail.getToAddress());

		
		String cc = mail.getCcAddress();
		if (!cc.isEmpty()) {
			msg.addRecipients(RecipientType.CC, cc);
		}

		
		String bcc = mail.getBccAddress();
		if (!bcc.isEmpty()) {
			msg.addRecipients(RecipientType.BCC, bcc);
		}

		msg.setSubject(mail.getSubject());

		MimeMultipart parts = new MimeMultipart();

		MimeBodyPart part = new MimeBodyPart();
		part.setContent(mail.getContent(), "text/html;charset=utf-8");
		parts.addBodyPart(part);

		List<AttachBean> attachBeanList = mail.getAttachs();
		if (attachBeanList != null) {
			for (AttachBean attach : attachBeanList) {
				MimeBodyPart attachPart = new MimeBodyPart();
				attachPart.attachFile(attach.getFile());
				attachPart.setFileName(MimeUtility.encodeText(attach
						.getFileName()));
				String cid = attach.getCid();
				if(cid != null) {
					attachPart.setContentID(cid);
				}
				parts.addBodyPart(attachPart);
			}
		}

		msg.setContent(parts);
		Transport.send(msg);
	}
}