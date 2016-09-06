package com.example.springwebtemplate.dbo;

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
import javax.persistence.Transient;

import com.example.springwebtemplate.dbo.base.MappedDomainObjectBase;
import com.example.springwebtemplate.dbo.enums.UserAuthenticationTypeEnum;

@Entity
@Table(name = "tbl_user_activity")
public class UserActivityDbo extends MappedDomainObjectBase {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_activity_id", unique = true, nullable = false)
	protected long userActivityId;

	@Column(name = "user_activity_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date userActivityDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	private UserDbo user;

	@Transient
	private String userActivityDateString;

	@Column(name = "user_activity_type")
	@Enumerated(EnumType.ORDINAL)
	private UserAuthenticationTypeEnum userActivityType;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "user_agent")
	private String userAgent;

	@Column(name = "user_location")
	private String userLocation;

	public UserActivityDbo() {
		// TODO Auto-generated constructor stub
	}

	public long getUserActivityId() {
		return userActivityId;
	}

	public void setUserActivityId(long userActivityId) {
		this.userActivityId = userActivityId;
	}

	public Date getUserActivityDate() {
		return userActivityDate;
	}

	public void setUserActivityDate(Date userActivityDate) {
		this.userActivityDate = userActivityDate;
	}

	public UserDbo getUser() {
		return user;
	}

	public void setUser(UserDbo user) {
		this.user = user;
	}

	public UserAuthenticationTypeEnum getUserActivityType() {
		return userActivityType;
	}

	public void setUserActivityType(UserAuthenticationTypeEnum userActivityType) {
		this.userActivityType = userActivityType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public String getUserLocation() {
		return userLocation;
	}
}
