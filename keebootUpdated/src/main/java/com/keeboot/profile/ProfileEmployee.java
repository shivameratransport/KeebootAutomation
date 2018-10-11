package com.keeboot.profile;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.Common;
import com.keeboot.web.KeebootDriver;


public class ProfileEmployee {

	 private KeebootDriver driver = null;
	 // ----------------------------------------------------
   // Class Constructor
   // ----------------------------------------------------
	 
	 
   public ProfileEmployee(KeebootDriver driver) {
       // Build the page area.
       this.driver = driver;
   }
   
   
   public String formatProfileUpdateRequest(String mobileNumber,String firstName,String lastName,String address,
		   String pan,String emailId,String emergencyMobileNumber) throws JSONException
   {
	   JSONObject requestParams = new JSONObject();
	   requestParams.put("mobileNumber", mobileNumber);
	   requestParams.put("firstName", firstName);
	   requestParams.put("lastName", lastName);
	   requestParams.put("address", address);
	   requestParams.put("PAN", pan);
	   requestParams.put("emailId", emailId);
	   requestParams.put("emergencyMobileNumber", emergencyMobileNumber);
	   
	   return requestParams.toString();
   }
}
