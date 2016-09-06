package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.controller.response.base.BaseRestResponse;

public class ImageDto implements BaseRestResponse{
	private long userId;
	private String imageBase64;
	
	public ImageDto() {
		// TODO Auto-generated constructor stub
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
}
