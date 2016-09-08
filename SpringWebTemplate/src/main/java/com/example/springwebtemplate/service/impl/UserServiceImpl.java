package com.example.springwebtemplate.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	public UserDbo isUserCredentialsValid(String uname, String password) {
		UserDbo user = userDao.findUser(uname);
		if (user != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean passwordValid = encoder.isPasswordValid(user.getPassword(),password, null);
			if (passwordValid) {
				return user;
			} else {
				return null;
			}
		}
		return null;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.info("Authentication starts for user: [ " + username + " ]");
		
		UserDbo user = userDao.findUser(username);
        if(user == null){
            logger.error("User not found on database: [ " + username + " ]");
            throw new UsernameNotFoundException("User not found on database: [ " + username + " ]"); 
        }
        
        return new org.springframework.security.core.userdetails.User(
        		user.getUsername(), 
        		user.getPassword(), 
                true, 
                true, 
                true, 
                true, 
                getGrantedAuthorities(user));
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(UserDbo user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        if(user.getRole() == UserRoleEnum.ROLE_ANONYMOUS){
        	authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		}
        else if(user.getRole() == UserRoleEnum.ROLE_USER){
        	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		else if(user.getRole() == UserRoleEnum.ROLE_ADMIN){
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
        
        logger.info("Authorities for user: [ " + authorities + " ]");
        return authorities;
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