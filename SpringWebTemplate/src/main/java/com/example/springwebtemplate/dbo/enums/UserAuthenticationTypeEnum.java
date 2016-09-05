package com.example.springwebtemplate.dbo.enums;

public enum UserAuthenticationTypeEnum implements CommonEnumInterface {
	LOG_IN(0, "Oturum Açıldı", "Oturum Açıldı"), 
	LOG_OUT(1,"Çıkış yapıldı", "Çıkış yapıldı"),
	LOG_IN_FAILED(2,"Başarısız Giriş Denemesi", "Başarısız Giriş Denemesi");

	private int value;
	private String key;
	private String key_en;

	private UserAuthenticationTypeEnum(int value, String key, String key_en) {
		this.value = value;
		this.key = key;
		this.key_en = key_en;
	}

	public static UserAuthenticationTypeEnum getValue(int intValue) {
		switch (intValue) {
		case 0:
			return LOG_IN;
		case 1:
			return LOG_OUT;
		case 2:
			return LOG_IN_FAILED;
		default:
			return LOG_IN_FAILED;
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
