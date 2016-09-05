package com.example.springwebtemplate.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.springwebtemplate.controller.response.UserDto;
import com.example.springwebtemplate.service.UserService;

public class UserValidator implements Validator {

	public UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDto user = (UserDto) target;

		if (user.getEmail() != null && user.getEmailRepeat() != null
				&& user.getEmail().compareTo(user.getEmailRepeat()) != 0) {
			errors.rejectValue("email", "EmailVerification");
			errors.rejectValue("emailRepeat", "EmailVerification");
		}

		if(user.getEmail() != null && !checkUseremailUniqueness(user.getEmail())){
			errors.rejectValue("email", "EmailUniqueness");
		}
		
		if (user.getUsername() != null
				&& !checkUsernameUniqueness(user.getUsername())) {
			errors.rejectValue("username", "UsernameUniqueness");
		}
	}

	private boolean checkUseremailUniqueness(String email) {
		return this.userService.findUserByEmail(email) != null ? false : true;
	}

	private boolean checkUsernameUniqueness(String username) {
		return this.userService.findUser(username) != null ? false : true;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
