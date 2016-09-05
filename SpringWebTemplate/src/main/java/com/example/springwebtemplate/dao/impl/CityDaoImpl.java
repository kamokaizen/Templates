package com.example.springwebtemplate.dao.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.springwebtemplate.dao.CityDao;
import com.example.springwebtemplate.dao.base.AbstractDaoImpl;
import com.example.springwebtemplate.dbo.CityDbo;

@Repository
public class CityDaoImpl extends AbstractDaoImpl<CityDbo, Long> implements CityDao {

	protected CityDaoImpl() {
		super(CityDbo.class);
	}

	@Override
	public void saveCity(CityDbo city) {
		saveOrUpdate(city);
	}

	@Override
	public CityDbo findCity(long cityId) {
		List<CityDbo> results = findByCriteria(Restrictions.like("cityId", cityId), null);
		if(results != null && results.size() > 0)
			return results.get(0);
		else
			return null;
	}

	@Override
	public CityDbo findCityByPlateNumber(String plateNumber) {
		List<CityDbo> results = findByCriteria(Restrictions.like("plate", plateNumber), null);
		if(results != null && results.size() > 0)
			return results.get(0);
		else
			return null;
	}

	@Override
	public List<CityDbo> getAllCities() {
		return getAll(Order.asc("name"));
	}

}
