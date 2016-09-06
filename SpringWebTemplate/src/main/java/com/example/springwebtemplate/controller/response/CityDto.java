package com.example.springwebtemplate.controller.response;

import com.example.springwebtemplate.controller.response.base.BaseRestResponse;
import com.example.springwebtemplate.dbo.CityDbo;

public class CityDto implements BaseRestResponse {

	protected long cityId;
	private String name;
	private String plate;
	
	public CityDto() {
		// TODO Auto-generated constructor stub
	}
	
	public CityDto(CityDbo city) {
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
}
