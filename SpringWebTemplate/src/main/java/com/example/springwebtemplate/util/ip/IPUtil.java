package com.example.springwebtemplate.util.ip;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import com.example.springwebtemplate.util.ConstantKeys;
import com.example.springwebtemplate.util.StreamUtil;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;

public class IPUtil {

	public static String getIpAddr(HttpServletRequest httpServletRequest) {
		String ip = null;
		try {
			ip = httpServletRequest.getHeader("X-FORWARDED-FOR");
			if (ip == null) {
				ip = httpServletRequest.getRemoteAddr();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}

	public static ServerLocation getLocationOfIp(String ipAddress) {

		ServerLocation serverLocation = null;

		URL url = null;
		try {
			url = StreamUtil.getResource(ConstantKeys.geoliteCities);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.err.println("location database is not found!");
		}

		if (url == null) {
			System.err.println("location database is not found!");
		} else {

			try {
				LookupService lookup = new LookupService(url.getPath(),
						LookupService.GEOIP_MEMORY_CACHE);
				Location locationServices = lookup.getLocation(ipAddress);

				if (locationServices != null) {
					serverLocation = new ServerLocation();
					serverLocation.setCountryCode(locationServices.countryCode);
					serverLocation.setCountryName(locationServices.countryName);
					serverLocation.setRegion(locationServices.region);
					serverLocation.setRegionName(regionName.regionNameByCode(
							locationServices.countryCode,
							locationServices.region));
					serverLocation.setCity(locationServices.city);
					serverLocation.setPostalCode(locationServices.postalCode);
					serverLocation.setLatitude(String
							.valueOf(locationServices.latitude));
					serverLocation.setLongitude(String
							.valueOf(locationServices.longitude));
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}

		}

		return serverLocation;

	}

	public static String getLocationStringOfIp(String ipAddress) {
		ServerLocation locationOfIp = IPUtil.getLocationOfIp(ipAddress);

		if (locationOfIp != null) {
			return locationOfIp.toString();
		}

		return null;
	}
}
