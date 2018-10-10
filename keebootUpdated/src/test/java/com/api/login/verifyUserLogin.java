package com.api.login;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class verifyUserLogin extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyUserLogin() {
	        return CSVDataProvider.getData("\"/datasheets/login/verifyUserLogin.csv");
	    }

	    @Test(groups = { "login verification", "verifyUserLogin" }, dataProvider = "scenario")
	    public void verifyUserLogin(String testScenario) {

	        String testName = "verifyUserLogin";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/login";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse = null;
	        
	        
	        restService.sendGetRequest(ApiUrl);
	       restResponse.getResponse();
	        
	    }
}
