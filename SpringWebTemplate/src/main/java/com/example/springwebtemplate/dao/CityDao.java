package com.example.springwebtemplate.dao;

import java.util.List;

import com.example.springwebtemplate.dbo.CityDbo;

public interface CityDao extends AbstractDao<CityDbo, Long> {
    void saveCity(CityDbo city);
    CityDbo findCity(long cityId);
    CityDbo findCityByPlateNumber(String plateNumber);
    List<CityDbo> getAllCities();
}
