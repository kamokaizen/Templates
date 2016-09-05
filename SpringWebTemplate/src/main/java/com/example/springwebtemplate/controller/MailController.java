package com.example.springwebtemplate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springwebtemplate.service.MailService;
import com.example.springwebtemplate.service.NotificationService;
import com.example.springwebtemplate.service.NotificationUserService;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationUserService notificationUserService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(MailController.class);	
}
