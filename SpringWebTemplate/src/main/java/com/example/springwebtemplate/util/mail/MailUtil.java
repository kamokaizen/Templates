package com.example.springwebtemplate.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;

public class MailUtil {
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private JavaMailSender mailSenderFacebook;
	
	@Autowired
	private JavaMailSender mailSenderGmail;

	@Autowired
	private JavaMailSender mailSenderHotmail;
	
	@Autowired
	private JavaMailSender mailSenderYahoo;
	
	private static final Logger logger = LoggerFactory
			.getLogger(MailUtil.class);

	public void sendMail(String from, String to, String subject, String msg, NotificationTypeEnum type) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		getActiveMailSender(type).send(message);
	}

	public void sendMailWithAttachment(String from, String to, String subject,
			String msg, String file, NotificationTypeEnum type) {
		JavaMailSender activeMailSender = getActiveMailSender(type);
		MimeMessage message = activeMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg);

			FileSystemResource fileSysyemResource = new FileSystemResource(file);
			helper.addAttachment(fileSysyemResource.getFilename(),
					fileSysyemResource);

		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		activeMailSender.send(message);
	}

	/**
	 * Utility method to send email with attachment
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public void sendAttachmentEmail(String from, String toEmail,
			String subject, String body,
			List<MailAttachmentUnit> attachmentUnits,
			NotificationTypeEnum type) {
		try {
			JavaMailSender activeMailSender = getActiveMailSender(type);
			MimeMessage msg = activeMailSender.createMimeMessage();
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(from, "no-reply"));
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail, false));

			Multipart multipart = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.addHeader("Content-type", "text/HTML; charset=UTF-8");
			htmlPart.setContent(body, "text/html; charset=UTF-8");
			multipart.addBodyPart(htmlPart);

			if (attachmentUnits != null && attachmentUnits.size() > 0) {
				for (MailAttachmentUnit unit : attachmentUnits) {
					MimeBodyPart imagePart = new MimeBodyPart();
					imagePart.setDataHandler(new DataHandler(unit
							.getDataSource()));
					imagePart.setFileName(unit.getFilename());
					imagePart.setContentID("<" + unit.getCid() + ">");
					imagePart.setDisposition(MimeBodyPart.INLINE);
					multipart.addBodyPart(imagePart);
				}
			}

			msg.setContent(multipart);
			activeMailSender.send(msg);
			System.out.println("Email Sent Successfully with attachment!!");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void sendMailWithAttachments(String from, String to, String subject,
			String msg, Map<String, DataSource> files, NotificationTypeEnum type) {
		JavaMailSender activeMailSender = getActiveMailSender(type);
		MimeMessage message = activeMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true,
					"UTF-8");

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg, true);

			if (files != null && files.size() > 0) {
				for (Entry<String, DataSource> file : files.entrySet()) {
					helper.addAttachment(file.getKey(), file.getValue());
				}
			}

		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		activeMailSender.send(message);
	}

	public void sendMailHtml(String from, String to, String subject, String msg, NotificationTypeEnum type)
			throws MessagingException {
		JavaMailSender activeMailSender = getActiveMailSender(type);
		MimeMessage message = activeMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(from);
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(msg, true);
		activeMailSender.send(message);
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public JavaMailSender getMailSenderFacebook() {
		return mailSenderFacebook;
	}

	public void setMailSenderFacebook(JavaMailSender mailSenderFacebook) {
		this.mailSenderFacebook = mailSenderFacebook;
	}

	public JavaMailSender getMailSenderGmail() {
		return mailSenderGmail;
	}

	public void setMailSenderGmail(JavaMailSender mailSenderGmail) {
		this.mailSenderGmail = mailSenderGmail;
	}

	public JavaMailSender getMailSenderHotmail() {
		return mailSenderHotmail;
	}

	public void setMailSenderHotmail(JavaMailSender mailSenderHotmail) {
		this.mailSenderHotmail = mailSenderHotmail;
	}
	
	public JavaMailSender getMailSenderYahoo() {
		return mailSenderYahoo;
	}

	public void setMailSenderYahoo(JavaMailSender mailSenderYahoo) {
		this.mailSenderYahoo = mailSenderYahoo;
	}

	private JavaMailSender getActiveMailSender(NotificationTypeEnum type){
		if(type == null){
			logger.debug("Default sender will be used as main sender");
			return mailSender;
		}
		
		switch (type) {
		case FACEBOOK:
			logger.debug("Facebook sender will be used as main sender");
			return mailSenderFacebook;
		case GMAIL:
			logger.debug("Gmail sender will be used as main sender");
			return mailSenderGmail;
		case HOTMAIL:
			logger.debug("Hotmail sender will be used as main sender");
			return mailSenderHotmail;
		case YAHOO:
			logger.debug("Yahoo sender will be used as main sender");
			return mailSenderYahoo;
		default:
			logger.debug("Default sender will be used as main sender");
			return mailSender;
		}
	}
}
