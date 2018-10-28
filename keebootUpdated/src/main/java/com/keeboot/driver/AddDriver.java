package com.keeboot.driver;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddDriver {
	private KeebootDriver driver = null;

// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public AddDriver(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}


public String formatEmployeeAddRequest(String name,String ownershipType,String mobileNumber) throws JSONException
{
	   JSONObject requestParams = new JSONObject();
	   
	   
	   
	   requestParams.put("name", name);
	   
	   if(!ownershipType.equals(""))
	   {
	   requestParams.put("ownershipType", ownershipType);
	   }

	   requestParams.put("mobileNumber", mobileNumber);
	   
	   
	   
	   return requestParams.toString();
}
}
