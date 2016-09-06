package com.example.springwebtemplate.controller.response;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.springwebtemplate.controller.response.base.BaseRestResponse;

public class ValidationDto implements BaseRestResponse{
	private boolean status;
	private Map<String, String> errors = new LinkedHashMap<String, String>();
	
	public ValidationDto() {
		// TODO Auto-generated constructor stub
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
