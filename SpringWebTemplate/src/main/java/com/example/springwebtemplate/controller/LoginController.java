package com.example.springwebtemplate.controller;

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

import com.example.springwebtemplate.dbo.UserDbo;
import com.example.springwebtemplate.service.CityService;
import com.example.springwebtemplate.service.UserActivityService;
import com.example.springwebtemplate.service.UserService;
import com.example.springwebtemplate.util.SpringPropertiesUtil;

/**
 * Handles requests for the login page
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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
			redirectPage = "login";
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
	public String welcome(ModelMap model, @RequestParam(value = "error", defaultValue = "false") String error) {
		model.addAttribute("loginFailed", error);
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		logger.info("Login page is called with error: " + error);
		return "login";
	}
}
