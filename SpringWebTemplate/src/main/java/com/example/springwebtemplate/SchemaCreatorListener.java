package com.example.springwebtemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.example.springwebtemplate.util.ConstantKeys;
import com.example.springwebtemplate.util.SchemaCreator;
import com.example.springwebtemplate.util.StreamUtil;

/**
 * 
 * @author Kamil inal
 * 
 */
public class SchemaCreatorListener implements ServletContextListener {

	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Schema creation
		try {			
			InputStream propertyStream = StreamUtil.getStream(ConstantKeys.database_config);
			Properties property = new Properties();
			property.load(propertyStream);
			SchemaCreator.createSchema(property.getProperty("jdbc.driver"),
					property.getProperty("jdbc.base_url"),
					property.getProperty("jdbc.database"),
					property.getProperty("jdbc.user"),
					property.getProperty("jdbc.password"));
		} catch (IOException e) {
			log.error("connection property read fail: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
