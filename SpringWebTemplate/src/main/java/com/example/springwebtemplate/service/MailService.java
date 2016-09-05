package com.example.springwebtemplate.service;

import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;

import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;
import com.example.springwebtemplate.util.mail.MailAttachmentUnit;

public interface MailService {
	void sendMail(String from, String to, String subject, String message, NotificationTypeEnum type);
	void sendMailWithAttachment(String from, String to, String subject, String message, String file, NotificationTypeEnum type);
	void sendAttachmentEmail(String from, String toEmail, String subject, String body, List<MailAttachmentUnit> attachments, NotificationTypeEnum type);
	void sendMailWithAttachments(String from, String to, String subject, String message, Map<String, DataSource> files, NotificationTypeEnum type);
	void sendHtmlMail(String from, String to, String subject, String message, NotificationTypeEnum type) throws MessagingException;
	void sendNotificationMail(NotificationDbo notification) throws MessagingException;
	void sendFacebookNotificationMail(NotificationDbo notification) throws MessagingException;
	void sendGmailNotificationMail(NotificationDbo notification) throws MessagingException;
	void sendHotmailNotificationMail(NotificationDbo notification) throws MessagingException;
	void sendYahooNotificationMail(NotificationDbo notification) throws MessagingException;
}
