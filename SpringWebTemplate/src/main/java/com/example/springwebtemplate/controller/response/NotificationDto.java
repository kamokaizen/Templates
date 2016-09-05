package com.example.springwebtemplate.controller.response;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.hibernate.validator.constraints.NotEmpty;

import com.example.springwebtemplate.dbo.NotificationDbo;

public class NotificationDto {
	private int notificationUser;
	private int notificationOs;
	private int notificationAction;
	private int notificationType;
	private int notificationState;
	@NotEmpty
	private String notificationDate;
	@NotEmpty
	private String notificationLocation;
	@NotEmpty
	private String notificationIp;

	// Transient Variables
	private long notificationId;
	private String notificationOsString;
	private String notificationActionString;
	private String notificationTypeString;
	private String notificationUserString;
	private String notificationStateString;
	private String notificationEmailString;

	public NotificationDto() {
		//Set default notification date to current
		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
		this.notificationDate = dateFormat.format(currentDate);
	}

	public NotificationDto(NotificationDbo notification) {
		if (notification != null) {
			this.notificationId = notification.getNotificationId();
			this.notificationUser = (int) notification.getNotificationUser()
					.getNotificationUserId();
			this.notificationOs = notification.getNotificationOs().getValue();
			this.notificationAction = notification.getNotificationAction()
					.getValue();
			this.notificationType = notification.getNotificationType().getValue();
			this.notificationState = notification.getNotificationState().getValue();
			this.notificationDate = notification.getNotificationDateString();
			this.notificationLocation = notification.getNotificationLocation();
			this.notificationIp = notification.getNotificationIp();
			this.notificationOsString = notification.getNotificationOs()
					.getKey();
			this.notificationActionString = notification
					.getNotificationAction().getKey();
			this.notificationTypeString = notification.getNotificationType().getKey();
			this.notificationStateString = notification.getNotificationState().getKey();
			this.notificationUserString = notification.getNotificationUser()
					.getName()
					+ " "
					+ notification.getNotificationUser().getSurname();
			this.notificationEmailString = notification.getNotificationUser().getEmail();
		}
	}

	public int getNotificationAction() {
		return notificationAction;
	}

	public void setNotificationAction(int notificationAction) {
		this.notificationAction = notificationAction;
	}

	public int getNotificationOs() {
		return notificationOs;
	}

	public void setNotificationOs(int notificationOs) {
		this.notificationOs = notificationOs;
	}

	public String getNotificationIp() {
		return notificationIp;
	}

	public void setNotificationIp(String notificationIp) {
		this.notificationIp = notificationIp;
	}

	public String getNotificationLocation() {
		return notificationLocation;
	}

	public void setNotificationLocation(String notificationLocation) {
		this.notificationLocation = notificationLocation;
	}

	public String getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

	public int getNotificationUser() {
		return notificationUser;
	}

	public void setNotificationUser(int notificationUser) {
		this.notificationUser = notificationUser;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationOsString() {
		return notificationOsString;
	}

	public void setNotificationOsString(String notificationOsString) {
		this.notificationOsString = notificationOsString;
	}

	public String getNotificationActionString() {
		return notificationActionString;
	}

	public void setNotificationActionString(String notificationActionString) {
		this.notificationActionString = notificationActionString;
	}

	public String getNotificationUserString() {
		return notificationUserString;
	}

	public void setNotificationUserString(String notificationUserString) {
		this.notificationUserString = notificationUserString;
	}
	
	public int getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}
	
	public int getNotificationState() {
		return notificationState;
	}

	public void setNotificationState(int notificationState) {
		this.notificationState = notificationState;
	}
	
	public String getNotificationStateString() {
		return notificationStateString;
	}

	public void setNotificationStateString(String notificationStateString) {
		this.notificationStateString = notificationStateString;
	}

	public String getNotificationTypeString() {
		return notificationTypeString;
	}

	public void setNotificationTypeString(String notificationTypeString) {
		this.notificationTypeString = notificationTypeString;
	}
	
	public String getNotificationEmailString() {
		return notificationEmailString;
	}

	public void setNotificationEmailString(String notificationEmailString) {
		this.notificationEmailString = notificationEmailString;
	}
	
	public Date getDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
		try {
			Date date = dateFormat.parse(this.getNotificationDate());
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
