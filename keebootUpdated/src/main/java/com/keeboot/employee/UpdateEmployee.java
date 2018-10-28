package com.keeboot.employee;

import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class UpdateEmployee {

private KeebootDriver driver = null;
// ----------------------------------------------------
// Class Constructor
// ----------------------------------------------------
	 
	 
public UpdateEmployee(KeebootDriver driver) {
    // Build the page area.
    this.driver = driver;
}


public String formatEmployeeUpdateRequest(String name,String gender,String department,
		String designation,String emailId,
		String reportingToID,String reportingToName,String notes,String address,String city,
		String state,String isActive) throws JSONException
{
	JSONObject requestParams = new JSONObject();
	if(!name.equals(""))
	{
	   requestParams.put("name", name);
	}
	if(!gender.equals(""))
	{
	   requestParams.put("gender", gender);
	}
	if(!department.equals(""))
	{
	   requestParams.put("department", department);
	}
	if(!designation.equals(""))
	{
		 requestParams.put("designation", designation);
	}
	  if(!emailId.equals(""))
	  {
		  requestParams.put("emailId", emailId);
	  }
	   if(!reportingToID.equals(""))
	   {
		   requestParams.put("reportingToID", reportingToID);
	   }
	  
	  if(!reportingToName.equals(""))
	  {
		  requestParams.put("reportingToName", reportingToName);
	  }
	  if(!notes.equals(""))
	  {
		  requestParams.put("notes", notes);  
	  }
	  if(!address.equals(""))
	  {
		  requestParams.put("address", address);
	  }
	  if(!city.equals(""))
	  {
		  requestParams.put("city", city);
	  }
	   if(!state.equals(""))
	   {
		   requestParams.put("state", state);
	   }
	   if(!isActive.equals(""))
	   {
		   requestParams.put("isActive", isActive);
	   }
	 
	   
	   return requestParams.toString();
}
}

//while updating, separate methods has to be written based on the get response
// separate format methods for bank details, contact details,personal details,others,address

//"name": "2",
//"gender": "Male",
//"mobileNumber": "+911234567891",
//"department": "IT",
//"designation": "Director",
//"phone": "",
//"roles": [
//  "",
//  "FINANCE"
//],
//"title": "Mr.",
//"profileImage": "IMAGER_F53F9470D37111E8B0F71F3FFAB021B5",
//"emailId": "",
//"reportingToID": "l/.;l[p[k8oy",
//"reportingToName": "ryht6yi",
//"notes": "",
//"isActive": true,
//"isAadharVerified": true,
//"address": {
//  "address": "sanjay nagar",
//  "city": "bangalore",
//  "state": "karnataka",
//  "zip": "566006",
//  "country": "india"
//},
//"bankDetails": {
//  "bankName": "dfgtg",
//  "branchName": "jlk",
//  "accountName": "jk;",
//  "accountNumber": "rdhdt",
//  "IFSC": "kl;/",
//  "cardNumber": "k;/",
//  "accountType": "ljk;"
//},
//"contactDetails": {
//  "emergencyContactName": "anyone",
//  "emergencyContactMobileNumber": "+911000000000",
//  "emergencyContactRelation": "bro"
//},
//"others": {
//  "ratings": "",
//  "points": ""
//},
//"personalDetails": {
//  "PAN": "tyffjk",
//  "PANDoc": "",
//  "idProofNumber": "+abcd",
//  "idProofDoc": "",
//  "joiningDate": "2018-10-16",
//  "terminationDate": "2018-10-17",
//  "DOB": "2018-10-16",
//  "bloodGroup": "AB+",
//  "notes": "gnhgjfsr"
//},
//"tenantId": "E8A4A90A78AC4649B1A39665CE54C704",
//"employeeId": "8689CE0D37C14BDA8E110BDE50695675",
//"userId": "E0A22B700CD64104B713D18E4855C103",
//"slNo": "4",
//"isDeleted": false,
//"createdTs": "2018-10-16 14:10:54"
//},