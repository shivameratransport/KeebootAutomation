package com.keeboot.indent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.keeboot.web.KeebootDriver;

public class AddIndent { 
private KeebootDriver driver = null;
// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public AddIndent(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}


public String formatIndentAddRequest(String customerName,String customerId,String indentType,String contractType,
		   String vehicleType,String vehicleTypeId,Integer noOfVehiclesRequired,
		   String goodsType,String goodsTypeId,String loadType,String pickupDateTime,String deliveryDateTime,
		   Double weight,Double volume,Boolean isActive,String locationId,String locationCode,String description) throws JSONException
{
	   JSONObject requestParams = new JSONObject(); 
	   
	   if(!customerName.equals("")) 
	   {
	   requestParams.put("customerName", customerName);
	   }
	   if(!customerId.equals("")) 
	   {
	   requestParams.put("customerId", customerId);
	   }
	   if(!indentType.equals("")) 
	   {
	   requestParams.put("indentType", indentType);
	   }
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
	   if(noOfVehiclesRequired!= 0) 
	   {
	   requestParams.put("noOfVehiclesRequired", noOfVehiclesRequired);
	   }
	   if(!goodsType.equals("")) 
	   {
	   requestParams.put("goodsType", goodsType);
	   }
	   if(!goodsTypeId.equals("")) 
	   {
	   requestParams.put("goodsTypeId", goodsTypeId);
	   }
	   if(!loadType.equals("")) 
	   {
	   requestParams.put("loadType", loadType);
	   }
	   if(!pickupDateTime.equals("")) 
	   {
	   requestParams.put("pickupDateTime", pickupDateTime);
	   }
	   if(!deliveryDateTime.equals("")) 
	   {
	   requestParams.put("deliveryDateTime", deliveryDateTime);
	   }
	   if(weight!= 0.0) 
	   {
	   requestParams.put("weight", weight);
	   }
	   if(volume!= 0.0) 
	   {
	   requestParams.put("volume", volume);
	   }
	   requestParams.put("isActive", isActive);
	  
	   JSONObject from = new JSONObject(); 
	   if(!locationId.equals("")) 
	   {
		   from.put("locationId", locationId);
	   }
	   if(!locationCode.equals("")) 
	   {
		   from.put("locationCode", locationCode);
	   }
	   if(!description.equals("")) 
	   {
		   from.put("description", description);
	   }
	   JSONObject to = new JSONObject(); 
	   if(!locationId.equals("")) 
	   {
		   to.put("locationId", locationId);
	   }
	   if(!locationCode.equals("")) 
	   {
		   to.put("locationCode", locationCode);
	   }
	   if(!description.equals("")) 
	   {
		   to.put("description", description);
	   }
	   requestParams.put("from", from);
	   requestParams.put("to", to);
	   JSONArray orderId = new JSONArray();
	   requestParams.put("orderId", orderId);
	   return requestParams.toString();
}
}
