package com.keeboot.api.ConsigneeConsignor;

import org.json.JSONException;
import org.json.JSONObject;
import com.keeboot.web.KeebootDriver;

public class EditConsigneeConsignor {

private KeebootDriver driver = null;
// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public EditConsigneeConsignor(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}

public String formatEditConsigneeConsignorRequest(String name,String type,String spocName,
		String mobileNumber,String landlineNumber,
		String emailId,String locationCode,
		String city,String customerName,String address,String state, String pincode,
		String gstin,Boolean status,String customerId,String locationId,
		Boolean emailAlert,Boolean smsAlert,String typeId) throws JSONException
{
	JSONObject requestParams = new JSONObject();
	if(!name.equals(""))
	{
	   requestParams.put("name", name);
	}
	if(!type.equals(""))
	{
	   requestParams.put("type", type);
	}
	if(!spocName.equals(""))
	{
	   requestParams.put("spocName", spocName);
	}
	if(!mobileNumber.equals(""))
	{
	   requestParams.put("mobileNumber", mobileNumber);
	}
	if(!landlineNumber.equals(""))
	{
	   requestParams.put("landlineNumber", landlineNumber);
	}
	if(!emailId.equals(""))
	{
	   requestParams.put("emailId", emailId);
	}
	if(!locationCode.equals(""))
	{
	   requestParams.put("locationCode", locationCode);
	}
	if(!city.equals(""))
	{
	   requestParams.put("city", city);
	}
	if(!customerName.equals(""))
	{
	   requestParams.put("customerName", customerName);
	}
	if(!address.equals(""))
	{
	   requestParams.put("address", address);
	}
	if(!state.equals(""))
	{
	   requestParams.put("state", state);
	}
	if(!pincode.equals(""))
	{
	   requestParams.put("pincode", pincode);
	}
	if(!gstin.equals(""))
	{
	   requestParams.put("gstin", gstin);
	}
	if(!customerId.equals(""))
	{
	   requestParams.put("customerId", customerId);
	}
	if(!locationId.equals(""))
	{
	   requestParams.put("locationId", locationId);
	}
	if(!typeId.equals(""))
	{
	   requestParams.put("typeId", typeId);
	}
		requestParams.put("emailAlert", emailAlert);
		requestParams.put("smsAlert", smsAlert);
		requestParams.put("status", status);
	 return requestParams.toString();
}
}
