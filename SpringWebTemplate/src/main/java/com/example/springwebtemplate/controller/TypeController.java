package com.example.springwebtemplate.controller;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springwebtemplate.controller.response.EnumTypeModel;
import com.example.springwebtemplate.dbo.enums.AuthenticationTypeEnum;
import com.example.springwebtemplate.dbo.enums.LoginTypeEnum;
import com.example.springwebtemplate.dbo.enums.NotificationStateEnum;
import com.example.springwebtemplate.dbo.enums.UserRoleEnum;

/**
 * This class serves defined enumerator types to end users.
 * All sub request pattern should start with /mobile/type
 */
@Controller
@RequestMapping("/type")
public class TypeController {

	protected final Log log = LogFactory.getLog(getClass());
		
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<EnumTypeModel> getRoleTypes() {
		ArrayList<EnumTypeModel> response = new ArrayList<EnumTypeModel>();
		try{
			UserRoleEnum[] values = UserRoleEnum.values();
			for(UserRoleEnum type : values){
				response.add(new EnumTypeModel(type));
			}
			return response;
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}
	
	@RequestMapping(value = "/authentication", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<EnumTypeModel> getAuthenticationTypes() {
		ArrayList<EnumTypeModel> response = new ArrayList<EnumTypeModel>();
		try{
			AuthenticationTypeEnum[] values = AuthenticationTypeEnum.values();
			for(AuthenticationTypeEnum type : values){
				response.add(new EnumTypeModel(type));
			}
			return response;
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<EnumTypeModel> getLoginTypes() {
		ArrayList<EnumTypeModel> response = new ArrayList<EnumTypeModel>();
		try{
			LoginTypeEnum[] values = LoginTypeEnum.values();
			for(LoginTypeEnum type : values){
				response.add(new EnumTypeModel(type));
			}
			return response;
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}
	
	@RequestMapping(value = "/notification/state", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<EnumTypeModel> getNotificationStates() {
		ArrayList<EnumTypeModel> response = new ArrayList<EnumTypeModel>();
		try{
			NotificationStateEnum[] values = NotificationStateEnum.values();
			for(NotificationStateEnum type : values){
				response.add(new EnumTypeModel(type));
			}
			return response;
		}
		catch(Exception ex){
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}
}
