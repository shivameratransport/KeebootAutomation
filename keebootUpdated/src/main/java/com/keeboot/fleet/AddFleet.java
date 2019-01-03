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
  
  
  public String formatFleetRequest(String name,String ownershipType,String vehicleNumber,String vehicleType,
		  Integer currentMeterReading,String ownerName,String ownerId,String ownerMobileNumber,
		  String vendorName,String vendorMobileNumber) throws JSONException
  {
	   JSONObject requestParams = new JSONObject();
	   JSONObject contractDetails = new JSONObject();
	   
	   
	   
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
	   
	   if(!ownerName.equals(""))
	   {
		   contractDetails.put("ownerName", ownerName);
	   }
	   
	   if(!ownerId.equals(""))
	   {
		   contractDetails.put("ownerId", ownerId);
	   }
	   if(!ownerMobileNumber.equals(""))
	   {
		   contractDetails.put("ownerMobileNumber", ownerMobileNumber);
	   }
	   if(!vendorName.equals(""))
	   {
		   contractDetails.put("vendorName", vendorName);
	   }
	   if(!vendorMobileNumber.equals(""))
	   {
		   contractDetails.put("vendorMobileNumber", vendorMobileNumber);
	   }
	   requestParams.put("currentMeterReading", currentMeterReading);
	   requestParams.put("contractDetails", contractDetails);
	   
	   return requestParams.toString();
  }
}
