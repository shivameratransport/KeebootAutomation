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
	if(name!="")
	{
	   requestParams.put("name", name);
	}
	if(mobileNumber!="")
	{
		 requestParams.put("mobileNumber", mobileNumber);
	}
	if(PAN!="")
	{
		 requestParams.put("PAN", PAN);
	}
	if(address!="")
	{
		requestParams.put("address", address);
	}
	if(emailId!="")
	{
		requestParams.put("emailId", emailId);
	}
	 return requestParams.toString();
}
}
