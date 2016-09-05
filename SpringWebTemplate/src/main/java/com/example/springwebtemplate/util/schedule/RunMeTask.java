package com.example.springwebtemplate.util.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.enums.NotificationStateEnum;
import com.example.springwebtemplate.service.MailService;
import com.example.springwebtemplate.service.NotificationService;

public class RunMeTask {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	MailService mailService;
			
	private static final Logger logger = LoggerFactory.getLogger(RunMeTask.class);
	
	public void dailyCheckNotificationStates() {
		//Active notifications are found
		List<NotificationDbo> activeNotifications = this.notificationService.findNotificationsByState(NotificationStateEnum.ACTIVE);
		if(activeNotifications != null && activeNotifications.size() > 0){
			logger.info("Total active notification number:  [ " +  activeNotifications.size() +" ] ");
			logger.info("Active notification state check job is finished.");
			return;
		}
		logger.info("No any active notification is found. ");
	}
}