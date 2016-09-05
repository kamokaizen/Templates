package com.example.springwebtemplate;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.example.springwebtemplate.dbo.CityDbo;
import com.example.springwebtemplate.dbo.UserDbo;
import com.example.springwebtemplate.dbo.enums.UserRoleEnum;
import com.example.springwebtemplate.dbo.enums.UserTypeEnum;
import com.example.springwebtemplate.service.CityService;
import com.example.springwebtemplate.service.UserService;
import com.example.springwebtemplate.util.BCryptPasswordEncoder;
import com.example.springwebtemplate.util.ConstantKeys;
import com.example.springwebtemplate.util.StreamUtil;

/**
 * 
 * @author Kamil inal
 * 
 */
public class InitializationListener implements ServletContextListener {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private CityService cityService;
	@Autowired
	private UserService userService;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Schema creation
		try {
			WebApplicationContextUtils
					.getRequiredWebApplicationContext(sce.getServletContext())
					.getAutowireCapableBeanFactory().autowireBean(this);

			checkCities();
			checkAdminUser();

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Initialization finished...");
	}

	private void indexItems() {
		try {
			log.info("Indexing entities");
			this.userService.indexItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Ended indexing of entities");
	}
	
	private void checkCities() {
		try {
			CityDbo city = this.cityService.findCityByPlateNumber("06");
			if (city == null) {
				this.insertCities();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkAdminUser() {
		try {
			UserDbo adminUser = this.userService.findUser("administrator");
			if (adminUser == null) {
				this.insertUser();
				indexItems();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertUser() {
		try {
			UserDbo userDbo = new UserDbo();
			userDbo.setName("admin");
			userDbo.setSurname("admin");
			userDbo.setUsername("administrator");
			userDbo.setEmail("help@springwebtemplate.com");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String hashedPassword = encoder.encodePassword("123456",encoder.genSalt());
			userDbo.setRole(UserRoleEnum.ROLE_ADMIN);
			userDbo.setSex(UserTypeEnum.MALE);
			userDbo.setPassword(hashedPassword);
			userDbo.setCity(this.cityService.findCityByPlateNumber("06"));
			userDbo.setAuthorizationId("76b1c1aa-8f9e-4e9e-a43e-7228a35e39a3");
			userDbo.setBirtDate(1980, 5, 20);
			this.userService.saveUser(userDbo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertCities() {
		try {
			List<CityDbo> allCities = cityService.getAllCities();
			if (allCities == null || allCities.size() < 1) {
				InputStream stream = StreamUtil
						.getStream(ConstantKeys.turkey_cities);
				DataInputStream in = new DataInputStream(stream);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, "UTF8"));

				String strLine;
				List<CityDbo> cityList = new ArrayList<CityDbo>();

				// Read File Line By Line
				while ((strLine = br.readLine()) != null) {
					String[] splittedColumns = strLine.split("\t", -1);
					CityDbo city = new CityDbo();
					city.setName(splittedColumns[1]);
					city.setPlate(splittedColumns[0]);
					cityList.add(city);
				}
				this.cityService.saveBulkInsert(cityList);
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
