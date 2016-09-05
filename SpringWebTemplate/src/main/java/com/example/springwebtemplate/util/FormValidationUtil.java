package com.example.springwebtemplate.util;

import java.util.Calendar;
import java.util.Date;

public class FormValidationUtil {
	
	
	public static boolean isStringValid(String validationValue){
		try{
			if(validationValue == null){
				return false;
			}
			else if(validationValue.length() < 1){
				return false;
			}
			else{
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isDateValid(Date validationValue){
		try{
			if(validationValue == null){
				return false;
			}
			Calendar nowDate = Calendar.getInstance();
			long onehouragoTimeInMillis = nowDate.getTimeInMillis()  - 3600000;
			if(validationValue.before(new Date(onehouragoTimeInMillis))) {
			   return false;
			} else {
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
