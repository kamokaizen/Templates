package com.example.springwebtemplate.service;

import java.util.Date;
import java.util.List;

import com.example.springwebtemplate.dbo.UserActivityDbo;

public interface UserActivityService extends AbstractService<UserActivityDbo>{
    void saveUserActivity(UserActivityDbo userActivity);
    UserActivityDbo findUserActivityById(long userActivityId);
    List<UserActivityDbo> findUserActivitiesByAuthId(String authId);
    List<UserActivityDbo> findUserActivitiesByUsername(String username);
    List<UserActivityDbo> findUserActivitiesByEmail(String email);
    List<UserActivityDbo> getAllUserActivities();
    Date findLatestUserActivityDateByUserId(long userId);
	void deleteUserActivityByActivity(UserActivityDbo userActivity);
	void deleteUserActivityByUserId(long userId);
	void deleteUserActivityById(long activityId);
}
