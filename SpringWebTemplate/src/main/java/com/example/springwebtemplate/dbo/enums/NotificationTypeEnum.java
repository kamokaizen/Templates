package com.example.springwebtemplate.dbo.enums;

public enum NotificationTypeEnum implements CommonEnumInterface {
	FACEBOOK(0, "Facebook", "Facebook"), 
	GMAIL(1,"Gmail","Gmail"),
	HOTMAIL(2,"Hotmail","Hotmail"),
	YAHOO(3,"Yahoo","Yahoo");

	private int value;
	private String key;
	private String key_en;

	private NotificationTypeEnum(int value, String key, String key_en) {
		this.value = value;
		this.key = key;
		this.key_en = key_en;
	}

	public static NotificationTypeEnum getValue(int intValue) {
		switch (intValue) {
		case 0:
			return FACEBOOK;
		case 1:
			return GMAIL;
		case 2:
			return HOTMAIL;
		case 3:
			return YAHOO;
		default:
			return FACEBOOK;
		}
	}

	public String getKey() {
		return key;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getKeyEn() {
		return key_en;
	}

}
