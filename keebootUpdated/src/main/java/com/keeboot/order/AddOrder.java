package com.keeboot.order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddOrder {
	
	
	 private KeebootDriver driver = null;
	 // ----------------------------------------------------
  // Class Constructor
  // ----------------------------------------------------
	 
	 
  public AddOrder(KeebootDriver driver) {
      // Build the page area.
      this.driver = driver;
  }
  
  
  public String formatOrderRequest(String contractType,String vehicleType,String vehicleTypeId,String goodsType,
		   String goodsTypeId,String customerName,String customerId,String aliasOrShortName,
		   String locationCode,String description,String locationType,String locationId) throws JSONException
  {
	   JSONObject requestParams = new JSONObject(); 
	   if(!contractType.equals("")) 
	   {
	   requestParams.put("contractType", contractType);
	   } 
	   if(!vehicleType.equals("")) 
	   {
	   requestParams.put("vehicleType", vehicleType);
	   }
	   if(!vehicleTypeId.equals("")) 
	   {
	   requestParams.put("vehicleTypeId", vehicleTypeId);
	   }
	   if(!goodsType.equals("")) 
	   {
	   requestParams.put("goodsType", goodsType);
	   }
	   if(!goodsTypeId.equals("")) 
	   {
	   requestParams.put("goodsTypeId", goodsTypeId);
	   }
	   JSONObject customer = new JSONObject(); 
	   if(!customerName.equals("")) 
	   {
		   customer.put("customerName", customerName);
	   }
	   if(!customerId.equals("")) 
	   {
		   customer.put("customerId", customerId);
	   }
	   if(!aliasOrShortName.equals("")) 
	   {
		   customer.put("aliasOrShortName", aliasOrShortName);
	   }
	   JSONArray locations = new JSONArray();
	   
	   JSONObject obj = new JSONObject(); 
	   JSONObject obj1 = new JSONObject(); 
	   if(!locationCode.equals("")) 
	   {
	   obj.put("locationCode", locationCode);
	   }
	   if(!locationCode.equals("")) 
	   {
	   obj1.put("locationCode", locationCode);
	   }
	   if(!description.equals("")) 
	   {
	   obj.put("description", description);
	   }
	   if(!description.equals("")) 
	   {
	   obj1.put("description", description);
	   }
	   if(!locationType.equals("")) 
	   {
	   obj.put("locationType", locationType);
	   }
	   if(!locationType.equals("")) 
	   {
	   obj1.put("locationType", locationType);
	   }
	   if(!locationId.equals("")) 
	   {
	   obj.put("locationId", locationId);
	   }
	   if(!locationId.equals("")) 
	   {
	   obj1.put("locationId", locationId);
	   }
	   locations.put(obj);
	   locations.put(obj1);
	  
	   
	   requestParams.put("customer", customer);
	   requestParams.put("locations", locations);
	   requestParams.put("locations", locations);
	   
	   return requestParams.toString();
  }
}
