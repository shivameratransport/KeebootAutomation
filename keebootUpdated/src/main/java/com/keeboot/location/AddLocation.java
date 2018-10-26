package com.keeboot.location;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddLocation {
	
	
	 private KeebootDriver driver = null;
	 // ----------------------------------------------------
   // Class Constructor
   // ----------------------------------------------------
	 
	 
   public AddLocation(KeebootDriver driver) {
       // Build the page area.
       this.driver = driver;
   }
   public String formatLocationAddRequest(String type,String radius,String lat,String lng,
		   String address,String locationCode,String state,String city,String locationType,String isActive) throws JSONException
   {
	   JSONObject requestParams = new JSONObject();
	   
	   type = "circle";
	   
	   requestParams.put("type", type);
	   
	   if(radius!="") 
	   {
	   requestParams.put("radius", radius);
	   }
	   if(lat!="") 
	   {
	   requestParams.put("lat", lat);
	   }
	   if(lng!="") 
	   {
	   requestParams.put("lng", lng);
	   }
	   if(address!="") 
	   {
	   requestParams.put("address", address);
	   } 
	   if(locationCode!="") 
	   {
	   requestParams.put("locationCode", locationCode);
	   } 
	   if(state!="") 
	   {
	   requestParams.put("state", state);
	   } 
	   if(city!="") 
	   {
	   requestParams.put("city", city);
	   } 
	   if(locationType!="") 
	   {
	   requestParams.put("locationType", locationType);
	   } 
	   
	   if(isActive!="") 
	   {
	   requestParams.put("isActive", isActive);
	   } 
	   return requestParams.toString();
   } 
}
