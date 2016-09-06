/*
 * 
 */
package com.example.springwebtemplate.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springwebtemplate.controller.response.CityDto;
import com.example.springwebtemplate.controller.response.PageDto;
import com.example.springwebtemplate.controller.response.StatusDto;
import com.example.springwebtemplate.controller.response.base.BaseRestResponse;
import com.example.springwebtemplate.dbo.CityDbo;
import com.example.springwebtemplate.service.CityService;

/**
 * The Class CityController. All sub request patternt should start with
 * /mobile/city
 */
@Controller
@RequestMapping("/city")
public class CityController {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	CityService cityService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<CityDto> getCities() {
		ArrayList<CityDto> response = new ArrayList<CityDto>();
		try {
			List<CityDbo> allCities = cityService.getAllCities();
			for (CityDbo city : allCities) {
				CityDto cityModel = new CityDto(city);
				response.add(cityModel);
			}
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public BaseRestResponse getCities(@RequestParam(value = "pn", defaultValue = "") int pageNumber) {
		PageDto<CityDto> response = new PageDto<CityDto>();
		try {
			int totalPageNumber = cityService.getPageNumber(null);
			List<CityDbo> allCities  = cityService.getPageResult(null, Order.asc("name"), pageNumber);
			response.setPage(pageNumber);
			response.setTotalPage(totalPageNumber);
			for (CityDbo city : allCities) {
				CityDto cityModel = new CityDto(city);
				response.getPageResult().add(cityModel);
			}
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getCityInfo(@RequestParam("cid") long cityId) {
		CityDto response = null;
		try {
			CityDbo city = cityService.findCity(cityId);
			if (city != null) {
				response = new CityDto(city);
			}
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return new StatusDto(false,
					"Şehir detayı hazırlanırken bir hata oluştu: "
							+ ex.getLocalizedMessage());
		}
	}
}
