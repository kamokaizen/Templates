package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.dbo.enums.CommonEnumInterface;

public class EnumTypeModel extends BaseRestModel {
	private int value;
	private String name;
	private String nameEn;

	public EnumTypeModel() {
		// TODO Auto-generated constructor stub
	}

	public EnumTypeModel(CommonEnumInterface typeEnum) {
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

	@Override
	public int getResponseType() {
		return ResponseTypeEnum.Enum.getValue();
	}

}
