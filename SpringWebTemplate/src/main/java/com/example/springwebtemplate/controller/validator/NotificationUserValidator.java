package com.example.springwebtemplate.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.springwebtemplate.controller.response.NotificationUserDto;
import com.example.springwebtemplate.dbo.NotificationUserDbo;
import com.example.springwebtemplate.service.NotificationUserService;

public class NotificationUserValidator implements Validator {

	public NotificationUserService notificationUserService;
	
	private EmailValidator emailValidator;
	
	public NotificationUserValidator() {
		this.emailValidator = new EmailValidator();
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NotificationUserDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NotificationUserDto user = (NotificationUserDto) target;

		if(user.getNotificationUseremail() != null){
			if(!checkUseremailUniqueness(user.getNotificationUseremail()))
				errors.rejectValue("notificationUseremail", "EmailUniqueness");
			if(!emailValidator.validate(user.getNotificationUseremail())){
				errors.rejectValue("notificationUseremail", "EmailFormatInvalid");
			}
		}
		
		if (user.getNotificationUserusername() != null
				&& !checkUsernameUniqueness(user.getNotificationUserusername())) {
			errors.rejectValue("notificationUserusername", "UsernameUniqueness");
		}
		
		if(user.getNotificationUserimage() == null || user.getNotificationUserimage().getSize()==0){
				errors.rejectValue("notificationUserimage", "RequiredFileUpload");
		}
		
		if(user.getNotificationUsername() == null || user.getNotificationUsername().length() == 0){
			errors.rejectValue("notificationUsername", "NotEmpty");
		}
		
		if(user.getNotificationUsersurname() == null || user.getNotificationUsersurname().length() == 0){
			errors.rejectValue("notificationUsersurname", "NotEmpty");
		}
		
		if(user.getNotificationUserusername() == null || user.getNotificationUserusername().length() == 0){
			errors.rejectValue("notificationUserusername", "NotEmpty");
		}
	}
	
	public void validateUpdate(NotificationUserDbo notificationUser, Object target, Errors errors){
		NotificationUserDto user = (NotificationUserDto) target;

		if(user.getNotificationUseremail() != null){
			if(notificationUser.getEmail().compareTo(user.getNotificationUseremail()) != 0){
				if(!checkUseremailUniqueness(user.getNotificationUseremail()))
					errors.rejectValue("notificationUseremail", "EmailUniqueness");
				if(!emailValidator.validate(user.getNotificationUseremail())){
					errors.rejectValue("notificationUseremail", "EmailFormatInvalid");
				}
			}
		}
		
		if(notificationUser.getUsername().compareTo(user.getNotificationUserusername()) != 0){
			if (user.getNotificationUserusername() != null
					&& !checkUsernameUniqueness(user.getNotificationUserusername())) {
				errors.rejectValue("notificationUserusername", "UsernameUniqueness");
			}
		}
		
		if(user.isNotificationUserimageChanged()){
			if(user.getNotificationUserimage() == null || user.getNotificationUserimage().getSize()==0){
				errors.rejectValue("notificationUserimage", "RequiredFileUpload");
			}
		}
		
		if(user.getNotificationUsername() == null || user.getNotificationUsername().length() == 0){
			errors.rejectValue("notificationUsername", "NotEmpty");
		}
		
		if(user.getNotificationUsersurname() == null || user.getNotificationUsersurname().length() == 0){
			errors.rejectValue("notificationUsersurname", "NotEmpty");
		}
		
		if(user.getNotificationUserusername() == null || user.getNotificationUserusername().length() == 0){
			errors.rejectValue("notificationUserusername", "NotEmpty");
		}
	}
	
	private boolean checkUseremailUniqueness(String email) {
		return this.notificationUserService.findNotificationUserByEmailInAllItems(email) != null ? false : true;
	}

	private boolean checkUsernameUniqueness(String username) {
		return this.notificationUserService.findNotificationUserByUsernameInAllItems(username) != null ? false : true;
	}

	public NotificationUserService getNotificationUserService() {
		return notificationUserService;
	}

	public void setNotificationUserService(
			NotificationUserService notificationUserService) {
		this.notificationUserService = notificationUserService;
	}
}
