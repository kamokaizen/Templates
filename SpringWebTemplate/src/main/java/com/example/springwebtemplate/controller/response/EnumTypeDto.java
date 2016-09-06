package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.controller.response.base.BaseRestResponse;
import com.example.springwebtemplate.dbo.enums.CommonEnumInterface;

public class EnumTypeDto implements BaseRestResponse {
	private int value;
	private String name;
	private String nameEn;

	public EnumTypeDto() {
		// TODO Auto-generated constructor stub
	}

	public EnumTypeDto(CommonEnumInterface typeEnum) {
		this.value = typeEnum.getValue();
		this.name = typeEnum.getKey();
		this.nameEn = typeEnum.getKeyEn();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
}
