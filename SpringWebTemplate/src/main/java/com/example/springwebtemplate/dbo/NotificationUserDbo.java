package com.example.springwebtemplate.dbo;

import java.io.IOException;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.example.springwebtemplate.controller.response.NotificationUserDto;
import com.example.springwebtemplate.dbo.base.MappedDomainObjectBase;
import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;
import com.example.springwebtemplate.util.ImageUtil;

@Entity
@Indexed
@Table(name = "tbl_notification_user")
public class NotificationUserDbo extends MappedDomainObjectBase {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_user_id", unique = true, nullable = false)
	protected long notificationUserId;

	@Column(name = "name")
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String name;

	@Column(name = "surname")
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String surname;

	@Column(name = "username", unique = true, nullable = false)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String username;

	@Lob
	@Column(name = "user_image", length = 100000)
	private byte[] userImage;

	@Transient
	private String userImageBase64;

	@Column(name = "email", unique = true, nullable = false)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String email;

	@ContainedIn
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notificationUser")
	private Set<NotificationDbo> notifications;

	public NotificationUserDbo() {
		if (this.userImage != null) {
			this.setUserImageBase64(Base64.encodeBase64String(this.userImage));
		}
	}

	public NotificationUserDbo(NotificationUserDto notificationUserDto) {
		if (notificationUserDto != null) {
			this.setName(notificationUserDto.getNotificationUsername());
			this.setSurname(notificationUserDto.getNotificationUsersurname());
			this.setUsername(notificationUserDto.getNotificationUserusername());
			this.setEmail(notificationUserDto.getNotificationUseremail());
			try {
				this.setUserImage(ImageUtil.resizeImage(notificationUserDto
						.getNotificationUserimage().getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateNotificationUser(NotificationUserDto notificationUserDto){
		if (notificationUserDto != null) {
			this.setName(notificationUserDto.getNotificationUsername());
			this.setSurname(notificationUserDto.getNotificationUsersurname());
			this.setUsername(notificationUserDto.getNotificationUserusername());
			this.setEmail(notificationUserDto.getNotificationUseremail());
			if(notificationUserDto.isNotificationUserimageChanged()){
				try {
					this.setUserImage(ImageUtil.resizeImage(notificationUserDto
							.getNotificationUserimage().getInputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNotificationUserId() {
		return notificationUserId;
	}

	public void setNotificationUserId(long notificationUserId) {
		this.notificationUserId = notificationUserId;
	}

	public Set<NotificationDbo> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<NotificationDbo> notifications) {
		this.notifications = notifications;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getUserImage() {
		return userImage;
	}

	public void setUserImage(byte[] userImage) {
		this.userImage = userImage;
	}

	public String getUserImageBase64() {
		if (this.userImage != null && this.userImageBase64 == null) {
			this.setUserImageBase64(Base64.encodeBase64String(this.userImage));
		}
		return userImageBase64;
	}

	public void setUserImageBase64(String userImageBase64) {
		this.userImageBase64 = userImageBase64;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(notificationUserId).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotificationUserDbo) {
			return notificationUserId == ((NotificationUserDbo) obj)
					.getNotificationUserId();
		}
		return super.equals(obj);
	}
		
	public NotificationTypeEnum getNotificationTypeFromEmail() {
		if(this.email != null && this.email.length() > 0){
			if(this.email.toLowerCase().contains("gmail.com")){
				return NotificationTypeEnum.GMAIL;
			}
			else if(this.email.toLowerCase().contains("outlook.com") || this.email.toLowerCase().contains("hotmail.com")){
				return NotificationTypeEnum.HOTMAIL;
			}
			else if(this.email.toLowerCase().contains("yahoo.com")){
				return NotificationTypeEnum.YAHOO;
			}
			else{
				return NotificationTypeEnum.FACEBOOK;
			}
		}
		return NotificationTypeEnum.FACEBOOK;
	}
}
