package com.example.springwebtemplate.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.springwebtemplate.dao.UserActivityDao;
import com.example.springwebtemplate.dao.base.AbstractDaoImpl;
import com.example.springwebtemplate.dbo.UserActivityDbo;
import com.example.springwebtemplate.dbo.enums.UserAuthenticationTypeEnum;

@Repository
public class UserActivityDaoImpl extends AbstractDaoImpl<UserActivityDbo, Long> implements UserActivityDao {

	protected UserActivityDaoImpl() {
		super(UserActivityDbo.class);
	}

	@Override
	public void saveUserActivity(UserActivityDbo userActivity) {
		saveOrUpdate(userActivity);
	}

	@Override
	public UserActivityDbo findById(long userActivityId) {
    	try{
            List<UserActivityDbo> activities = findByCriteria(Restrictions.eq("userActivityId", userActivityId), null);
            if(activities != null && activities.size() > 0)
            	return activities.get(0);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public List<UserActivityDbo> findByUser(String userName) {
    	try{    		
    		return findByCriteria(Restrictions.like("user.username", userName, MatchMode.EXACT), null);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public List<UserActivityDbo> findByAuthorizationId(String authorizationId) {
    	try{    		
    		return findByCriteria(Restrictions.like("user.authorizationId", authorizationId, MatchMode.EXACT), null);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public List<UserActivityDbo> findByUserId(long userId) {
    	try{    		
    		return findByCriteria(Restrictions.eq("user.userId", userId), null);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}
	
	@Override
	public List<UserActivityDbo> getAllUserActivities() {
		Conjunction andConjuction = Restrictions.conjunction();
		andConjuction.add(Restrictions.eq("deleted",false));
		List<UserActivityDbo> activities = findByCriteria(andConjuction, null);
		return activities;
	}

	@Override
	public List<UserActivityDbo> findByEmail(String email) {
    	try{    		
    		return findByCriteria(Restrictions.like("user.email", email, MatchMode.EXACT), null);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public Date findLatestUserActivityDateByUserId(long userId) {
    	try{ 
    		// This method returns the last logout time of user which id is equal to userId. 
    		// User is not the notification user. (Administrator user) 
    		Criterion criterionUserId = Restrictions.eq("user.id", userId);
    		Criterion criterionLogout = Restrictions.eq("userActivityType", UserAuthenticationTypeEnum.LOG_OUT);
    		Criterion andCriterion = Restrictions.and(criterionUserId,criterionLogout);
    		Criterion deletedFalse = Restrictions.eq("deleted", false);
            Criterion andDeletedFalseCriterion = Restrictions.and(andCriterion,deletedFalse);
            Criteria criteria = getCurrentSession().createCriteria(UserActivityDbo.class);
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            criteria.setProjection(Projections.max("userActivityDate"));
            criteria.add(andDeletedFalseCriterion);
            List<?> list = criteria.list();
            if(list != null && list.size() > 0){
            	return (Date) list.get(0);
            }
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
		return null;
	}
}
