package com.keeboot.api.ConsigneeConsignor;

import org.json.JSONException;
import org.json.JSONObject;
import com.keeboot.web.KeebootDriver;

public class AddConsigneeConsignor {

private KeebootDriver driver = null;
// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public AddConsigneeConsignor(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}

public String formatConsigneeConsignorRequest(String name,String type,String spocName,
		String mobileNumber,String typeId) throws JSONException
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
	if(!typeId.equals(""))
	{
	   requestParams.put("typeId", typeId);
	}
	
	 return requestParams.toString();
}
}
