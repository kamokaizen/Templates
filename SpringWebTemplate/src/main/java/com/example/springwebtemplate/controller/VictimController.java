package com.example.springwebtemplate.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springwebtemplate.controller.response.ImageDto;
import com.example.springwebtemplate.controller.response.NotificationDto;
import com.example.springwebtemplate.controller.response.NotificationUserDto;
import com.example.springwebtemplate.controller.response.PageDto;
import com.example.springwebtemplate.controller.response.StatusDto;
import com.example.springwebtemplate.controller.response.ValidationDto;
import com.example.springwebtemplate.controller.response.base.BaseRestResponse;
import com.example.springwebtemplate.controller.validator.NotificationUserValidator;
import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.NotificationUserDbo;
import com.example.springwebtemplate.dbo.enums.OperatingSystemTypeEnum;
import com.example.springwebtemplate.service.NotificationService;
import com.example.springwebtemplate.service.NotificationUserService;
import com.example.springwebtemplate.util.SpringPropertiesUtil;

@Controller
@RequestMapping("/victim")
public class VictimController {
	private static final Logger logger = LoggerFactory
			.getLogger(VictimController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CheatController cheatController;
	
	@Autowired
	NotificationUserService notificationUserService;
		
	@Autowired
	NotificationService notificationService;
			
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String victims(ModelMap model){
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		model.addAttribute("notificationUserDto", new NotificationUserDto());
		model.addAttribute("notificationDto",new NotificationDto());
		model.addAttribute("notificationOsValues",OperatingSystemTypeEnum.values());
		model.addAttribute("notificationOs", -1);
		return "responsiveviews/victims";
	}
		
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse createNotificationUser(NotificationUserDto notificationUserDto,
			   							Locale locale){
		ValidationDto response = new ValidationDto();
		NotificationUserValidator validator = new NotificationUserValidator();
		validator.setNotificationUserService(this.notificationUserService);
		BindingResult result = new BeanPropertyBindingResult(notificationUserDto, "notificationUserDto");
		validator.validate(notificationUserDto, result);
		
        if (result.hasErrors()){
        	response.setStatus(false);
        	for (FieldError error : result.getFieldErrors()) {
        		response.getErrors().put(error.getField(), messageSource.getMessage(error.getCode(), null, locale));
            }
        	return response;
        }
        
        this.notificationUserService.saveNotificationUser(new NotificationUserDbo(notificationUserDto));
        response.setStatus(true);
        return response;
	}
	
	@RequestMapping(value = "/cheat/new", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse newNotification(NotificationDto notificationDto, Locale locale) throws UnsupportedEncodingException {
		return cheatController.newNotification(notificationDto, locale);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse updateNotificationUser(NotificationUserDto notificationUserDto, Locale locale){
		ValidationDto response = new ValidationDto();
		try{
			if(notificationUserDto != null){
				NotificationUserDbo user = this.notificationUserService.findNotificationUserById(notificationUserDto.getNotificationUserId());
				
				if(user != null){
					BindingResult result = new BeanPropertyBindingResult(notificationUserDto, "notificationUserDto");
					NotificationUserValidator validator = new NotificationUserValidator();
					validator.setNotificationUserService(this.notificationUserService);
					validator.validateUpdate(user, notificationUserDto, result);
					
			        if (result.hasErrors()){
			        	response.setStatus(false);
			        	for (FieldError error : result.getFieldErrors()) {
			        		response.getErrors().put(error.getField(), messageSource.getMessage(error.getCode(), null, locale));
			             }
			        	return response;
			        }
			        
			        user.updateNotificationUser(notificationUserDto);        
			        this.notificationUserService.saveNotificationUser(user);
			        response.setStatus(true);
			        return response;
				}
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		response.setStatus(false);
		return response;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse deleteNotificationUser(@RequestParam(value = "uid", defaultValue = "0") String userId){
		StatusDto response = new StatusDto();
		try{
			List<NotificationDbo> notifications = this.notificationService.findNotificationsByUserId(Long.parseLong(userId));
			if(notifications != null && notifications.size() > 0){
				this.notificationService.deleteNotifications(notifications);	
			}
			this.notificationUserService.deleteNotificationUsersById(Long.parseLong(userId));
			response.setReason("Kullanıcı silindi");
			response.setStatus(true);
			return response;
		}
		catch(Exception e){
			e.printStackTrace();
			response.setReason(e.getLocalizedMessage());
		}
		response.setStatus(false);
		return response;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getNotificationUsers(@RequestParam(value = "pn", defaultValue = "1") String pageNumber) {
		PageDto<NotificationUserDto> response = new PageDto<NotificationUserDto>();
		try {
			int totalPageNumber = this.notificationUserService.getPageNumber(null);
			List<NotificationUserDbo> allUsers = this.notificationUserService.getPageResult(null, Order.desc("storeDate"), Integer.parseInt(pageNumber));
			response.setPage(Integer.parseInt(pageNumber));
			response.setTotalPage(totalPageNumber);
			if(allUsers != null){
				for (NotificationUserDbo user : allUsers) {
					NotificationUserDto notuser = new NotificationUserDto(user);
					response.getPageResult().add(notuser);
				}
			}
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}
	
	@RequestMapping(value = "/image/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getUserImage(@RequestParam(value = "uid", defaultValue = "0") String userId){
		ImageDto model = new ImageDto();
		try{
			NotificationUserDbo user = this.notificationUserService.findNotificationUserById(Integer.parseInt(userId));
			if(user != null){
				model.setUserId(user.getNotificationUserId());
				model.setImageBase64(user.getUserImageBase64());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/cheat/type/image/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getNotificationTypeImage(@RequestParam(value = "tid", defaultValue = "0") String typeId){
		return cheatController.getNotificationTypeImage(typeId);
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public int getVictimCount() {
		int count = 0;
		try {
			count = this.notificationUserService.getRowCount(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occured: " + ex.getLocalizedMessage());
		}
		return count;
	}
		
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse searchNotificationUsers(@RequestParam(value = "text", defaultValue = "") String searchText,
			@RequestParam(value = "pn", defaultValue = "1") String pageNumber) {
		PageDto<NotificationUserDto> response = new PageDto<NotificationUserDto>();
		try {
			List<NotificationUserDbo> results = this.notificationUserService.searchNotificationUsers(searchText, Integer.parseInt(pageNumber));
			if(results != null){
				logger.info("[ " + results.size() + " ] item is found for keyword: " + searchText + " Page: " + pageNumber);
				response.setPage(Integer.parseInt(pageNumber));
				response.setTotalPage(0);
				for (NotificationUserDbo user : results) {
					NotificationUserDto notuser = new NotificationUserDto(user);
					response.getPageResult().add(notuser);
				}
			}
			else{
				logger.info("[ 0 ] item is found for keyword: " + searchText + " Page: " + pageNumber);
			}
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}
}
