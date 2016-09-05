package com.example.springwebtemplate.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This class creates the schema according to given parameters on the database.
 *
 * @author Kamil inal
 * 
 * Feb 22, 2012 5:25:48 PM
 */
public class SchemaCreator {

	protected static final Log log = LogFactory.getLog(SchemaCreator.class);
	
	/**
	 * Schema creation 
	 * 
	 * @param driver : a driver class for manage database connection.
	 * @param connectionURL: a connection path to connect database
	 * @param databaseName : database schema name
	 * @param user	: database user name
	 * @param password : database user password
	 * @return boolean : if schema exist, this method returns false, else returns true
	 */
	public static boolean createSchema(String driver, String connectionURL, String databaseName, String user, String password){
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(connectionURL, user, password);
			Statement st = con.createStatement();
			st.executeUpdate("CREATE DATABASE " + databaseName +" DEFAULT CHARACTER SET utf8 COLLATE utf8_turkish_ci");
			log.info("Database created successfully.");
			con.close();
			return true;
		} catch (Exception e) {
			log.error("Database could not created: "+ e.getMessage());
			return false;
		}
	}
}
