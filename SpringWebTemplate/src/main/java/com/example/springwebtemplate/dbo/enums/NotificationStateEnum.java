package com.example.springwebtemplate.dbo.enums;

public enum NotificationStateEnum implements CommonEnumInterface {
	IDLE(0, "Boşta", "Boşta"),
	PASSIVE(1, "Pasif", "Pasif"), 
	ACTIVE(2, "Aktif", "Aktif"), 
	FAIL(3, "Başarısız", "Başarısız"),
	SUCCESS(4, "Başarılı", "Başarılı");

	private int value;
	private String key;
	private String key_en;

	private NotificationStateEnum(int value, String key, String key_en) {
		this.value = value;
		this.key = key;
		this.key_en = key_en;
	}

	public static NotificationStateEnum getValue(int intValue) {
		switch (intValue) {
		case 0:
			return IDLE;
		case 1:
			return PASSIVE;
		case 2:
			return ACTIVE;
		case 3:
			return FAIL;
		case 4:
			return SUCCESS;
		default:
			return IDLE;
		}
	}

	@Override
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
	
	@Override
	public String toString() {
		return getKey();
	}
}
