package com.keeboot.owner;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddOwner {

private KeebootDriver driver = null;
// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public AddOwner(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}

public String formatOwnerRequest(String name,String mobileNumber,String PAN,
		String address,String emailId) throws JSONException
{
	JSONObject requestParams = new JSONObject();
	if(!name.equals(""))
	{
	   requestParams.put("name", name);
	}
	if(!mobileNumber.equals(""))
	{
		 requestParams.put("mobileNumber", mobileNumber);
	}
	if(!PAN.equals(""))
	{
		 requestParams.put("PAN", PAN);
	}
	if(!address.equals(""))
	{
		requestParams.put("address", address);
	}
	if(!emailId.equals(""))
	{
		requestParams.put("emailId", emailId);
	}
	 return requestParams.toString();
}
}
