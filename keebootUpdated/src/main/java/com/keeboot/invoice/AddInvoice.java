package com.keeboot.invoice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddInvoice {
	
	
	 private KeebootDriver driver = null;
	 // ----------------------------------------------------
	 // Class Constructor
	 // ----------------------------------------------------
	 
	 
 public AddInvoice(KeebootDriver driver) {
     // Build the page area.
     this.driver = driver;
 }
 
 
 public String formatInvoiceRequest(String customerId,String companyName,String tripNumber,
		   String valueForFreight,String valueForLoading,String valueForUnLoading,String valueForHalting,String fromAddress,String toAddress,
		   String fromCustomerName,String toCustomerName,String customerName,String displayTripId) throws JSONException
 {
	   JSONObject requestParams = new JSONObject(); 
	   JSONObject freightRate = new JSONObject(); 
	   JSONObject loadingCharges = new JSONObject(); 
	   JSONObject unloadingCharges = new JSONObject(); 
	   JSONObject haltingCharges = new JSONObject(); 
	   
	   if(!customerId.equals(""))
	   {
		   requestParams.put("customerId", customerId);
	   }
	   if(!companyName.equals(""))
	   {
		   requestParams.put("companyName", companyName);
	   }
	   if(!tripNumber.equals(""))
	   {
		   requestParams.put("tripNumber", tripNumber);
	   }
	   if(!tripNumber.equals(""))
	   {
		   requestParams.put("tripNumber", tripNumber);
	   }
	   if(!fromAddress.equals(""))
	   {
		   requestParams.put("fromAddress", fromAddress);
	   }
	   if(!toAddress.equals(""))
	   {
		   requestParams.put("toAddress", toAddress);
	   }
	   if(!fromCustomerName.equals(""))
	   {
		   requestParams.put("fromCustomerName", fromCustomerName);
	   }
	   if(!toCustomerName.equals(""))
	   {
		   requestParams.put("toCustomerName", toCustomerName);
	   }
	   if(!customerName.equals(""))
	   {
		   requestParams.put("customerName", customerName);
	   }
	   if(!displayTripId.equals(""))
	   {
		   requestParams.put("displayTripId", displayTripId);
	   }
	   
	   String key = "freightRate";   //hard coded as the rate type key remains same through-out
	   if(!key.equals(""))
	   {
		   freightRate.put("key", key);
	   }
	   if(!valueForFreight.equals(""))
	   {
		   freightRate.put("value", valueForFreight);
	   }
	   
	   String display = "Freight rate";  //some string values can be randomly generated and used, but hard coded for understanding 
	   if(!display.equals(""))
	   {
		   freightRate.put("display", display);
	   }
	   
	   key = "loadingCharges";
	   if(!key.equals(""))
	   {
		   loadingCharges.put("key", key);
	   }
	   if(!valueForLoading.equals(""))
	   {
		   loadingCharges.put("value", valueForLoading);
	   }
	   display = "Loading Charges";
	   if(!display.equals(""))
	   {
		   loadingCharges.put("display", display);
	   }
	   
	   key = "unloadingCharges";
	   if(!key.equals(""))
	   {
		   unloadingCharges.put("key", key);
	   }
	   if(!valueForUnLoading.equals(""))
	   {
		   unloadingCharges.put("value", valueForUnLoading);
	   }
	   display = "Unloading Charges";
	   if(!display.equals(""))
	   {
		   unloadingCharges.put("display", display);
	   }
	   
	   key = "haltingCharges";
	   if(!key.equals(""))
	   {
		   haltingCharges.put("key", key);
	   }
	   if(!valueForHalting.equals(""))
	   {
		   haltingCharges.put("value", valueForHalting);
	   }
	   display = "Halting Charges";
	   if(!display.equals(""))
	   {
		   haltingCharges.put("display", display);
	   }
	   JSONArray rates = new JSONArray();
	   rates.put(freightRate);
	   rates.put(loadingCharges);
	   rates.put(unloadingCharges);
	   rates.put(haltingCharges);
	   requestParams.put("rates",rates);
	   return requestParams.toString();

 }
}
