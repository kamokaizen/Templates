package com.example.springwebtemplate.controller.response;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.example.springwebtemplate.dbo.NotificationUserDbo;
import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;

public class NotificationUserDto {
	@NotEmpty
	@Size(min=1)
	private String notificationUsername;
	@NotEmpty
	@Size(min=1)
	private String notificationUsersurname;
	@NotEmpty
	@Size(min=1)
	private String notificationUserusername;
	@NotEmpty
	private String notificationUseremail;
	private MultipartFile notificationUserimage;
	private boolean notificationUserimageChanged = true;
	private long notificationUserId;
	private int notificationType;

	public NotificationUserDto() {
		// TODO Auto-generated constructor stub
	}
	
	public NotificationUserDto(NotificationUserDbo user) {
		if(user != null){
			this.notificationUserId = user.getNotificationUserId();
			this.notificationUsername = user.getName();
			this.notificationUsersurname = user.getSurname();
			this.notificationUserusername = user.getUsername();
			this.notificationUseremail = user.getEmail();
			this.notificationType = grabNotificationTypeFromEmail(this.notificationUseremail);
		}
	}

	public String getNotificationUsername() {
		return notificationUsername;
	}

	public void setNotificationUsername(String notificationUsername) {
		this.notificationUsername = notificationUsername;
	}

	public String getNotificationUsersurname() {
		return notificationUsersurname;
	}

	public void setNotificationUsersurname(String notificationUsersurname) {
		this.notificationUsersurname = notificationUsersurname;
	}

	public String getNotificationUserusername() {
		return notificationUserusername;
	}

	public void setNotificationUserusername(String notificationUserusername) {
		this.notificationUserusername = notificationUserusername;
	}

	public String getNotificationUseremail() {
		return notificationUseremail;
	}

	public void setNotificationUseremail(String notificationUseremail) {
		this.notificationUseremail = notificationUseremail;
	}

	public MultipartFile getNotificationUserimage() {
		return notificationUserimage;
	}

	public void setNotificationUserimage(MultipartFile notificationUserimage) {
		this.notificationUserimage = notificationUserimage;
	}

	public long getNotificationUserId() {
		return notificationUserId;
	}

	public void setNotificationUserId(long notificationUserId) {
		this.notificationUserId = notificationUserId;
	}

	public boolean isNotificationUserimageChanged() {
		return notificationUserimageChanged;
	}

	public void setNotificationUserimageChanged(boolean notificationUserimageChanged) {
		this.notificationUserimageChanged = notificationUserimageChanged;
	}
	
	public int getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}
	
	private int grabNotificationTypeFromEmail(String notificationUseremail) {
		if(notificationUseremail != null && notificationUseremail.length() > 0){
			if(notificationUseremail.toLowerCase().contains("gmail.com")){
				return NotificationTypeEnum.GMAIL.getValue();
			}
			else if(notificationUseremail.toLowerCase().contains("outlook.com") || notificationUseremail.toLowerCase().contains("hotmail.com")){
				return NotificationTypeEnum.HOTMAIL.getValue();
			}
			else if(notificationUseremail.toLowerCase().contains("yahoo.com")){
				return NotificationTypeEnum.YAHOO.getValue();
			}
			else{
				return NotificationTypeEnum.FACEBOOK.getValue();
			}
		}
		return NotificationTypeEnum.FACEBOOK.getValue();
	}
}
