package com.example.springwebtemplate.dbo.enums;

public enum LanguageEnum {
	ENGLISH("en"), TURKISH("tr");
	
	private String key;
	
	private LanguageEnum(String key) {
		this.key = key;
	}
	
	public static LanguageEnum getValue(int intValue) {
		switch (intValue) {
			case 0 :
				return ENGLISH;
			case 1:
				return TURKISH;
			default:
				return ENGLISH;
		}
	}
		
	public String getKey() {
		return key;
	}
}
