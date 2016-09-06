package com.example.springwebtemplate.dbo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.example.springwebtemplate.controller.response.NotificationDto;
import com.example.springwebtemplate.dbo.base.MappedDomainObjectBase;
import com.example.springwebtemplate.dbo.enums.AuthenticationTypeEnum;
import com.example.springwebtemplate.dbo.enums.NotificationStateEnum;
import com.example.springwebtemplate.dbo.enums.NotificationTypeEnum;
import com.example.springwebtemplate.dbo.enums.OperatingSystemTypeEnum;
import com.google.gson.Gson;

@Entity
@Indexed
@Table(name = "tbl_notification")
@SuppressWarnings("serial")
public class NotificationDbo extends MappedDomainObjectBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id", unique = true, nullable = false)
	protected long notificationId;

	@Column(name = "email_from")
	private String emailfrom;

	@Column(name = "email_to")
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String emailto;

	@Column(name = "email_subject")
	private String emailSubject;

	@Transient
	private Gson gson = new Gson();

	@Column(name = "notification_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date notificationDate;

	@Transient
	private String notificationDateString;

	@Column(name = "notification_location")
	private String notificationLocation;

	@Column(name = "notification_os")
	@Enumerated(EnumType.ORDINAL)
	private OperatingSystemTypeEnum notificationOs;

	@Column(name = "notification_action")
	@Enumerated(EnumType.ORDINAL)
	private AuthenticationTypeEnum notificationAction;

	@Column(name = "notification_ip")
	private String notificationIp;

	@Column(name = "notification_authorizationId", unique = true)
	private String notificationAuthorizationId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "notification_user_id", referencedColumnName = "notification_user_id", nullable = false)
	@IndexedEmbedded
	private NotificationUserDbo notificationUser;

	@Column(name = "notification_type")
	@Enumerated(EnumType.ORDINAL)
	private NotificationTypeEnum notificationType;

	@Column(name = "notification_state")
	@Enumerated(EnumType.ORDINAL)
	private NotificationStateEnum notificationState = NotificationStateEnum.IDLE;

	public NotificationDbo() {
	}

	public NotificationDbo(NotificationDto notificationDto) {
		if (notificationDto != null) {
			this.setEmailSubject(AuthenticationTypeEnum.getValue(notificationDto.getNotificationAction()).getKey());
			this.setNotificationDate(notificationDto.getDate());
			this.setNotificationLocation(notificationDto.getNotificationLocation());
			this.setNotificationOs(OperatingSystemTypeEnum.getValue(notificationDto.getNotificationOs()));
			this.setNotificationIp(notificationDto.getNotificationIp());
			this.setNotificationType(NotificationTypeEnum.getValue(notificationDto.getNotificationType()));
			this.setNotificationAction(AuthenticationTypeEnum.getValue(notificationDto.getNotificationAction()));
		}
	}

	public NotificationUserDbo getNotificationUser() {
		return notificationUser;
	}

	public void setNotificationUser(NotificationUserDbo notificationUser) {
		this.notificationUser = notificationUser;
	}

	public AuthenticationTypeEnum getNotificationAction() {
		return notificationAction;
	}

	public void setNotificationAction(AuthenticationTypeEnum notificationAction) {
		this.notificationAction = notificationAction;
	}

	public String getEmailfrom() {
		return emailfrom;
	}

	public void setEmailfrom(String emailfrom) {
		this.emailfrom = emailfrom;
	}

	public String getEmailto() {
		return emailto;
	}

	public void setEmailto(String emailto) {
		this.emailto = emailto;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public Date getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getNotificationLocation() {
		return notificationLocation;
	}

	public void setNotificationLocation(String notificationLocation) {
		this.notificationLocation = notificationLocation;
	}

	public OperatingSystemTypeEnum getNotificationOs() {
		return notificationOs;
	}

	public void setNotificationOs(OperatingSystemTypeEnum notificationOs) {
		this.notificationOs = notificationOs;
	}

	public String getNotificationIp() {
		return notificationIp;
	}

	public void setNotificationIp(String notificationIp) {
		this.notificationIp = notificationIp;
	}

	public String getNotificationAuthorizationId() {
		return notificationAuthorizationId;
	}

	public void setNotificationAuthorizationId(String notificationAuthorizationId) {
		this.notificationAuthorizationId = notificationAuthorizationId;
	}

	public NotificationTypeEnum getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationTypeEnum notificationType) {
		this.notificationType = notificationType;
	}

	public NotificationStateEnum getNotificationState() {
		return notificationState;
	}

	public void setNotificationState(NotificationStateEnum notificationState) {
		this.notificationState = notificationState;
	}

	public String getNotificationDateString() {
		Date notificationDate = this.getNotificationDate();
		if (notificationDate != null) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
			df.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
			try {
				return df.format(notificationDate);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public void setNotificationDateString(String notificationDateString) {
		Date notificationDate = this.getNotificationDate();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
		df.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
		this.notificationDateString = df.format(notificationDate);
	}

	@Override
	public int hashCode() {
		return Long.valueOf(notificationId).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotificationDbo) {
			return notificationId == ((NotificationDbo) obj).getNotificationId();
		}
		return super.equals(obj);
	}
}
