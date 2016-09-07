package com.example.springwebtemplate.util.authentication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springwebtemplate.dbo.UserActivityDbo;
import com.example.springwebtemplate.dbo.UserDbo;
import com.example.springwebtemplate.dbo.enums.UserAuthenticationTypeEnum;
import com.example.springwebtemplate.service.UserActivityService;
import com.example.springwebtemplate.service.UserService;
import com.example.springwebtemplate.util.ip.IPUtil;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private UserActivityService userActivityService;

	@Autowired
	private UserService userService;

	@Autowired
    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

	public CustomAuthenticationFailureHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// do some logic here if you want something to be done whenever the user failed logs in.
		String usernameParameter = usernamePasswordAuthenticationFilter.getUsernameParameter();
	    String username = request.getParameter(usernameParameter);
		
		logger.info("User [ " + username + " ] authentication failed!");

		try {
			if (username != null && username.length() > 0) {
				UserDbo user = this.userService.findUser(username);
				if (user != null) {
					UserActivityDbo loginFailedActivity = new UserActivityDbo();
					loginFailedActivity.setUser(user);
					loginFailedActivity.setUserActivityDate(new Date());
					loginFailedActivity.setUserActivityType(UserAuthenticationTypeEnum.LOG_IN_FAILED);
					loginFailedActivity.setIpAddress(IPUtil.getIpAddr(request));
					loginFailedActivity.setUserLocation(IPUtil.getLocationStringOfIp(loginFailedActivity.getIpAddress()));
					loginFailedActivity.setUserAgent(request.getHeader("user-agent"));
					this.userActivityService.saveUserActivity(loginFailedActivity);
				}
			}
		} catch (Exception e) {
			logger.error("Something went wrong during login: " + e.getMessage());
		}
		
		// set our response to OK status
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		// //we will redirect the user to login page
		response.sendRedirect("login?error=true");
	}

}