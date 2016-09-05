package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.dbo.UserDbo;

public class UserWebModel implements BaseRestResponse {

	private String staffId;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String cityId;

	public UserWebModel() {
		// TODO Auto-generated constructor stub
	}

	public UserWebModel(UserDbo user) {
		if (user != null) {
			this.staffId = String.valueOf(user.getUserId());
			this.name = user.getName();
			this.surname = user.getSurname();
			this.username = user.getUsername();
			this.cityId = String.valueOf(user.getCity().getCityId());
		}
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

}
