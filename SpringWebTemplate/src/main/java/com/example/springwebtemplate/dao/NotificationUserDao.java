package com.example.springwebtemplate.dao;

import java.util.List;

import com.example.springwebtemplate.dbo.NotificationUserDbo;

public interface NotificationUserDao extends AbstractDao<NotificationUserDbo, Long>{
    void saveNotificationUser(NotificationUserDbo notificationUser);
    NotificationUserDbo findNotificationUserById(long notificationUserId);
    NotificationUserDbo findNotificationUserByUsernameInAllItems(String userName);
    NotificationUserDbo findNotificationUserByEmailInAllItems(String email);
    List<NotificationUserDbo> getAllNotificationUsers();
}