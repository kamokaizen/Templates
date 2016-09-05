package com.example.springwebtemplate.util;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *	@author skutas
 *	This class generate random ID. 
 *	Using singleton implementation 
 */
public class RandomGUID
{
	private static RandomGUID instance = null;
	protected final Log log = LogFactory.getLog(getClass());
	private RandomGUID()
	{
		
	}
	
	/** 
	 *	if created instance of RandomGUID, will never create again..
	 */
	public static RandomGUID getInstance()
	{
		if(instance == null)
			instance = new RandomGUID();
		return instance;
	}
	
	/**
	 *	method generate key(16 character) for channel specification...
	 *	@exception 'Exception e' any exceptions...
	 */
	public String generateRandomKey()
	{
		try
		{
			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			return randomUUIDString;
		} catch (Exception e)
		{
			log.error("Exception RandomGUID - generateRandomKey() : "+e.toString());
			return "failed";
		}
	}
}
