package com.api.Employee;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.employee.AddEmployee;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddEmployee extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyUserLoggedin() {
	        return CSVDataProvider.getData("/datasheets/customer/verifyThatCustomerIsActiveOrInactive.csv");
	    }

	    @Test(groups = { "login verification", "verifyUserLogin" }, dataProvider = "scenario")
	    public void verifyThatCustomerIsActiveOrInactiveApi(String testScenario) throws JSONException {

	        String testName = "verifyUserLogin";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/profile/employee";
	        
	        String token = "";
	        RestService restService = new RestService();
	        AddEmployee employee  =new  AddEmployee(getDriver());
	        String httpBody =employee.formatEmployeeAddRequest("random", "", "1236547895", "", "", "", "", "");
	        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody );
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        
	       // TestReporter.assertTrue(restResponse.getResponse().contains("Profile details updated successfully"), "Profile details can be edited and updated");
	        
	        
	    }
}
