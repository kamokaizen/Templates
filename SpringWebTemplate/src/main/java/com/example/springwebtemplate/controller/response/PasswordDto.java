package com.example.springwebtemplate.controller.response;

import org.hibernate.validator.constraints.NotEmpty;

public class PasswordDto {
	private String auth;

	@NotEmpty
	private String password;

	public PasswordDto() {
		// TODO Auto-generated constructor stub
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
