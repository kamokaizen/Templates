package com.example.springwebtemplate.controller;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springwebtemplate.controller.response.BaseRestResponse;
import com.example.springwebtemplate.controller.response.ImageModel;
import com.example.springwebtemplate.controller.response.PageModel;
import com.example.springwebtemplate.controller.response.StatusModel;
import com.example.springwebtemplate.controller.response.UserActivityDto;
import com.example.springwebtemplate.dbo.UserActivityDbo;
import com.example.springwebtemplate.dbo.enums.UserAuthenticationTypeEnum;
import com.example.springwebtemplate.service.UserActivityService;
import com.example.springwebtemplate.util.ConstantKeys;
import com.example.springwebtemplate.util.SpringPropertiesUtil;
import com.example.springwebtemplate.util.StreamUtil;

@Controller
@RequestMapping("/loginactivity")
public class LoginActivityController {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginActivityController.class);
	
	@Autowired
	private UserActivityService userActivityService;
		
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String home(ModelMap model){
		model.addAttribute("contextPath", SpringPropertiesUtil.getProperty("contextPath"));
		return "responsiveviews/loginactivities";
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getUserActivities(@RequestParam(value = "uid", defaultValue = "0") String userId,
											  @RequestParam(value = "pn", defaultValue = "1") String pageNumber) {
		PageModel<UserActivityDto> response = new PageModel<UserActivityDto>();
		try {	
			Criterion criterion = null;
			if(userId.compareTo("0") != 0){
				criterion = Restrictions.eq("user.userId", Long.valueOf(userId));
			}
			
			int totalPageNumber = this.userActivityService.getPageNumber(criterion);
			List<UserActivityDbo> allActivities = this.userActivityService.getPageResult(criterion, Order.desc("storeDate"), Integer.parseInt(pageNumber));
			response.setPage(Integer.parseInt(pageNumber));
			response.setTotalPage(totalPageNumber);
			if(allActivities != null){
				for (UserActivityDbo activity : allActivities) {
					UserActivityDto userActivityDto = new UserActivityDto(activity);
					response.getPageResult().add(userActivityDto);
				}
			}
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occured: " + ex.getLocalizedMessage());
			return response;
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse deleteActivity(@RequestParam(value = "aid", defaultValue = "0") String activityId){
		StatusModel response = new StatusModel();
		try{
			UserActivityDbo activity = this.userActivityService.findUserActivityById(Long.parseLong(activityId));
			if(activity != null){				
				this.userActivityService.deleteUserActivityByActivity(activity);
			}
			response.setReason("Aktivite silindi");
			response.setStatus(true);
			return response;
		}
		catch(Exception e){
			e.printStackTrace();
			response.setReason(e.getLocalizedMessage());
		}
		response.setStatus(false);
		return response;
	}
	
	@RequestMapping(value = "/image/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BaseRestResponse getTypeImage(@RequestParam(value = "tid", defaultValue = "0") String typeId){
		ImageModel model = new ImageModel();
		try{
			model.setUserId(Integer.parseInt(typeId));
			switch(UserAuthenticationTypeEnum.getValue(Integer.parseInt(typeId))){
				case LOG_IN:
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.loginImage))));
					break;
				case LOG_IN_FAILED: 
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.loginFailImage))));
					break;
				case LOG_OUT:
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.logoutImage))));
					break;
				default:
					model.setNotificationUserimageBase64(Base64.encodeBase64String(IOUtils.toByteArray(StreamUtil.getStream(ConstantKeys.loginImage))));
					break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public int getActivityCount() {
		int count = 0;
		try {
			count = this.userActivityService.getRowCount(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occured: " + ex.getLocalizedMessage());
		}
		return count;
	}
}
