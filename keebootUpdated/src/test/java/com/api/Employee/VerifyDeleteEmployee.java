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

public class VerifyDeleteEmployee extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] VerifyDeleteEmployeeInApi() {
	        return CSVDataProvider.getData("/datasheets/employee/VerifyDeleteEmployee.csv");
	    }

	    @Test(groups = { "login verification", "VerifyDeleteEmployeeInApi" }, dataProvider = "scenario")
	    public void VerifyDeleteEmployeeInApi(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteEmployeeInApi";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.qe.keeboot.com/profile/employee";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl+"s", HeaderType.JSON);
	        
	        JSONObject jObject = new JSONObject(restResponse.getResponse());

	        String employeeId=jObject.getJSONArray("response").getJSONObject(0).getString("employeeId");
	        
	        TestReporter.logStep("The employee id to be deleted is " + employeeId);
	        
	         restResponse = restService.sendDeleteRequest(ApiUrl+"/"+employeeId,HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        
	    }
}
