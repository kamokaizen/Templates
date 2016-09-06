package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.controller.response.base.BaseRestResponse;

public class StatusDto implements BaseRestResponse {
	private boolean status;
	private String reason;

	public StatusDto() {

	}

	public StatusDto(boolean status, String reason) {
		this.status = status;
		this.reason = reason;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
