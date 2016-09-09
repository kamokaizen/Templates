package com.example.springwebtemplate.util.authentication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.springwebtemplate.dbo.UserActivityDbo;
import com.example.springwebtemplate.dbo.UserDbo;
import com.example.springwebtemplate.dbo.enums.UserAuthenticationTypeEnum;
import com.example.springwebtemplate.dbo.enums.UserRoleEnum;
import com.example.springwebtemplate.service.UserActivityService;
import com.example.springwebtemplate.service.UserService;
import com.example.springwebtemplate.util.SpringPropertiesUtil;
import com.example.springwebtemplate.util.ip.IPUtil;

public class CustomAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		// do some logic here if you want something to be done whenever
		// the user successfully logs in.
		String redirectPage = SpringPropertiesUtil.getProperty("accessDeniedPath");
		
		HttpSession session = httpServletRequest.getSession();
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		session.setAttribute("username", authUser.getUsername());
		session.setAttribute("authorities", authentication.getAuthorities());
		
		if(authUser != null){
			UserDbo user = this.userService.findUser(authUser.getUsername());
			session.setAttribute("userId", user.getUserId());
			
			if(user != null){
				redirectPage = determineTargetUrl(user);				
				//save user login activity
				UserActivityDbo activity = new UserActivityDbo();
				activity.setUser(user);
				activity.setUserActivityDate(new Date());
				activity.setUserActivityType(UserAuthenticationTypeEnum.LOG_IN);
				activity.setIpAddress(IPUtil.getIpAddr(httpServletRequest));
				activity.setUserLocation(IPUtil.getLocationStringOfIp(activity.getIpAddress()));
				activity.setUserAgent(httpServletRequest.getHeader("user-agent"));
				this.userActivityService.saveUserActivity(activity);	
			}
		}

		// set our response to OK status
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		// //we will redirect the user after successfully login
		httpServletResponse.sendRedirect(redirectPage);
		
		logger.info("User [ " + authUser.getUsername() + " ] authentication succeeded!");
	}

	  /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    private String determineTargetUrl(UserDbo user) {
        String url = "";
 
        if (user.getRole() == UserRoleEnum.ROLE_ANONYMOUS) {
            url = SpringPropertiesUtil.getProperty("accessDeniedPath");
        } else if (user.getRole() == UserRoleEnum.ROLE_ADMIN) {
            url = SpringPropertiesUtil.getProperty("adminPath");
        } else if (user.getRole() == UserRoleEnum.ROLE_USER) {
            url = SpringPropertiesUtil.getProperty("homePath");
        } else {
            url = SpringPropertiesUtil.getProperty("accessDeniedPath");
        }
 
        return url;
    }
	
}
