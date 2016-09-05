package com.example.springwebtemplate.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.enums.NotificationStateEnum;

public interface NotificationDao extends AbstractDao<NotificationDbo, Long>{
    void saveNotification(NotificationDbo notification);
    NotificationDbo findNotificationById(long notificationId);
    NotificationDbo findNotificationByAuthId(String authId);
    List<NotificationDbo> findNotificationsByUsername(String userName);
    List<NotificationDbo> findNotificationsByEmail(String email);
    List<NotificationDbo> findNotificationsByState(NotificationStateEnum state);
    List<NotificationDbo> getAllNotifications();
    List<NotificationDbo> findNotificationsByUserId(long userId, Order order, int firstResult, int maxResult);
    List<NotificationDbo> findNotificationsByUserId(long userId);
}
