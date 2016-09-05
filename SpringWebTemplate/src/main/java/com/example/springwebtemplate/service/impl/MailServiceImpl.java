package com.example.springwebtemplate.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;
import com.example.springwebtemplate.service.MailService;
import com.example.springwebtemplate.util.ImageUtil;
import com.example.springwebtemplate.util.SpringPropertiesUtil;
import com.example.springwebtemplate.util.StreamUtil;
import com.example.springwebtemplate.util.mail.MailAttachmentUnit;
import com.example.springwebtemplate.util.mail.MailUtil;

@Service("mailService")
@Transactional
public class MailServiceImpl implements MailService{

	private static final Logger logger = LoggerFactory
			.getLogger(MailServiceImpl.class);
	
	@Autowired
	MailUtil mailUtil;
	
	@Override
	public void sendMail(String from, String to, String subject, String message, NotificationTypeEnum type) {		
		this.mailUtil.sendMail(from, to, subject, message, type);
	}

	@Override
	public void sendHtmlMail(String from, String to, String subject,
			String message, NotificationTypeEnum type) throws MessagingException {
		this.mailUtil.sendMailHtml(from, to, subject, message, type);
	}

	@Override
	public void sendMailWithAttachment(String from, String to, String subject,
			String message, String file, NotificationTypeEnum type) {
		this.mailUtil.sendMailWithAttachment(from, to, subject, message, file, type);
	}
	
	@Override
	public void sendAttachmentEmail(String from, String toEmail, String subject, String body, List<MailAttachmentUnit> attachments, NotificationTypeEnum type){
		this.mailUtil.sendAttachmentEmail(from, toEmail, subject, body, attachments, type);
	}
	
	@Override
	public void sendMailWithAttachments(String from, String to, String subject,
			String message, Map<String, DataSource> files, NotificationTypeEnum type) {
		this.mailUtil.sendMailWithAttachments(from, to, subject, message, files, type);
	}
	
	@Override
	public void sendNotificationMail(NotificationDbo notification) throws MessagingException{
		switch (notification.getNotificationType()) {
		case FACEBOOK:
			this.sendFacebookNotificationMail(notification);
			break;
		case GMAIL:
			this.sendGmailNotificationMail(notification);
			break;
		case HOTMAIL:
			this.sendHotmailNotificationMail(notification);
			break;
		case YAHOO:
			this.sendYahooNotificationMail(notification);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void sendFacebookNotificationMail(NotificationDbo notification) throws MessagingException{
		String emailContent = null;
		try {
			emailContent = new String(notification.getEmailContent(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.sendHtmlMail(notification.getEmailfrom(), notification.getEmailto(), notification.getEmailSubject(), emailContent, notification.getNotificationType());
	}
	
	@Override
	public void sendGmailNotificationMail(NotificationDbo notification) throws MessagingException{
		String emailContent = null;
		try {
			emailContent = new String(notification.getEmailContent(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<MailAttachmentUnit> mailAttachmentUnits = notification.getMailAttachmentUnits();
		if(mailAttachmentUnits != null && mailAttachmentUnits.size() == 5){
			try {
				mailAttachmentUnits.get(0).setDataSource(new ByteArrayDataSource(StreamUtil.getStream(mailAttachmentUnits.get(0).getFilepath()), "image/png"));
				InputStream stream = new ByteArrayInputStream(notification.getNotificationUser().getUserImage()); 
				mailAttachmentUnits.get(1).setDataSource(new ByteArrayDataSource(ImageUtil.resizeImageWithWidthAndHeight(stream, Integer.parseInt(SpringPropertiesUtil.getProperty("GmailProfileImageWidth")), Integer.parseInt(SpringPropertiesUtil.getProperty("GmailProfileImageHeight"))), "image/png"));
				mailAttachmentUnits.get(2).setDataSource(new ByteArrayDataSource(StreamUtil.getStream(mailAttachmentUnits.get(2).getFilepath()), "image/png"));
				mailAttachmentUnits.get(3).setDataSource(new ByteArrayDataSource(StreamUtil.getStream(mailAttachmentUnits.get(3).getFilepath()), "image/png"));
				mailAttachmentUnits.get(4).setDataSource(new ByteArrayDataSource(StreamUtil.getStream(mailAttachmentUnits.get(4).getFilepath()), "image/png"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		notification.setEmailSubject(notification.getNotificationOs().getKey() + " cihazdan yeni oturum açma işlemi");
		this.sendAttachmentEmail(notification.getEmailfrom(), notification.getEmailto(), notification.getEmailSubject(), emailContent, mailAttachmentUnits, notification.getNotificationType());
	}
	
	@Override
	public void sendHotmailNotificationMail(NotificationDbo notification) throws MessagingException{
		String emailContent = null;
		try {
			emailContent = new String(notification.getEmailContent(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<MailAttachmentUnit> mailAttachmentUnits = notification.getMailAttachmentUnits();
		if(mailAttachmentUnits != null && mailAttachmentUnits.size() == 2){
			try {
				mailAttachmentUnits.get(0).setDataSource(new ByteArrayDataSource(StreamUtil.getStream(mailAttachmentUnits.get(0).getFilepath()), "image/png"));
				InputStream stream = new ByteArrayInputStream(notification.getNotificationUser().getUserImage()); 
				mailAttachmentUnits.get(1).setDataSource(new ByteArrayDataSource(ImageUtil.resizeImageWithWidthAndHeight(stream, Integer.parseInt(SpringPropertiesUtil.getProperty("GmailProfileImageWidth")), Integer.parseInt(SpringPropertiesUtil.getProperty("GmailProfileImageHeight"))), "image/png"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		this.sendAttachmentEmail(notification.getEmailfrom(), notification.getEmailto(), notification.getEmailSubject(), emailContent, mailAttachmentUnits, notification.getNotificationType());
	}
	
	@Override
	public void sendYahooNotificationMail(NotificationDbo notification) throws MessagingException{
		String emailContent = null;
		try {
			emailContent = new String(notification.getEmailContent(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<MailAttachmentUnit> mailAttachmentUnits = notification.getMailAttachmentUnits();
		if(mailAttachmentUnits != null && mailAttachmentUnits.size() == 2){
			try {
				mailAttachmentUnits.get(0).setDataSource(new ByteArrayDataSource(StreamUtil.getStream(mailAttachmentUnits.get(0).getFilepath()), "image/png"));
				InputStream stream = new ByteArrayInputStream(notification.getNotificationUser().getUserImage()); 
				mailAttachmentUnits.get(1).setDataSource(new ByteArrayDataSource(ImageUtil.resizeImageWithWidthAndHeight(stream, Integer.parseInt(SpringPropertiesUtil.getProperty("GmailProfileImageWidth")), Integer.parseInt(SpringPropertiesUtil.getProperty("GmailProfileImageHeight"))), "image/png"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		this.sendAttachmentEmail(notification.getEmailfrom(), notification.getEmailto(), notification.getEmailSubject(), emailContent, mailAttachmentUnits, notification.getNotificationType());
	}
}
