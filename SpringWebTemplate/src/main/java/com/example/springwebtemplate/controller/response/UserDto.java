package com.example.springwebtemplate.controller.response;

import java.util.Calendar;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.example.springwebtemplate.controller.response.base.BaseRestResponse;
import com.example.springwebtemplate.dbo.UserDbo;

public class UserDto implements BaseRestResponse {

	private long userId;
	
	@NotEmpty
	private String name;

	@NotEmpty
	private String surname;

	@NotEmpty
	private String username;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Email
	private String emailRepeat;

	@NotEmpty
	private String password;

	@Range(min=1)
	private int city;

	@Range(min=1, max=31)
	private int birthDay;

	@Range(min=1, max=12)
	private int birthMonth;

	@Range(min=1900, max=2015)
	private int birthYear;

	private int sex;

	public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public UserDto(UserDbo user) {
		if (user != null) {
			this.userId = user.getUserId();
			this.name = user.getName();
			this.surname = user.getSurname();
			this.username = user.getUsername();
			this.email = user.getEmail();
			this.city = (int) user.getCity().getCityId();
			
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(user.getBirthDate());
		    this.birthYear = cal.get(Calendar.YEAR);
		    this.birthMonth = cal.get(Calendar.MONTH);
		    this.birthDay = cal.get(Calendar.DAY_OF_MONTH);
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailRepeat() {
		return emailRepeat;
	}

	public void setEmailRepeat(String emailRepeat) {
		this.emailRepeat = emailRepeat;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getUserId() {
		return userId;
	}
}
