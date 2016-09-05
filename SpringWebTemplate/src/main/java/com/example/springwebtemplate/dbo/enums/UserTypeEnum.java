package com.example.springwebtemplate.dbo.enums;

public enum UserTypeEnum implements CommonEnumInterface{
	MALE(0,"Erkek","Male"), FEMALE(1,"Kadın","Female");
	
	private int value;
	private String key;
	private String key_en;
	
	private UserTypeEnum(int value,String key,String key_en) {
		this.value = value;
		this.key = key;
		this.key_en = key_en;
	}
	
	public static UserTypeEnum getValue(int intValue) {
		switch (intValue) {
			case 0 :
				return MALE;
			case 1:
				return FEMALE;
			default:
				return MALE;
		}
	}
		
	public String getKey() {
		return key;
	}
	
	public int getValue(){
		return value;
	}

	@Override
	public String getKeyEn() {
		return key_en;
	}
}
