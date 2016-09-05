package com.example.springwebtemplate.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.dao.AbstractDao;
import com.example.springwebtemplate.dao.UserActivityDao;
import com.example.springwebtemplate.dbo.UserActivityDbo;
import com.example.springwebtemplate.service.UserActivityService;

@Service("userActivityService")
@Transactional
public class UserActivityServiceImpl extends
		AbstractServiceImpl<UserActivityDbo, Long> implements
		UserActivityService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserActivityServiceImpl.class);

	@Autowired
	private UserActivityDao userActivityDao;

	@Override
	public void saveUserActivity(UserActivityDbo userActivity) {
		this.userActivityDao.saveUserActivity(userActivity);
	}

	@Override
	public UserActivityDbo findUserActivityById(long userActivityId) {
		return this.userActivityDao.findById(userActivityId);
	}

	@Override
	public List<UserActivityDbo> findUserActivitiesByAuthId(String authId) {
		return this.userActivityDao.findByAuthorizationId(authId);
	}

	@Override
	public List<UserActivityDbo> findUserActivitiesByUsername(String username) {
		return this.userActivityDao.findByUser(username);
	}

	@Override
	public List<UserActivityDbo> findUserActivitiesByEmail(String email) {
		return this.userActivityDao.findByEmail(email);
	}

	@Override
	public List<UserActivityDbo> getAllUserActivities() {
		return this.userActivityDao.getAllUserActivities();
	}

	@Override
	public void deleteUserActivityByActivity(UserActivityDbo userActivity) {
		if (userActivity != null) {
			this.userActivityDao.delete(userActivity);
		}
	}

	@Override
	public void deleteUserActivityByUserId(long userId) {
		List<UserActivityDbo> activities = this.userActivityDao.findByUserId(userId);
		if (activities != null && activities.size() > 0) {
			this.userActivityDao.bulkDelete(activities);
			logger.debug("Activities are deleted");
		}
	}

	@Override
	public void deleteUserActivityById(long activityId) {
		UserActivityDbo activity = this.findUserActivityById(activityId);
		if (activity != null) {
			this.deleteUserActivityByActivity(activity);
		}
	}

	@Override
	public AbstractDao<UserActivityDbo, Long> getDao() {
		return this.userActivityDao;
	}

	@Override
	public Date findLatestUserActivityDateByUserId(long userId) {
		return this.userActivityDao.findLatestUserActivityDateByUserId(userId);
	}

}
