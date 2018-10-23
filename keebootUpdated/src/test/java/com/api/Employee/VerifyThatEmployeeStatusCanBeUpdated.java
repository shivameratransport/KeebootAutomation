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
import com.keeboot.employee.UpdateEmployee;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyThatEmployeeStatusCanBeUpdated extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyUserLoggedin() {
	        return CSVDataProvider.getData("/datasheets/employee/VerifyThatEmployeeDetailsCanBeEdited.csv");
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
	        
	      String userId=jObject.getJSONArray("response").getJSONObject(5).getString("userId");
	      String updatedUrl = "https://api.staging.keeboot.com/profile/employee/"+userId;
	      UpdateEmployee employee = new UpdateEmployee(getDriver());
	      restService.sendPutRequest(updatedUrl, HeaderType.JSON, employee.formatEmployeeUpdateRequest("", "", "", "", "", "", "", "", "", "", "", "","false"));
	      
	      TestReporter.assertTrue(restResponse.getResponse().contains("Profile details updated successfully"), "Employee details updated successfully");
	      

	    }
}
