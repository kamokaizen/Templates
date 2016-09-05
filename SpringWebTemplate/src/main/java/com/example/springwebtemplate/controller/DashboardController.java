package com.example.springwebtemplate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springwebtemplate.util.SpringPropertiesUtil;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	private static final Logger logger = LoggerFactory
			.getLogger(DashboardController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private VictimController victimController;
	
	@Autowired
	private CheatController cheatController;
		
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String home(ModelMap model){
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return "responsiveviews/dashboard";
	}
	
	@RequestMapping(value = "/victim/count", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public int getVictimCount() {
		return victimController.getVictimCount();
	}
	
	@RequestMapping(value = "/cheat/count", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public int getNotificationCount() {
		return cheatController.getNotificationCount();
	}
}
