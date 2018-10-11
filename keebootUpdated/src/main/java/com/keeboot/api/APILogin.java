package com.keeboot.api;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.keeboot.web.KeebootDriver;

public class APILogin {

	
	private KeebootDriver driver = null;
	
	
	 public APILogin(KeebootDriver driver) {
	        // Build the page area.
	        this.driver = driver;
	    }
	 
	 public String formatGenerateOtpBody(String mobileNum, String appType) throws JSONException
	 {
		 
		 JSONObject requestParams = new JSONObject();
		 appType = "KEEBOOT-MAIN-WEB";
		 
		 
		 requestParams.put("mobileNumber", mobileNum);
		 requestParams.put("appType", appType);
		 
		 return requestParams.toString();
	 }
	 
	 public static List<NameValuePair> formatAuthorization(String tokenID) {
	        List<NameValuePair> aList = new ArrayList<NameValuePair>();
	        aList.add(new BasicNameValuePair("authorization_token", tokenID));
	        return aList;
	    }

}
