package com.example.springwebtemplate.controller.response;

import com.google.gson.Gson;


public abstract class BaseRestModel implements BaseRestResponse{
	protected int responseType;
	
	public void setResponseType(int responseType){
		this.responseType = responseType;
	}
	
	public enum ResponseTypeEnum {
		FirstAuthentication(99),
		Authentication(100),
		Donation(101),
		City(102),
		Enum(103),
		Place(104),
		Status(105),
		UserDonation(106),
		UserSummary(107),
		UserListSummary(108),
		PlaceList(109),
		DonationList(110),
		UserDonationList(111),
		PastDonation(112),
		PastDonationList(113),
		User(114);

		int value;
		
		private ResponseTypeEnum(int value) {
			this.value = value;
		}
		
		public static ResponseTypeEnum getValue(int intValue) {
			switch (intValue) {
			case 99:
				return FirstAuthentication;
			case 100:
				return Authentication;
			case 101:
				return Donation;
			case 102:
				return City;
			case 103:
				return Enum;
			case 104:
				return Place;
			case 105:
				return Status;
			case 106:
				return UserDonation;
			case 107:
				return UserSummary;
			case 108:
				return UserListSummary;
			case 109:
				return PlaceList;
			case 110:
				return DonationList;
			case 111:
				return UserDonationList;
			case 112:
				return PastDonation;
			case 113:
				return PastDonationList;
			case 114:
				return User;
			default:
				return null;
			}
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public String serialize() {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(this);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BaseRestModel deSerialize(String jsonString, ResponseTypeEnum type) {
		try {
			Gson gson = new Gson();
			BaseRestModel model = null;
			
			switch (type) {
			case City:
				model = gson.fromJson(jsonString, CityModel.class);
				break;
			case Enum:
				model = gson.fromJson(jsonString, EnumTypeModel.class);
				break;
			case Status:
				model = gson.fromJson(jsonString, StatusModel.class);
				break;
			default:
				return null;
			}
			
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public abstract int getResponseType();
}
