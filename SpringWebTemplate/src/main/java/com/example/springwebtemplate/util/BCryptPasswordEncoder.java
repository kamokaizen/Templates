package com.example.springwebtemplate.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {

	private static final Logger logger = LoggerFactory
			.getLogger(BCryptPasswordEncoder.class);
	
	public String encodePassword(String rawPass, Object salt)
			throws DataAccessException {
		Date passwordEncodeStart = new Date();
		logger.info("password encoding started");
		
		String hashpw = BCrypt.hashpw(rawPass, genSalt());
		
		Date passwordEncodeEnd = new Date();
		logger.info("password encoding is over, elapsed time " + (passwordEncodeStart.getTime() - passwordEncodeEnd.getTime()) + " ms");
		
		return hashpw;
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException {
		Date passwordValidationStart = new Date();
		logger.info("password validation started");
		
		boolean checkpw = false;
		
		try{
			checkpw = BCrypt.checkpw(rawPass, encPass);
			
			Date passwordValidationEnd = new Date();
			logger.info("password validation is over, elapsed time " + (passwordValidationEnd.getTime() - passwordValidationStart.getTime()) + " ms");	
		}
		catch(Exception e){
			logger.error("something went wrong during password validation error: " + e.getLocalizedMessage());
		}
				
		return checkpw;
	}
	
	public String genSalt() {
		Date generatingSaltBegin = new Date();
		logger.info("salt generator started");
		
		String gensalt = BCrypt.gensalt(4);
		
		Date generatingSaltEnd = new Date();
		logger.info("salt generator is over, elapsed time " + (generatingSaltEnd.getTime() - generatingSaltBegin.getTime()) + " ms");
		
		return gensalt;
	}

}
