package com.example.springwebtemplate.dbo.enums;

public enum OperatingSystemTypeEnum implements CommonEnumInterface{
	IOS(0,"iPhone","iPhone"), ANDROID(1,"Android","Android"), WINDOWS(2,"Windows","Windows"), MAC(3,"Mac","Mac"), LINUX(4,"Linux", "Linux");
	
	private int value;
	private String key;
	private String key_en;
	
	private OperatingSystemTypeEnum(int value,String key,String key_en) {
		this.value = value;
		this.key = key;
		this.key_en = key_en;
	}
	
	public static OperatingSystemTypeEnum getValue(int intValue) {
		switch (intValue) {
			case 0 :
				return IOS;
			case 1:
				return ANDROID;
			case 2:
				return WINDOWS;
			case 3:
				return MAC;
			case 4:
				return LINUX;
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
