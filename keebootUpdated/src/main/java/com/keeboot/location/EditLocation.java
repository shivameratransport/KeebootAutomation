package com.keeboot.location;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class EditLocation {



private KeebootDriver driver = null;
// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public EditLocation(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}
public String formatLocationEditRequest(String locationCode,String state,String city,String zip,
		   String country,String locationType,String isActive) throws JSONException
{
	   JSONObject requestParams = new JSONObject();

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
	   if(zip!="") 
	   {
	   requestParams.put("zip", zip);
	   }
	   
	   if(country!="") 
	   {
	   requestParams.put("country", country);
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
