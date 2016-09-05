package com.example.springwebtemplate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.controller.response.UserWebModel;
import com.example.springwebtemplate.dao.AbstractDao;
import com.example.springwebtemplate.dao.UserDao;
import com.example.springwebtemplate.dbo.UserDbo;
import com.example.springwebtemplate.dbo.enums.UserRoleEnum;
import com.example.springwebtemplate.service.CityService;
import com.example.springwebtemplate.service.UserService;
import com.example.springwebtemplate.util.BCryptPasswordEncoder;

@Service("userService")
@Transactional
public class UserServiceImpl extends AbstractServiceImpl<UserDbo, Long> implements UserService, UserDetailsService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private CityService cityService;

	@Override
	public UserDbo findById(long id) {
		return userDao.findById(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.info("Authentication starts for user: [ " + username + " ]");
		Date startDate = new Date();
		
		UserDbo findUser = userDao.findUser(username);
		List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();

		if(findUser != null){
			int index = 0;
			// Check user role
			if(findUser.getRole() == UserRoleEnum.ROLE_USER){
				authority.add(index, new GrantedAuthorityImpl("ROLE_USER"));
				index ++;
			}
			else if(findUser.getRole() == UserRoleEnum.ROLE_ADMIN){
				authority.add(index, new GrantedAuthorityImpl("ROLE_USER"));
				index ++;
				authority.add(index, new GrantedAuthorityImpl("ROLE_ADMIN"));
			}
			else if(findUser.getRole() == UserRoleEnum.ROLE_ANONYMOUS){
				authority.add(index, new GrantedAuthorityImpl("ROLE_ANONYMOUS"));
			}
		}

		Date credentialsLoadTime = new Date();
		logger.info("user roles are loaded for user : [ " + username + " ] elapsed time " + (credentialsLoadTime.getTime() - startDate.getTime()) + " ms");
		
		UserDetails user = new User(username, findUser.getPassword(), true, true, true, true, authority);
		
		Date detailsCreationTime = new Date();
		logger.info("user details are created for user : [ " + username + " ] elapsed time " + (detailsCreationTime.getTime() - credentialsLoadTime.getTime()) + " ms");
		
		return user;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveUser(UserDbo user) {
		userDao.saveUser(user);
	}

	@Override
	public boolean updateUser(UserWebModel staffWebModel) {
		UserDbo user = findById(Long.parseLong(staffWebModel.getStaffId()));
		if (user != null) {
			if (staffWebModel.getCityId() != null)
				user.setCity(cityService.findCity(Long.parseLong(staffWebModel
						.getCityId())));

			if (staffWebModel.getName() != null)
				user.setName(staffWebModel.getName());
			if (staffWebModel.getSurname() != null)
				user.setSurname(staffWebModel.getSurname());
			if (staffWebModel.getUsername() != null)
				user.setUsername(staffWebModel.getUsername());
			saveUser(user);
			return true;
		} else
			return false;
	}

	@Override
	public void deleteUser(long userId) {
		UserDbo userWillBeDeleted = findById(userId);
		userWillBeDeleted.setDeleted(true);
		userDao.saveUser(userWillBeDeleted);
	}

	@Override
	public void deleteUser(UserDbo userWillBeDeleted) {
		try {
			userWillBeDeleted.setDeleted(true);
			userDao.saveUser(userWillBeDeleted);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserDbo findUser(String user) {
		return userDao.findUser(user);
	}

	@Override
	public List<UserDbo> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public UserDbo isUserCredentialsValid(String uname, String password) {
		UserDbo user = userDao.findUser(uname);
		if (user != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean passwordValid = encoder.isPasswordValid(user.getPassword(),
					password, null);
			if (passwordValid) {
				return user;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public UserDbo findByAuthorizationId(String authorizationId) {
		return userDao.findByAuthorizationId(authorizationId);
	}

	@Override
	public UserDbo findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public AbstractDao<UserDbo, Long> getDao() {
		return this.userDao;
	}

}