package com.keeboot.fuelcard;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddFuelCard {
	
		 private KeebootDriver driver = null;
		 // ----------------------------------------------------
	   // Class Constructor
	   // ----------------------------------------------------
		 
		 
	   public AddFuelCard(KeebootDriver driver) {
	       // Build the page area.
	       this.driver = driver;
	   }
	   
	   public String formatFuelCardAddRequest(String cardNumber,String cardProvider,String cardLimit,String cardExpiryDate,
			   String vehicleNumber) throws JSONException
	   {
		   JSONObject requestParams = new JSONObject();
		   
		   if(!cardNumber.equals("")) 
		   {
		   requestParams.put("cardNumber", cardNumber);
		   }
		   
		   if(!cardProvider.equals("")) 
		   {
		   requestParams.put("cardProvider", cardProvider);
		   }
		   if(!cardLimit.equals("")) 
		   {
		   requestParams.put("cardLimit", cardLimit);
		   }
		   if(!cardExpiryDate.equals("")) 
		   {
		   requestParams.put("cardExpiryDate", cardExpiryDate);
		   }
		   if(!vehicleNumber.equals("")) 
		   {
		   requestParams.put("vehicleNumber", vehicleNumber);
		   }
		   
		   return requestParams.toString();
	   }
}
