package com.example.springwebtemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.springwebtemplate.util.SpringPropertiesUtil;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(ModelMap model){
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return "responsiveviews/home";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(ModelMap model){
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return "responsiveviews/dashboard";
	}
		
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public String notifications(ModelMap model){
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return "responsiveviews/notifications";
	}
}
