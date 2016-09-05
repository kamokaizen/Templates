package com.example.springwebtemplate.service;

import java.util.List;

import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.enums.NotificationStateEnum;

public interface NotificationService extends AbstractService<NotificationDbo>{
    void saveNotification(NotificationDbo notification);
    NotificationDbo findNotificationById(long notificationId);
    NotificationDbo findNotificationByAuthId(String authId);
    List<NotificationDbo> findNotificationsByUsername(String userName);
    List<NotificationDbo> findNotificationsByUserId(long userId);
    List<NotificationDbo> findNotificationsByEmail(String email);
    List<NotificationDbo> findNotificationsByState(NotificationStateEnum state);
    List<NotificationDbo> getAllNotifications();
	void deleteNotificationsByUserId(long userId);
	void deleteNotifications(List<NotificationDbo> notifications);
	List<NotificationDbo> searchNotifications(String searchText, int pageNumber);
}
