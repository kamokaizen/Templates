package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.dbo.UserDbo;

public class UserSummaryModel implements BaseRestResponse {

	protected long userId;
	private String name;
	private String surname;
	private String username;

	public UserSummaryModel() {
		// TODO Auto-generated constructor stub
	}

	public UserSummaryModel(UserDbo user) {
		if (user != null) {
			this.userId = user.getUserId();
			this.name = user.getName();
			this.surname = user.getSurname();
			this.username = user.getUsername();
		}
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
