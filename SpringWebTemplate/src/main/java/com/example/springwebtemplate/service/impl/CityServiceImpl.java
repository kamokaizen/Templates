package com.example.springwebtemplate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.dao.AbstractDao;
import com.example.springwebtemplate.dao.CityDao;
import com.example.springwebtemplate.dbo.CityDbo;
import com.example.springwebtemplate.service.CityService;

@Service("cityService")
@Transactional
public class CityServiceImpl extends AbstractServiceImpl<CityDbo, Long> implements CityService {
	
	@Autowired
	CityDao cityDao;
	
	@Override
	public void saveCity(CityDbo city) {
		cityDao.saveCity(city);
	}

	@Override
	public CityDbo findCity(long cityId) {
		return cityDao.findCity(cityId);
	}

	@Override
	public CityDbo findCityByPlateNumber(String plateNumber) {
		return cityDao.findCityByPlateNumber(plateNumber);
	}

	@Override
	public List<CityDbo> getAllCities() {
		return cityDao.getAllCities();
	}

	@Override
	public void saveBulkInsert(List<CityDbo> cities) {
		cityDao.bulkInsert(cities);
	}

	@Override
	public AbstractDao<CityDbo, Long> getDao() {
		return cityDao;
	}
}
