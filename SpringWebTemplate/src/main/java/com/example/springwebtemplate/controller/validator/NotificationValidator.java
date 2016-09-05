package com.example.springwebtemplate.controller.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.springwebtemplate.controller.response.NotificationDto;
import com.example.springwebtemplate.dbo.NotificationUserDbo;
import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;
import com.example.springwebtemplate.service.NotificationUserService;
import com.example.springwebtemplate.util.ConstantKeys;

public class NotificationValidator implements Validator {
	
	public NotificationUserService notificationUserService;
	
	private IPAddressValidator ipAddressValidator;
	private DateValidator	dateValidator;
	
	public NotificationValidator() {
		ipAddressValidator = new IPAddressValidator();
		dateValidator = new DateValidator();
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NotificationDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NotificationDto notification = (NotificationDto) target;
		
		if(notification.getNotificationUser() < 1){
			errors.rejectValue("notificationUser", "notificationUserRequired");
		}
		
		if(notification.getNotificationOs() < 0){
			errors.rejectValue("notificationOs", "notificationOsRequired");
		}
		
		if(notification.getNotificationAction() < 0){
			errors.rejectValue("notificationAction", "notificationActionRequired");
		}
		
		if(notification.getNotificationType() < 0){
			errors.rejectValue("notificationType", "notificationTypeRequired");
		}
		
		/* When user and type is selected then mail and type should be matched. For example, user mail is gmail and type should be gmail, vice versa.. */
		if(notification.getNotificationType() > 0 && notification.getNotificationUser() > 1){
			if(!checkTypeAndMailMatched(notification.getNotificationType(), notification.getNotificationUser())){
				errors.rejectValue("notificationType", "notificationTypeMismatched");
			}
		}
		
		if(notification.getNotificationDate() != null && notification.getNotificationDate().length() > 0){
			if(!dateValidator.validate(notification.getNotificationDate())){
				errors.rejectValue("notificationDate", "notificationDateNotValid");
			}
			else{
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
				dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
				try {
					Date date = dateFormat.parse(notification.getNotificationDate());
					if(date.getTime() > System.currentTimeMillis()){
						errors.rejectValue("notificationDate", "notificationDateMustBeLessFromCurrent");
					}
				} catch (ParseException e) {
					e.printStackTrace();
					errors.rejectValue("notificationDate", "notificationDateRequired");
				}	
			}
		}
		
		if(notification.getNotificationIp() != null && notification.getNotificationIp().length() > 0){
			if(!ipAddressValidator.validate(notification.getNotificationIp())){
				errors.rejectValue("notificationIp", "notificationIpFormatInvalid");
			}
		}
		
		if(notification.getNotificationLocation() == null || notification.getNotificationLocation().length() == 0){
			errors.rejectValue("notificationLocation", "NotEmpty");
		}
		
		if(notification.getNotificationIp() == null || notification.getNotificationIp().length() == 0){
			errors.rejectValue("notificationIp", "NotEmpty");
		}
	}
	
	private boolean checkTypeAndMailMatched(int type, int notificationUserId) {
		try{
			NotificationUserDbo user = this.notificationUserService.findNotificationUserById(notificationUserId);
			if(user != null){
				String email = user.getEmail();
				String[] splittedEmail = email.split("@");
				String suffixEmail = splittedEmail[1];
				String keyValue = NotificationTypeEnum.getValue(type).getKey();
				
				// Mail accounts may have different suffix such as hotmail, outlook, windowslive for microsoft. 
				// We must check the email with all microsoft mail types for validation..
				if(NotificationTypeEnum.getValue(type) == NotificationTypeEnum.HOTMAIL){
					for (String mailType : ConstantKeys.microsoftMailTypes) {
						if(suffixEmail.toLowerCase().contains(mailType.toLowerCase())){
							return true;
						}	
					}
				}
				else{
					if(suffixEmail.toLowerCase().contains(keyValue.toLowerCase())){
						return true;
					}	
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public NotificationUserService getNotificationUserService() {
		return notificationUserService;
	}

	public void setNotificationUserService(
			NotificationUserService notificationUserService) {
		this.notificationUserService = notificationUserService;
	}
}
