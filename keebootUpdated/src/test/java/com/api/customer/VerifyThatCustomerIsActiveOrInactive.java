package com.api.customer;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.api.APILogin;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;
import com.keeboot.Common;

public class VerifyThatCustomerIsActiveOrInactive extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyUserLoggedin() {
	        return CSVDataProvider.getData("/datasheets/customer/verifyThatCustomerIsActiveOrInactive.csv");
	    }

	    @Test(groups = { "verification", "VerifyThatCustomerIsActiveOrInactive" }, dataProvider = "scenario")
	    public void verifyThatCustomerIsActiveOrInactiveApi(String testScenario) throws JSONException {

	        String testName = "VerifyThatCustomerIsActiveOrInactive";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.keeboot.com/profile/company";
	        
	        String token = "";
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl,HeaderType.JSON,APILogin.formatAuthorization(token));
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        JSONObject jObject = new JSONObject(restResponse.getResponse());
	        Boolean isActive = jObject.getJSONObject("response").getBoolean("isActive");
	        
	        TestReporter.assertTrue(isActive, "The customer is in active status");
	     
	     
}
}
