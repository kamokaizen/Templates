package com.example.springwebtemplate.service.impl;

import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;
import com.example.springwebtemplate.service.MailService;
import com.example.springwebtemplate.util.mail.MailAttachmentUnit;
import com.example.springwebtemplate.util.mail.MailUtil;

@Service("mailService")
@Transactional
public class MailServiceImpl implements MailService{

	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	MailUtil mailUtil;
	
	@Override
	public void sendMail(String from, String to, String subject, String message, NotificationTypeEnum type) {
		logger.info("Mail is sending from: " + from + " to: " + to);
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
}
