package com.example.springwebtemplate.dbo.enums;

public enum LoginTypeEnum implements CommonEnumInterface{
	IOS(0,"Ios","Ios"), ANDROID(1,"Android","Android");
	
	private int value;
	private String key;
	private String key_en;
	
	private LoginTypeEnum(int value,String key,String key_en) {
		this.value = value;
		this.key = key;
		this.key_en = key_en;
	}
	
	public static LoginTypeEnum getValue(int intValue) {
		switch (intValue) {
			case 0 :
				return IOS;
			case 1:
				return ANDROID;
			default:
				return IOS;
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
