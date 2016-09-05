package com.example.springwebtemplate.service;

import java.util.List;

import com.example.springwebtemplate.dbo.NotificationUserDbo;

public interface NotificationUserService extends AbstractService<NotificationUserDbo>{
    void saveNotificationUser(NotificationUserDbo notificationUser);
    NotificationUserDbo findNotificationUserById(long notificationUserId);
    NotificationUserDbo findNotificationUserByUsernameInAllItems(String userName);
    NotificationUserDbo findNotificationUserByEmailInAllItems(String email);
    List<NotificationUserDbo> getAllNotificationUsers();
    List<NotificationUserDbo> searchNotificationUsers(String searchText, int pageNumber);
	void deleteNotificationUsersById(long userId);
}
