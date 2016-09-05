package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.dbo.CityDbo;

public class CityModel extends BaseRestModel {

	protected long cityId;
	private String name;
	private String plate;
	
	public CityModel() {
		// TODO Auto-generated constructor stub
	}
	
	public CityModel(CityDbo city) {
		if(city != null){
			this.cityId = city.getCityId();
			this.name = city.getName();
			this.plate = city.getPlate();
		}
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	@Override
	public int getResponseType() {
		return ResponseTypeEnum.City.getValue();
	}
}
