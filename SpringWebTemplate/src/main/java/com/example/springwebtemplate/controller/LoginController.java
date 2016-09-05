package com.example.springwebtemplate.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springwebtemplate.dbo.UserActivityDbo;
import com.example.springwebtemplate.dbo.UserDbo;
import com.example.springwebtemplate.dbo.enums.UserAuthenticationTypeEnum;
import com.example.springwebtemplate.service.CityService;
import com.example.springwebtemplate.service.UserActivityService;
import com.example.springwebtemplate.service.UserService;
import com.example.springwebtemplate.util.SpringPropertiesUtil;
import com.example.springwebtemplate.util.ip.IPUtil;

/**
 * Handles requests for the login page
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	UserActivityService  userActivityService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultProxy(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		UserDbo authenticatedUser = userService.findUser(username);

		String redirectPage = "responsiveviews/home";

		if (authenticatedUser == null) {
			redirectPage = "error403";
			return redirectPage;
		}
		
		model.addAttribute("username", username);
		model.addAttribute("userid", authenticatedUser.getUserId());
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return redirectPage;
	}
		
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String accessdenied(ModelMap model) {
		model.addAttribute("error", "true");
		return "error403";
	}
				
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		model.addAttribute("loginFailed", false);
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return "login";
	}
	
	@RequestMapping(value = "/logoutsuccess", method = RequestMethod.GET)
	public String logoutsuccess(ModelMap model, HttpServletRequest httpServletRequest, @RequestParam(value = "auth", defaultValue = "") String auth) {
		if(auth != null){
			UserDbo user = this.userService.findByAuthorizationId(auth);
			if(user != null){
				UserActivityDbo logoutActivity = new UserActivityDbo();
				logoutActivity.setUser(user);
				logoutActivity.setUserActivityDate(new Date());
				logoutActivity.setUserActivityType(UserAuthenticationTypeEnum.LOG_OUT);
				logoutActivity.setIpAddress(IPUtil.getIpAddr(httpServletRequest));
				logoutActivity.setUserLocation(IPUtil.getLocationStringOfIp(logoutActivity.getIpAddress()));
				logoutActivity.setUserAgent(httpServletRequest.getHeader("user-agent"));
				this.userActivityService.saveUserActivity(logoutActivity);
			}
		}
		
		model.addAttribute("loginFailed", false);
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return "login";
	}
	
	@RequestMapping(value = "/loginfail", method = RequestMethod.GET)
	public String authenticationfail(ModelMap model, HttpServletRequest httpServletRequest,  @RequestParam(value = "auth", defaultValue = "") String auth) {
		if(auth != null){
			UserDbo user = this.userService.findByAuthorizationId(auth);
			if(user != null){
				UserActivityDbo loginFailedActivity = new UserActivityDbo();
				loginFailedActivity.setUser(user);
				loginFailedActivity.setUserActivityDate(new Date());
				loginFailedActivity.setUserActivityType(UserAuthenticationTypeEnum.LOG_IN_FAILED);
				loginFailedActivity.setIpAddress(IPUtil.getIpAddr(httpServletRequest));
				loginFailedActivity.setUserLocation(IPUtil.getLocationStringOfIp(loginFailedActivity.getIpAddress()));
				loginFailedActivity.setUserAgent(httpServletRequest.getHeader("user-agent"));
				this.userActivityService.saveUserActivity(loginFailedActivity);
			}
		}
		
		// Adding an attribute to flag that an error happened at login
		model.addAttribute("loginFailed", true);
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return "login";
	}
}
