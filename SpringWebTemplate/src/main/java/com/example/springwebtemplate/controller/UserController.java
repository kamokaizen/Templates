package com.example.springwebtemplate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springwebtemplate.controller.response.BaseRestResponse;
import com.example.springwebtemplate.controller.response.StatusModel;
import com.example.springwebtemplate.controller.response.UserDto;
import com.example.springwebtemplate.controller.response.UserSummaryModel;
import com.example.springwebtemplate.controller.response.UserWebModel;
import com.example.springwebtemplate.controller.validator.UserValidator;
import com.example.springwebtemplate.dbo.CityDbo;
import com.example.springwebtemplate.dbo.UserDbo;
import com.example.springwebtemplate.service.CityService;
import com.example.springwebtemplate.service.UserService;
import com.example.springwebtemplate.util.BCryptPasswordEncoder;
import com.example.springwebtemplate.util.FormValidationUtil;

/**
 * The Class WebMethodController.Only authenticated web users can access this
 * controller methods!.
 * 
 * @author Kamil inal
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private MessageSource messageSource;

	/** The log. */
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	UserService userService;

	/** The city service. */
	@Autowired
	CityService cityService;

	/**
	 * Gets the users summary.
	 * 
	 * @return the users summary
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ArrayList<UserSummaryModel> getUserSummary(Locale locale) {
		ArrayList<UserSummaryModel> response = new ArrayList<UserSummaryModel>();
		try {
			List<UserDbo> allUsers = userService.getAllUsers();
			if (allUsers != null) {
				for (UserDbo user : allUsers) {
					response.add(new UserSummaryModel(user));
				}
			}
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(messageSource.getMessage("userGetError", null, locale));
			return response;
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getUserInfo(@RequestParam("uid") long userId,
			Locale locale) {
		try {
			boolean validNumber = ESAPI.validator().isValidNumber(
					"ESAPI Validation Secure Long", userId + "", 0,
					Long.MAX_VALUE, true);
			if (validNumber) {
				UserDbo user = userService.findById(userId);
				if (user != null)
					return new UserWebModel(user);
				else
					return new StatusModel(false, messageSource.getMessage(
							"userNotFound", null, locale));
			} else
				return new StatusModel(false, messageSource.getMessage(
						"invalidParameter", null, locale));
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return new StatusModel(false, messageSource.getMessage(
					"userGetError", null, locale)
					+ " "
					+ ex.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public BaseRestResponse updateUserAccount(
			@RequestBody final UserWebModel userModel, Locale locale) {

		try {
			boolean updated = userService.updateUser(userModel);
			if (updated)
				return new StatusModel(true, messageSource.getMessage(
						"userUpdated", null, locale));
			else
				return new StatusModel(false, messageSource.getMessage(
						"userNotFound", null, locale));
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			return new StatusModel(false, messageSource.getMessage(
					"userCannotUpdated", null, locale)
					+ " "
					+ ex.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String savePassword( 
			@Valid UserDto userDto,
			BindingResult result,
			ModelMap model,
			@RequestParam(value = "auth", defaultValue = "") String auth){
		try{
			UserValidator validator = new UserValidator();
			validator.setUserService(this.userService);
			validator.validate(userDto, result);
			
	        if (result.hasErrors()){
	        	model.addAttribute("auth", auth);
	        	model.addAttribute("cityId", userDto.getCity());
	        	model.addAttribute("cities", this.cityService.getAllCities());
				return "register";
	        }
	        else {
	        	UserDbo user = new UserDbo(userDto);
	        	CityDbo city = this.cityService.findCity(userDto.getCity());
	        	user.setCity(city);
	        	this.userService.saveUser(user);
	        	model.addAttribute("auth", auth);
	        	model.addAttribute("successfullyRegistered", true);
				return "redirect:/login";
	        }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "error";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public BaseRestResponse createUser(
			@RequestBody final UserWebModel userModel, Locale locale) {

		StringBuffer error = new StringBuffer();
		try {
			UserDbo newUser = new UserDbo();

			if (FormValidationUtil.isStringValid(userModel.getName())) {
				newUser.setName(userModel.getName());
			} else {
				error.append(messageSource.getMessage("noNameDefined", null,
						locale) + "\n");
			}
			if (FormValidationUtil.isStringValid(userModel.getPassword())) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String hashedPassword = encoder.encodePassword(
						userModel.getPassword(), encoder.genSalt());
				newUser.setPassword(hashedPassword);
			} else {
				error.append(messageSource.getMessage("noPasswordDefined",
						null, locale) + "\n");
			}
			if (FormValidationUtil.isStringValid(userModel.getSurname())) {
				newUser.setSurname(userModel.getSurname());
			} else {
				error.append(messageSource.getMessage("noSurnameDefined", null,
						locale) + "\n");
			}
			if (FormValidationUtil.isStringValid(userModel.getUsername())) {
				newUser.setUsername(userModel.getUsername());
			} else {
				error.append(messageSource.getMessage("noUsernameDefined",
						null, locale) + "\n");
			}

			if (Long.parseLong(userModel.getCityId()) != 0)
				newUser.setCity(cityService.findCity(Long.parseLong(userModel
						.getCityId())));

			if (error.length() > 0) {
				return new StatusModel(false, error.toString());
			} else {
				userService.saveUser(newUser);
				return new StatusModel(true, "Personel hesabı oluşturuldu.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error occured: " + ex.getLocalizedMessage());
			if (ex instanceof org.hibernate.exception.ConstraintViolationException) {
				return new StatusModel(
						false,
						"Personel hesabı oluşturulamadı: Girilen kullanıcı ismi kullanımda, lütfen başka bir kullanıcı ismi belirleyiniz!");
			}
			return new StatusModel(false, "Personel hesabı oluşturulamadı: "
					+ error.toString());
		}
	}
}
