package com.example.springwebtemplate.dbo.enums;


public enum AuthenticationTypeEnum implements CommonEnumInterface {
	LOG_IN(0,"Oturum Açma Girişimi","Oturum Açma Girişimi"),LOG_OUT(1,"Çıkış yapıldı","Çıkış yapıldı");

	private int value;
	private String key;
	private String key_en;

	private AuthenticationTypeEnum(int value, String key, String key_en) {
		this.value = value;
		this.key = key;
		this.key_en = key_en;
	}

	public static AuthenticationTypeEnum getValue(int intValue) {
		switch (intValue) {
		case 0:
			return LOG_IN;
		case 1:
			return LOG_OUT;
		default:
			return LOG_IN;
		}
	}

	public String key() {
		return key;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getKeyEn() {
		return key_en;
	}
}
