package com.example.springwebtemplate.dbo.enums;

public enum UserRoleEnum implements CommonEnumInterface{
	ROLE_USER(0,"ROLE_USER","ROLE_USER"), ROLE_ADMIN(1,"ROLE_ADMIN","ROLE_ADMIN"), ROLE_ANONYMOUS(1,"ROLE_ANONYMOUS","ROLE_ANONYMOUS");
	
	private int value;
	private String key;
	private String key_en;
	
	private UserRoleEnum(int value, String key, String key_en) {
		this.value = value;
		this.key = key;
		this.key_en = key_en;
	}
	
	public static UserRoleEnum getValue(int intValue) {
		switch (intValue) {
			case 0 :
				return ROLE_USER;
			case 1 :
				return ROLE_ADMIN;
			case 2:
				return ROLE_ANONYMOUS;
			default:
				return ROLE_ANONYMOUS;
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
