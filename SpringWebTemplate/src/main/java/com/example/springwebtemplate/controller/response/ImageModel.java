package com.example.springwebtemplate.controller.response;

public class ImageModel implements BaseRestResponse{
	private long userId;
	private String notificationUserimageBase64;
	
	public ImageModel() {
		// TODO Auto-generated constructor stub
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public String getNotificationUserimageBase64() {
		return notificationUserimageBase64;
	}

	public void setNotificationUserimageBase64(String notificationUserimageBase64) {
		this.notificationUserimageBase64 = notificationUserimageBase64;
	}
}
