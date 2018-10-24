package com.api.Employee;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyThatEmployeeIsActiveByDefault extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyUserLoggedin() {
	        return CSVDataProvider.getData("/datasheets/employee/VerifyThatEmployeeIsActiveByDefault.csv");
	    }

	    @Test(groups = { "login verification", "VerifyThatEmployeeIsActiveByDefault" }, dataProvider = "scenario")
	    public void VerifyThatEmployeeIsActiveByDefaultInApi(String testScenario) throws JSONException {

	        String testName = "VerifyThatEmployeeIsActiveByDefault";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/profile/employees";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl,HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        JSONObject jObject = new JSONObject(restResponse.getResponse());
	        
	      Boolean isActive=jObject.getJSONArray("response").getJSONObject(0).getBoolean("isActive");
	        TestReporter.assertTrue(isActive, "The customer is in active status");
		     
	        
	    }
}
