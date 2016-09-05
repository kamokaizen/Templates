package com.example.springwebtemplate.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.springwebtemplate.controller.response.PasswordDto;

public class PasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PasswordDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//PasswordDto passwordDto = (PasswordDto) target;
		//validation handled in dto
	}

}
