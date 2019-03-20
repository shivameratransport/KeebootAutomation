package com.keeboot.location;

import org.json.JSONArray;
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
   
   
   public String formatLocationAddRequest(String type,Double radius,Double lat,Double lng,
		   String address,String locationCode,String state,String area,
		   String city,String zip,String locationType,String name,Boolean isActive ) throws JSONException
   {
	   JSONObject requestParams = new JSONObject(); 
	   JSONObject geoLocation = new JSONObject(); 
	   JSONObject obj = new JSONObject(); 
	   JSONObject geoFence = new JSONObject();
	   JSONArray jsonArray = new JSONArray();
	   obj.put("lat", lat);
	   obj.put("lng", lng);
	   jsonArray.put(obj);
	   geoFence.put("center", jsonArray);
	   type = "circle";
	   geoFence.put("type", type);
	   radius = 1000.0;
	   geoFence.put("radius", radius);
	  requestParams.put("geoFence", geoFence);
	 	geoLocation.put("lat", lat);
	   geoLocation.put("lng", lng);
	   requestParams.put("geoLocation", geoLocation);
	   
	  
	   if(!address.equals("")) 
	   {
	   requestParams.put("address", address);
	   } 
	   if(!locationCode.equals("")) 
	   {
	   requestParams.put("locationCode", locationCode);
	   } 
	   if(!state.equals("")) 
	   {
	   requestParams.put("state", state);
	   } 
	   if(!area.equals("")) 
	   {
	   requestParams.put("area", area);
	   } 
	   if(!city.equals("")) 
	   {
	   requestParams.put("city", city);
	   } 
	   if(!zip.equals("")) 
	   {
	   requestParams.put("zip", zip);
	   } 
	   
	   if(!name.equals("")) 
	   {
	   requestParams.put("name", name);
	   } 
	   
	   if(!locationType.equals("")) 
	   {
	   requestParams.put("locationType", locationType);
	   } 
	   requestParams.put("description", address);
	   requestParams.put("isActive", isActive);
	   return requestParams.toString();
   } 
}
