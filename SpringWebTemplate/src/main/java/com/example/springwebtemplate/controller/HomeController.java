package com.example.springwebtemplate.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springwebtemplate.controller.response.EnumTypeDto;
import com.example.springwebtemplate.controller.response.NotificationDto;
import com.example.springwebtemplate.controller.response.NotificationUserDto;
import com.example.springwebtemplate.controller.response.base.BaseRestResponse;
import com.example.springwebtemplate.dbo.enums.OperatingSystemTypeEnum;
import com.example.springwebtemplate.util.SpringPropertiesUtil;

@Controller
@RequestMapping("/home")
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private VictimController victimController;
	
	@Autowired
	private CheatController cheatController;
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String home(ModelMap model, Locale locale){
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		model.addAttribute("notificationOsValues",OperatingSystemTypeEnum.values());
		model.addAttribute("notificationOs", -1);
		model.addAttribute("notificationUserDto", new NotificationUserDto());
		model.addAttribute("notificationDto",new NotificationDto());
		return "responsiveviews/home";
	}
	
	@RequestMapping(value = "/victim/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getNotificationUsers(@RequestParam(value = "pn", defaultValue = "1") String pageNumber) {
		return victimController.getNotificationUsers(pageNumber);
	}
	
	@RequestMapping(value = "/victim/create", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse createNotificationUser(NotificationUserDto notificationUserDto, Locale locale){
		return victimController.createNotificationUser(notificationUserDto, locale);
	}
	
	@RequestMapping(value = "/victim/update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse updateNotificationUser(NotificationUserDto notificationUserDto, Locale locale){
		return victimController.updateNotificationUser(notificationUserDto, locale);
	}
	
	@RequestMapping(value = "/victim/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse deleteNotificationUser(@RequestParam(value = "uid", defaultValue = "0") String userId){
		return victimController.deleteNotificationUser(userId);
	}
	
	@RequestMapping(value = "/victim/search", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse searchNotificationUser(@RequestParam(value = "text", defaultValue = "") String searchText,
			@RequestParam(value = "pn", defaultValue = "1") String pageNumber){
		return victimController.searchNotificationUsers(searchText, pageNumber);
	}
	
	@RequestMapping(value = "/victim/image/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getUserImage(@RequestParam(value = "uid", defaultValue = "0") String userId){
		return victimController.getUserImage(userId);
	}
	
	@RequestMapping(value = "/cheat/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getNotifications(@RequestParam(value = "uid", defaultValue = "0") String userId,
										     @RequestParam(value = "pn", defaultValue = "1") String pageNumber) {
		return cheatController.getNotifications(userId, pageNumber);
	}
	
	@RequestMapping(value = "/cheat/new", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse newNotification(NotificationDto notificationDto, Locale locale) throws UnsupportedEncodingException {
		return cheatController.newNotification(notificationDto, locale);
	}
	
	@RequestMapping(value = "/cheat/update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse updateNotification(NotificationDto notificationDto, Locale locale) throws UnsupportedEncodingException {
		return cheatController.updateNotification(notificationDto, locale);
	}
	
	@RequestMapping(value = "/cheat/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse deleteNotification(@RequestParam(value = "nid", defaultValue = "0") String notificationId){
		return cheatController.deleteNotification(notificationId);
	}
	
	@RequestMapping(value = "/cheat/search", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse searchNotification(@RequestParam(value = "text", defaultValue = "") String searchText,
			@RequestParam(value = "pn", defaultValue = "1") String pageNumber){
		return cheatController.searchNotifications(searchText, pageNumber);
	}
	
	@RequestMapping(value = "/cheat/type/image/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getNotificationTypeImage(@RequestParam(value = "tid", defaultValue = "0") String typeId){
		return cheatController.getNotificationTypeImage(typeId);
	}
	
	@RequestMapping(value = "/cheat/state/image/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getStateImage(@RequestParam(value = "sid", defaultValue = "0") String stateId){
		return cheatController.getStateImage(stateId);
	}
	
	@RequestMapping(value = "/cheat/state/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<EnumTypeDto> getStates(){
		return cheatController.getStates();
	}
	
	@RequestMapping(value = "/cheat/state/change", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse changeNotificationState(@RequestParam(value = "nid", defaultValue = "0") String notificationId,
															@RequestParam(value = "sid", defaultValue = "0") String stateId){
		return cheatController.changeNotificationState(notificationId, stateId);
	}
}
