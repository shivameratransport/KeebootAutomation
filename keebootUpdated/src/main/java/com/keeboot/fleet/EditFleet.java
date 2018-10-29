package com.keeboot.fleet;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class EditFleet {
	 private KeebootDriver driver = null;
	 // ----------------------------------------------------
 // Class Constructor
 // ----------------------------------------------------
	 
	 
 public EditFleet (KeebootDriver driver) {
     // Build the page area.
     this.driver = driver;
 }
 
 
 public String formatFleetRequest(String name,String ownershipType,String vehicleNumber,
		 String vehicleType,String make,String model,String goodsType,String isActive,String isExtraLoadAllowed) throws JSONException
 {
	   JSONObject requestParams = new JSONObject();
	   
	   JSONObject specification = new JSONObject();
	   if(!isExtraLoadAllowed.equals(""))
	   {
		   specification.put("isExtraLoadAllowed", isExtraLoadAllowed);
	   }
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
	   if(!make.equals(""))
	   {
	   requestParams.put("make", make);
	   }
	   if(!model.equals(""))
	   {
	   requestParams.put("model", model);
	   }
	   if(!goodsType.equals(""))
	   {
	   requestParams.put("goodsType", goodsType);
	   }
	   if(!isActive.equals(""))
	   {
	   requestParams.put("isActive", isActive);
	   }
	   requestParams.put("specification", specification);
	   
	   
	   return requestParams.toString();
 }
}
