package com.keeboot.fleet;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddFleet  {
	 private KeebootDriver driver = null;
	 // ----------------------------------------------------
  // Class Constructor
  // ----------------------------------------------------
	 
	 
  public AddFleet(KeebootDriver driver) {
      // Build the page area.
      this.driver = driver;
  }
  
  
  public String formatFleetRequest(String name,String ownershipType,String vehicleNumber,String vehicleType) throws JSONException
  {
	   JSONObject requestParams = new JSONObject();
	   if(!name.equals(""))
	   {
	   requestParams.put("name", name);
	   }
	   if(!ownershipType.equals(""))
	   {
	   requestParams.put("ownershipType", ownershipType);
	   }
	   if(!vehicleNumber.equals(""))
	   {
	   requestParams.put("vehicleNumber", vehicleNumber);
	   }
	   if(!vehicleType.equals(""))
	   {
	   requestParams.put("vehicleType", vehicleType);
	   }
	   
	   
	   return requestParams.toString();
  }
}
