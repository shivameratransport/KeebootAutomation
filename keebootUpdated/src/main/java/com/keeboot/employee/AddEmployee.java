package com.keeboot.employee;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class AddEmployee {
	 private KeebootDriver driver = null;
	 // ----------------------------------------------------
   // Class Constructor
   // ----------------------------------------------------
	 
	 
   public AddEmployee(KeebootDriver driver) {
       // Build the page area.
       this.driver = driver;
   }
   
   
   public String formatEmployeeAddRequest(String name,String title,String mobileNumber,String isActive,
		   String emailId,String gender,String department,String designation) throws JSONException
   {
	   JSONObject requestParams = new JSONObject();
	   requestParams.put("name", name);
	   if(title!="") //if nothing is passed as parameter, do nothing(default value1)
	   {
	   requestParams.put("title", title);
	   }
	   requestParams.put("mobileNumber", mobileNumber); //mobile number  is mandatory
	   
	   if(isActive!="")
	   {
	   requestParams.put("isActive", isActive);
	   }
	   if(emailId!="")
	   {
	   requestParams.put("emailId", emailId);
	   }
	   if(gender!="")
	   {
	   requestParams.put("gender", gender);
	   }
	   if(department!="")
	   {
	   requestParams.put("department", department);
	   }
	   if(designation!="")
	   {
	   requestParams.put("designation", designation);
	   }
	   
	   return requestParams.toString();
	   
	   
   }
}
