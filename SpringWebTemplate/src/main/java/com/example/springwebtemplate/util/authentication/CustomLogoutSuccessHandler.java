package com.example.springwebtemplate.util.authentication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.example.springwebtemplate.dbo.UserActivityDbo;
import com.example.springwebtemplate.dbo.UserDbo;
import com.example.springwebtemplate.dbo.enums.UserAuthenticationTypeEnum;
import com.example.springwebtemplate.service.UserActivityService;
import com.example.springwebtemplate.service.UserService;
import com.example.springwebtemplate.util.ip.IPUtil;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);
	
	public CustomLogoutSuccessHandler() {
		this.setDefaultTargetUrl("/login");
	}
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		try{
			if(authentication != null){
				String username = ((User)authentication.getPrincipal()).getUsername();
				UserDbo user = this.userService.findUser(username);
				if(user != null){
					UserActivityDbo logoutActivity = new UserActivityDbo();
					logoutActivity.setUser(user);
					logoutActivity.setUserActivityDate(new Date());
					logoutActivity.setUserActivityType(UserAuthenticationTypeEnum.LOG_OUT);
					logoutActivity.setIpAddress(IPUtil.getIpAddr(request));
					logoutActivity.setUserLocation(IPUtil.getLocationStringOfIp(logoutActivity.getIpAddress()));
					logoutActivity.setUserAgent(request.getHeader("user-agent"));
					this.userActivityService.saveUserActivity(logoutActivity);
					logger.info("Logout is succeded for user: [ " + username + " ]");
				}
			}
		}
		catch(Exception e){
			logger.error("Something went wrong during logout: " + e.getMessage());
		}
		
		super.onLogoutSuccess(request, response, authentication);
	}
}