package com.api.login;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.api.APILogin;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyUserLogin extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyUserLoggedin() {
	        return CSVDataProvider.getData("/datasheets/login/verifyUserLogin.csv");
	    }

	    @Test(groups = { "login verification", "verifyUserLogin" }, dataProvider = "scenario")
	    public void verifyUserLoggedin(String testScenario) throws JSONException {

	        String testName = "verifyUserLogin";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/login/generate-otp";
	        
	        RestService restService = new RestService();
	     
	        APILogin apiLogin = new APILogin(getDriver());
	        
	        //send otp request-generate otp
	        String httpBody = apiLogin.formatGenerateOtpBody("9035434312", "KEEBOOT-MAIN-WEB");
	        RestResponse restResponse=restService.sendPostRequest(ApiUrl,HeaderType.JSON,httpBody);
	        restResponse.getStatusCode();
	    
	        
	    }
}
