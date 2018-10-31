package com.keeboot.vendor;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddVendor {

private KeebootDriver driver = null;
// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public AddVendor(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}

public String formatVendorRequest(String name,String mobileNumber,String vendorType) throws JSONException
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
	if(!vendorType.equals(""))
	{
		requestParams.put("vendorType", vendorType);
	}
	return requestParams.toString();
}
}
