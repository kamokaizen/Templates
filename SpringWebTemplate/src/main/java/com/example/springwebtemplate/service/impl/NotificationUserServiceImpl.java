package com.example.springwebtemplate.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.dao.AbstractDao;
import com.example.springwebtemplate.dao.NotificationUserDao;
import com.example.springwebtemplate.dbo.NotificationUserDbo;
import com.example.springwebtemplate.service.NotificationUserService;

@Service("notificationUserService")
@Transactional
public class NotificationUserServiceImpl extends AbstractServiceImpl<NotificationUserDbo, Long> implements NotificationUserService {

	private static final Logger logger = LoggerFactory
			.getLogger(NotificationUserServiceImpl.class);

	@Autowired
	private NotificationUserDao notificationUserDao;

	@Override
	public void saveNotificationUser(NotificationUserDbo notificationUser) {
		this.notificationUserDao.saveNotificationUser(notificationUser);
	}

	@Override
	public NotificationUserDbo findNotificationUserById(long notificationUserId) {
		return this.notificationUserDao.findNotificationUserById(notificationUserId);
	}

	@Override
	public NotificationUserDbo findNotificationUserByUsernameInAllItems(String userName) {
		return this.notificationUserDao.findNotificationUserByUsernameInAllItems(userName);
	}

	@Override
	public NotificationUserDbo findNotificationUserByEmailInAllItems(String email) {
		return this.notificationUserDao.findNotificationUserByEmailInAllItems(email);
	}

	@Override
	public List<NotificationUserDbo> getAllNotificationUsers() {
		return this.notificationUserDao.getAllNotificationUsers();
	}

	@Override
	public AbstractDao<NotificationUserDbo, Long> getDao() {
		return this.notificationUserDao;
	}

	@Override
	public void deleteNotificationUsersById(long userId) {
		NotificationUserDbo result = this.notificationUserDao.findNotificationUserById(userId);
		this.notificationUserDao.delete(result);
		logger.debug("Notification user for userId : " + userId + " is deleted.");
	}

	@Override
	public List<NotificationUserDbo> searchNotificationUsers(String searchText, int pageNumber) {
		return this.search(searchText, new String[]{"name","surname","username","email"}, pageNumber);
	}

}
