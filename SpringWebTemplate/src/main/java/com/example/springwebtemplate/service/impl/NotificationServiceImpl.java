package com.example.springwebtemplate.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.dao.AbstractDao;
import com.example.springwebtemplate.dao.NotificationDao;
import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.enums.NotificationStateEnum;
import com.example.springwebtemplate.service.NotificationService;

@Service("notificationService")
@Transactional
public class NotificationServiceImpl extends AbstractServiceImpl<NotificationDbo, Long> implements NotificationService {

	private static final Logger logger = LoggerFactory
			.getLogger(NotificationServiceImpl.class);
	
	@Autowired
	private NotificationDao notificationDao;
	
	@Override
	public void saveNotification(NotificationDbo notification) {
		notificationDao.saveNotification(notification);
	}

	@Override
	public NotificationDbo findNotificationById(long notificationId) {
		return notificationDao.findNotificationById(notificationId);
	}

	@Override
	public NotificationDbo findNotificationByAuthId(String authId) {
		return notificationDao.findNotificationByAuthId(authId);
	}

	@Override
	public List<NotificationDbo> findNotificationsByUsername(String userName) {
		return notificationDao.findNotificationsByUsername(userName);
	}
	
	@Override
	public List<NotificationDbo> findNotificationsByUserId(long userId) {
		return notificationDao.findNotificationsByUserId(userId);
	}

	@Override
	public List<NotificationDbo> findNotificationsByEmail(String email) {
		return notificationDao.findNotificationsByEmail(email);
	}

	@Override
	public List<NotificationDbo> getAllNotifications() {
		return notificationDao.getAll();
	}
	
	@Override
	public AbstractDao<NotificationDbo, Long> getDao() {
		return this.notificationDao;
	}

	@Override
	public void deleteNotificationsByUserId(long userId) {
		List<NotificationDbo> results = this.notificationDao.findNotificationsByUserId(userId);
		this.notificationDao.bulkDelete(results);
		logger.debug("Notifications for userId : " + userId + " are deleted.");
	}

	@Override
	public void deleteNotifications(List<NotificationDbo> notifications) {
		this.notificationDao.bulkDelete(notifications);
		logger.debug("Notifications are deleted.");
	}

	@Override
	public List<NotificationDbo> findNotificationsByState(
			NotificationStateEnum state) {
		return this.notificationDao.findNotificationsByState(state);
	}

	@Override
	public List<NotificationDbo> searchNotifications(String searchText, int pageNumber) {
		return this.search(searchText, new String[]{"emailto","notificationUser.name","notificationUser.surname"}, pageNumber);
	}
}
