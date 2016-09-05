package com.example.springwebtemplate.dao;

import java.util.Date;
import java.util.List;

import com.example.springwebtemplate.dbo.UserActivityDbo;

public interface UserActivityDao extends AbstractDao<UserActivityDbo, Long>{
    void saveUserActivity(UserActivityDbo userActivity);
    UserActivityDbo findById(long userActivityId);
    List<UserActivityDbo> findByUserId(long userId);
    List<UserActivityDbo> findByUser(String userName);
    List<UserActivityDbo> findByEmail(String email);
    List<UserActivityDbo> findByAuthorizationId(String authorizationId);
    List<UserActivityDbo> getAllUserActivities();
	Date findLatestUserActivityDateByUserId(long userId);
}
