package com.example.springwebtemplate.dao.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.springwebtemplate.dao.NotificationDao;
import com.example.springwebtemplate.dao.base.AbstractDaoImpl;
import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.enums.NotificationStateEnum;

@Repository
public class NotificationDaoImpl extends AbstractDaoImpl<NotificationDbo, Long>
		implements NotificationDao {

	protected NotificationDaoImpl() {
		super(NotificationDbo.class);
	}

	@Override
	public void saveNotification(NotificationDbo notification) {
		saveOrUpdate(notification);
	}

	@Override
	public NotificationDbo findNotificationById(long notificationId) {
		try {
			List<NotificationDbo> notifications = findByCriteria(
					Restrictions.eq("notificationId", notificationId), null);
			if (notifications != null && notifications.size() > 0)
				return notifications.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NotificationDbo findNotificationByAuthId(String authId) {
		try {
			List<NotificationDbo> notifications = findByCriteria(
					Restrictions.like("notificationAuthorizationId", authId,
							MatchMode.EXACT), null);
			if (notifications != null && notifications.size() > 0)
				return notifications.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<NotificationDbo> findNotificationsByUsername(String userName) {
		return findByCriteria(Restrictions.like("notificationUser.username",
				userName, MatchMode.EXACT), null);
	}

	@Override
	public List<NotificationDbo> findNotificationsByEmail(String email) {
		return findByCriteria(Restrictions.like("notificationUser.email",
				email, MatchMode.EXACT), null);
	}

	@Override
	public List<NotificationDbo> findNotificationsByState(
			NotificationStateEnum state) {
		return findByCriteria(Restrictions.eq("notificationState", state), null);
	}
	
	@Override
	public List<NotificationDbo> getAllNotifications() {
		return getAll();
	}

	@Override
	public List<NotificationDbo> findNotificationsByUserId(long userId,
			Order order, int firstResult, int maxResult) {
		List<NotificationDbo> result = null;
		try {
			result = findByCriteria(Restrictions.eq(
					"notificationUser.notificationUserId", userId), order,
					firstResult, maxResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<NotificationDbo> findNotificationsByUserId(long userId) {
		List<NotificationDbo> result = null;
		try {
			result = findByCriteria(Restrictions.eq(
					"notificationUser.notificationUserId", userId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
