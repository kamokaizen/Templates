package com.example.springwebtemplate.controller.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationModel implements BaseRestResponse{
	private boolean status;
	private Map<String, String> errors = new LinkedHashMap<String, String>();
	
	public ValidationModel() {
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
