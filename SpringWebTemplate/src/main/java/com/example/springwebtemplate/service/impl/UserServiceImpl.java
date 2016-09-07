package com.example.springwebtemplate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.controller.response.UserDto;
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

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.info("Authentication starts for user: [ " + username + " ]");
		Date startDate = new Date();
		
		UserDetails user = null;
		UserDbo findUser = userDao.findUser(username);
		List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
		
		if(findUser != null){
			int index = 0;
			// Check user role
			if(findUser.getRole() == UserRoleEnum.ROLE_USER){
				authority.add(index, new SimpleGrantedAuthority("ROLE_USER"));
				index ++;
			}
			else if(findUser.getRole() == UserRoleEnum.ROLE_ADMIN){
				authority.add(index, new SimpleGrantedAuthority("ROLE_USER"));
				authority.add(index, new SimpleGrantedAuthority("ROLE_ADMIN"));
				index ++;
			}
			else if(findUser.getRole() == UserRoleEnum.ROLE_ANONYMOUS){
				authority.add(index, new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
			}
			
			Date credentialsLoadTime = new Date();
			logger.info("user roles are loaded for user : [ " + username + " ] elapsed time " + (credentialsLoadTime.getTime() - startDate.getTime()) + " ms");
			
			user = new User(username, findUser.getPassword(), true, true, true, true, authority);
			
			Date detailsCreationTime = new Date();
			logger.info("user details are created for user : [ " + username + " ] elapsed time " + (detailsCreationTime.getTime() - credentialsLoadTime.getTime()) + " ms");
		}
		
		if(user == null){
			authority.add(0, new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
			user = new User(username, "password", true, true, true, true, authority);
		}
		
		return user;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveUser(UserDbo user) {
		userDao.saveUser(user);
	}

	@Override
	public boolean updateUser(UserDto userDto) {
		UserDbo user = findById(userDto.getUserId());
		if (user != null) {
			if (userDto.getCity() != 0)
				user.setCity(cityService.findCity(userDto.getCity()));

			if (userDto.getName() != null)
				user.setName(userDto.getName());
			if (userDto.getSurname() != null)
				user.setSurname(userDto.getSurname());
			if (userDto.getUsername() != null)
				user.setUsername(userDto.getUsername());
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