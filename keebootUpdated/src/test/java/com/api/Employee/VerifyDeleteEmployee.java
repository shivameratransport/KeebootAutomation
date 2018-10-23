package com.api.Employee;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyDeleteEmployee extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] VerifyDeleteEmployeeInApi() {
	        return CSVDataProvider.getData("/datasheets/customer/verifyThatCustomerIsActiveOrInactive.csv");
	    }

	    @Test(groups = { "login verification", "VerifyDeleteEmployeeInApi" }, dataProvider = "scenario")
	    public void VerifyDeleteEmployeeInApi(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteEmployeeInApi";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/profile/employee";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse = restService.sendDeleteRequest(ApiUrl+"/9F0F87A534FF44449468ECF9D30326E3",HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        
	    }
}
