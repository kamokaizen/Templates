package com.example.springwebtemplate.service;

import java.util.List;

import com.example.springwebtemplate.dbo.CityDbo;

public interface CityService extends AbstractService<CityDbo>{
	void saveBulkInsert(List<CityDbo> cities);
    void saveCity(CityDbo city);
    CityDbo findCity(long cityId);
    CityDbo findCityByPlateNumber(String plateNumber);
    List<CityDbo> getAllCities();
}
