package com.example.springwebtemplate.dbo;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.springwebtemplate.controller.response.UserDto;
import com.example.springwebtemplate.dbo.base.MappedDomainObjectBase;
import com.example.springwebtemplate.dbo.enums.UserRoleEnum;
import com.example.springwebtemplate.dbo.enums.UserTypeEnum;
import com.example.springwebtemplate.util.BCryptPasswordEncoder;
import com.example.springwebtemplate.util.RandomGUID;

@Entity
@Table(name = "tbl_user")
@SuppressWarnings("serial")
public class UserDbo extends MappedDomainObjectBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	protected long userId;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "uname", unique = true)
	private String username;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birth_date")
	private Date birthDate = new Date();

	@Column(name = "authorization_id", unique = true, nullable = false)
	private String authorizationId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", referencedColumnName = "city_id", nullable = false)
	private CityDbo city;

	@Column(name = "role")
	@Enumerated(EnumType.ORDINAL)
	private UserRoleEnum role;

	@Column(name = "sex")
	@Enumerated(EnumType.ORDINAL)
	private UserTypeEnum sex;

	public UserDbo() {

	}

	public UserDbo(UserDto userDto) {
		if(userDto != null){
			setName(userDto.getName());
			setSurname(userDto.getSurname());
			setUsername(userDto.getUsername());
			setEmail(userDto.getEmail());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String hashedPassword = encoder.encodePassword(userDto.getPassword(), encoder.genSalt());
			setPassword(hashedPassword);
			setRole(UserRoleEnum.ROLE_USER);
			setSex(UserTypeEnum.getValue(userDto.getSex()));
			setBirtDate(userDto.getBirthYear(), userDto.getBirthMonth(), userDto.getBirthDay());
			setAuthorizationId(RandomGUID.getInstance().generateRandomKey());
		}
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAuthorizationId() {
		return authorizationId;
	}

	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}

	public UserRoleEnum getRole() {
		return role;
	}

	public void setRole(UserRoleEnum role) {
		this.role = role;
	}

	public UserTypeEnum getSex() {
		return sex;
	}

	public void setSex(UserTypeEnum sex) {
		this.sex = sex;
	}

	public long getUserId() {
		return userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CityDbo getCity() {
		return city;
	}

	public void setCity(CityDbo city) {
		this.city = city;
	}

	public void setBirtDate(int year, int month, int day){
		Calendar rightNow = Calendar.getInstance();
		rightNow.set(year, month, day);
		setBirthDate(rightNow.getTime());
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(userId).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDbo) {
			return userId == ((UserDbo) obj).getUserId();
		}
		return super.equals(obj);
	}
}
