package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.dbo.UserActivityDbo;

public class UserActivityDto {

	protected long userActivityId;
	private String userActivityDate;
	private long userId;
	private String user;
	private String userEmail;
	private String userActivityType;
	private int userActivityTypeId;
	private String userIp;
	private String userAgent;
	private String userLocation;

	public UserActivityDto() {
		// TODO Auto-generated constructor stub
	}

	public UserActivityDto(UserActivityDbo activity) {
		if (activity != null) {
			this.userActivityId = activity.getUserActivityId();
			this.userActivityDate = activity.getDateString(activity
					.getUserActivityDate());
			this.userActivityType = activity.getUserActivityType().getKey();
			this.userId = activity.getUser().getUserId();
			this.userEmail = activity.getUser().getEmail();
			this.user = activity.getUser().getUsername();
			this.userActivityTypeId = activity.getUserActivityType().getValue();
			String ipAddress = activity.getIpAddress();
			this.userIp = ipAddress;
			if(ipAddress == null){
				this.userIp = "IP Adresi Yok";
			}
			String agent = activity.getUserAgent();
			this.userAgent = agent;
			if(agent == null){
				this.userAgent = "Agent Yok";
			}
			String location = activity.getUserLocation();
			this.userLocation = location;
			if(location == null){
				this.userLocation = "Konum Yok";
			}
		}
	}

	public long getUserActivityId() {
		return userActivityId;
	}

	public void setUserActivityId(long userActivityId) {
		this.userActivityId = userActivityId;
	}

	public String getUserActivityDate() {
		return userActivityDate;
	}

	public void setUserActivityDate(String userActivityDate) {
		this.userActivityDate = userActivityDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserActivityType() {
		return userActivityType;
	}

	public void setUserActivityType(String userActivityType) {
		this.userActivityType = userActivityType;
	}

	public int getUserActivityTypeId() {
		return userActivityTypeId;
	}

	public void setUserActivityTypeId(int userActivityTypeId) {
		this.userActivityTypeId = userActivityTypeId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	
	public String getUserLocation() {
		return userLocation;
	}
}
