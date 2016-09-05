package com.example.springwebtemplate.controller.response;


public class StatusModel extends BaseRestModel {
	private boolean status;
	private String reason;

	public StatusModel() {

	}

	public StatusModel(boolean status, String reason) {
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

	@Override
	public int getResponseType() {
		return ResponseTypeEnum.Status.getValue();
	}
}
