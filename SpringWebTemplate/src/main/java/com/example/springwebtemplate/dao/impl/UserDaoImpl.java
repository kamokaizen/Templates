package com.example.springwebtemplate.dao.impl;

import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.springwebtemplate.dao.UserDao;
import com.example.springwebtemplate.dao.base.AbstractDaoImpl;
import com.example.springwebtemplate.dbo.UserDbo;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<UserDbo, Long> implements UserDao {

    protected UserDaoImpl() {
        super(UserDbo.class);
    }

	@Override
	public List<UserDbo> getAllUsers() {
		Conjunction andConjuction = Restrictions.conjunction();
		andConjuction.add(Restrictions.eq("deleted",false));
		List<UserDbo> users = findByCriteria(andConjuction, null);
		return users;
	}
	
	@Override
	public void saveUser(UserDbo user) {
		// TODO Auto-generated method stub
		saveOrUpdate(user);
	}

	@Override
	public UserDbo findUser(String userName) {
    	try{
            List<UserDbo> users = findByCriteria(Restrictions.like("username", userName, MatchMode.EXACT), null);
            if(users != null && users.size() > 0)
            	return users.get(0);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public UserDbo findById(long userId) {
    	try{
            List<UserDbo> users = findByCriteria(Restrictions.eq("userId", userId), null);
            if(users != null && users.size() > 0)
            	return users.get(0);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public UserDbo findByAuthorizationId(String authorizationId) {
    	try{
            List<UserDbo> users = findByCriteria(Restrictions.like("authorizationId", authorizationId, MatchMode.EXACT), null);
            if(users != null && users.size() > 0)
            	return users.get(0);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}

	@Override
	public UserDbo findByEmail(String email) {
    	try{
            List<UserDbo> users = findByCriteria(Restrictions.like("email", email, MatchMode.EXACT), null);
            if(users != null && users.size() > 0)
            	return users.get(0);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
	}
}
