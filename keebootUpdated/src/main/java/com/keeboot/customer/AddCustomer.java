package com.keeboot.customer;

import org.json.JSONException;
import org.json.JSONObject;
import com.keeboot.web.KeebootDriver;

public class AddCustomer {

private KeebootDriver driver = null;
// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public AddCustomer(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}

public String formatCustomerRequest(String customerType,String customerName,String mobileNumber,
		String emailId) throws JSONException
{
	JSONObject requestParams = new JSONObject();
	if(!customerType.equals(""))
	{
	   requestParams.put("customerType", customerType);
	}
	if(!customerName.equals(""))
	{
	   requestParams.put("customerName", customerName);
	}
	if(!mobileNumber.equals(""))
	{
	   requestParams.put("mobileNumber", mobileNumber);
	}
	if(!emailId.equals(""))
	{
	   requestParams.put("emailId", emailId);
	}
	
	 return requestParams.toString();
}
}
