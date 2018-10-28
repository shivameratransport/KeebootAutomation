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

	   if(!locationCode.equals("")) 
	   {
	   requestParams.put("locationCode", locationCode);
	   }
	   
	   if(!state.equals("")) 
	   {
	   requestParams.put("state", state);
	   }
	   
	   if(!city.equals("")) 
	   {
	   requestParams.put("city", city);
	   }
	   if(!zip.equals("")) 
	   {
	   requestParams.put("zip", zip);
	   }
	   
	   if(!country.equals("")) 
	   {
	   requestParams.put("country", country);
	   }
	   
	   if(!locationType.equals("")) 
	   {
	   requestParams.put("locationType", locationType);
	   }
	   
	   if(!isActive.equals("")) 
	   {
	   requestParams.put("isActive", isActive);
	   }
	   
	   return requestParams.toString();	   
}
}
