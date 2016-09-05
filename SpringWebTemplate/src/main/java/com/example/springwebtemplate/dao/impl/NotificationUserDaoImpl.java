package com.example.springwebtemplate.dao.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.springwebtemplate.dao.NotificationUserDao;
import com.example.springwebtemplate.dao.base.AbstractDaoImpl;
import com.example.springwebtemplate.dbo.NotificationUserDbo;

@Repository
public class NotificationUserDaoImpl extends
		AbstractDaoImpl<NotificationUserDbo, Long> implements
		NotificationUserDao {

    protected NotificationUserDaoImpl() {
        super(NotificationUserDbo.class);
    }

	@Override
	public void saveNotificationUser(NotificationUserDbo notificationUser) {
		saveOrUpdate(notificationUser);
	}

	@Override
	public NotificationUserDbo findNotificationUserById(long notificationUserId) {
    	try{
            List<NotificationUserDbo> notifications = findByCriteria(Restrictions.eq("notificationUserId", notificationUserId), null);
            if(notifications != null && notifications.size() > 0)
            	return notifications.get(0);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public NotificationUserDbo findNotificationUserByUsernameInAllItems(String userName) {
    	try{
            List<NotificationUserDbo> notifications = findByCriteriaInAllItems(Restrictions.like("username", userName, MatchMode.EXACT), null);
            if(notifications != null && notifications.size() > 0)
            	return notifications.get(0);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public NotificationUserDbo findNotificationUserByEmailInAllItems(String email) {
    	try{
            List<NotificationUserDbo> notifications = findByCriteriaInAllItems(Restrictions.like("email", email, MatchMode.EXACT), null);
            if(notifications != null && notifications.size() > 0)
            	return notifications.get(0);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public List<NotificationUserDbo> getAllNotificationUsers() {
		return getAll();
	}
}
