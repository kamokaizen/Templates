package com.example.springwebtemplate.dbo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import javax.persistence.Lob;
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
import com.example.springwebtemplate.util.ConstantKeys;
import com.example.springwebtemplate.util.SpringPropertiesUtil;
import com.example.springwebtemplate.util.StreamUtil;
import com.example.springwebtemplate.util.mail.MailAttachmentUnit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

	@Lob
	@Column(name = "email_content", length = 100000)
	private byte[] emailContent;

	@Column(name = "email_attachments", length = 100000)
	private String emailAttachments;

	@Transient
	private List<MailAttachmentUnit> mailAttachmentUnits;

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
			setDefaultFromEmail();
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

	public byte[] getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(byte[] emailContent) {
		this.emailContent = emailContent;
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

	public String getEmailAttachments() {
		return emailAttachments;
	}

	public void setEmailAttachments(String emailAttachments) {
		this.emailAttachments = emailAttachments;
	}

	public List<MailAttachmentUnit> getMailAttachmentUnits() {
		if (this.getEmailAttachments() != null && this.getEmailAttachments().length() > 0) {
			Type listOfMailAttachmentObject = new TypeToken<List<MailAttachmentUnit>>() {
			}.getType();
			this.mailAttachmentUnits = gson.fromJson(this.getEmailAttachments(), listOfMailAttachmentObject);
		}
		return mailAttachmentUnits;
	}

	public void setMailAttachmentUnits(List<MailAttachmentUnit> mailAttachmentUnits) {
		this.mailAttachmentUnits = mailAttachmentUnits;
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

	private int getCurrentYear(){
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;
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

	private void setDefaultFromEmail() {
		switch (this.notificationType) {
		case FACEBOOK:
			this.setEmailfrom(SpringPropertiesUtil.getProperty("defaultFromEmailAddress"));
			break;
		case GMAIL:
			this.setEmailfrom(SpringPropertiesUtil.getProperty("defaultGmailFromEmailAddress"));
			break;
		case HOTMAIL:
			this.setEmailfrom(SpringPropertiesUtil.getProperty("defaultHotmailFromEmailAddress"));
			break;
		case YAHOO:
			this.setEmailfrom(SpringPropertiesUtil.getProperty("defaultYahooFromEmailAddress"));
			break;
		default:
			this.setEmailfrom(SpringPropertiesUtil.getProperty("defaultFromEmailAddress"));
			break;
		}
	}

	public void prepareNotificationEmailContent() throws UnsupportedEncodingException {

		try {
			switch (this.getNotificationType()) {
			case FACEBOOK:
				this.setEmailContent(prepareEmailForEacebook());
				break;
			case GMAIL:
				this.setEmailContent(prepareAttachmentEmail(ConstantKeys.templateNotificationGmail,
						new MailAttachmentUnit("google.png", ConstantKeys.logoGoogle)));
				break;
			case HOTMAIL:
				this.setEmailContent(prepareAttachmentEmail(ConstantKeys.templateNotificationHotmail,
						new MailAttachmentUnit("outlook.png", ConstantKeys.logoHotmail)));
				break;
			case YAHOO:
				this.setEmailContent(prepareAttachmentEmail(ConstantKeys.templateNotificationYahoo,
						new MailAttachmentUnit("yahoo.png", ConstantKeys.logoYahoo)));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] prepareAttachmentEmail(String template, MailAttachmentUnit logoUnit) {
		try {
			InputStream stream = StreamUtil.getStream(template);
			DataInputStream in = new DataInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			List<MailAttachmentUnit> attachments = new ArrayList<MailAttachmentUnit>();
			MailAttachmentUnit userUnit = new MailAttachmentUnit("user.png");
			attachments.add(logoUnit);
			attachments.add(userUnit);
			
			MailAttachmentUnit googleLockUnit = null;
			MailAttachmentUnit googleSeperatorUnit = null;
			MailAttachmentUnit googleDeviceUnit = null;
			
			// These attachments only for gmail type
			if(this.getNotificationType().getValue() == NotificationTypeEnum.GMAIL.getValue()){
				googleLockUnit = new MailAttachmentUnit("googleLock.png", ConstantKeys.googleLock);
				googleSeperatorUnit = new MailAttachmentUnit("googleSeperator.png", ConstantKeys.googleSeperator);
				googleDeviceUnit = null;
				
				switch (notificationOs) {
					case IOS:
						googleDeviceUnit = new MailAttachmentUnit("googleDeviceApple.png", ConstantKeys.googleDeviceApple);
						break;
					case ANDROID:
						googleDeviceUnit = new MailAttachmentUnit("googleDeviceAndroid.png", ConstantKeys.googleDeviceAndroid);
						break;
					case WINDOWS:
						googleDeviceUnit = new MailAttachmentUnit("googleDeviceWindows.png", ConstantKeys.googleDeviceWindows);
						break;
					case MAC:
						googleDeviceUnit = new MailAttachmentUnit("googleDeviceMac.png", ConstantKeys.googleDeviceMac);
						break;
					case LINUX:
						googleDeviceUnit = new MailAttachmentUnit("googleDeviceWindows.png", ConstantKeys.googleDeviceWindows);
						break;
					default:
						googleDeviceUnit = new MailAttachmentUnit("googleDeviceWindows.png", ConstantKeys.googleDeviceWindows);
						break;
				}
				attachments.add(googleLockUnit);
				attachments.add(googleSeperatorUnit);
				attachments.add(googleDeviceUnit);
			}

			this.setMailAttachmentUnits(attachments);
			Type listOfMailAttachmentObject = new TypeToken<List<MailAttachmentUnit>>() {
			}.getType();
			this.setEmailAttachments(gson.toJson(attachments, listOfMailAttachmentObject));

			String strLine;
			StringBuffer strBuffer = new StringBuffer();
			NotificationUserDbo notificationUser = this.getNotificationUser();
			String serverName = SpringPropertiesUtil.getProperty("applicationServerName");
			String formattedDate = this.getNotificationDateString();

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				strLine = strLine.replace("${logo}", logoUnit.getCid());
				
				// These replacements only made for GMAIL type
				if(this.getNotificationType().getValue() == NotificationTypeEnum.GMAIL.getValue()){
					strLine = strLine.replace("${googleLock}", googleLockUnit.getCid());
					strLine = strLine.replace("${googleSeperator}", googleSeperatorUnit.getCid());
					strLine = strLine.replace("${googleDevice}", googleDeviceUnit.getCid());	
				}
				
				strLine = strLine.replace("${userLogo}", userUnit.getCid());
				strLine = strLine.replace("${user}", notificationUser.getName());
				strLine = strLine.replace("${fullname}", notificationUser.getName() + " " + notificationUser.getSurname());
				strLine = strLine.replace("${date}", formattedDate);
				strLine = strLine.replace("${currentYear}", String.valueOf(this.getCurrentYear()));
				strLine = strLine.replace("${location}", this.getNotificationLocation());
				strLine = strLine.replace("${IP}", this.getNotificationIp());
				strLine = strLine.replace("${operatingSystem}", this.getNotificationOs().getKey());
				strLine = strLine.replace("${email}", notificationUser.getEmail());
				strLine = strLine.replace("${auth}", this.getNotificationAuthorizationId());
				strLine = strLine.replace("${applicationServerName}", serverName);
				strBuffer.append(strLine);
			}
			in.close();
			return strBuffer.toString().getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] prepareEmailForEacebook() {
		try {
			InputStream stream = StreamUtil.getStream(ConstantKeys.templateNotificationFacebook);
			DataInputStream in = new DataInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String strLine;
			StringBuffer strBuffer = new StringBuffer();
			NotificationUserDbo notificationUser = this.getNotificationUser();
			String serverName = SpringPropertiesUtil.getProperty("applicationServerName");
			String formattedDate = this.getNotificationDateString();

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				strLine = strLine.replace("${user}", notificationUser.getName());
				strLine = strLine.replace("${date}", formattedDate);
				strLine = strLine.replace("${location}", this.getNotificationLocation());
				strLine = strLine.replace("${operatingSystem}", this.getNotificationOs().getKey());
				strLine = strLine.replace("${email}", notificationUser.getEmail());
				strLine = strLine.replace("${auth}", this.getNotificationAuthorizationId());
				strLine = strLine.replace("${applicationServerName}", serverName);
				strBuffer.append(strLine);
			}
			in.close();
			return strBuffer.toString().getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
