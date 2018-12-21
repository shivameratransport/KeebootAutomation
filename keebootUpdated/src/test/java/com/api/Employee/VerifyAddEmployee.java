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
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddEmployee extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyUserLoggedin() {
	        return CSVDataProvider.getData("/datasheets/employee/VerifyAddEmployee.csv");
	    }

	    @Test(groups = { "login verification", "verifyUserLogin" }, dataProvider = "scenario")
	    public void verifyThatCustomerIsActiveOrInactiveApi(String testScenario) throws JSONException {

	        String testName = "verifyUserLogin";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.qe.keeboot.com/profile/employee";
	        
	        int mobileNum =  Randomness.randomNumberBetween(111111111,999999999);
	        String employeeName =  Randomness.randomString(5);
	        RestService restService = new RestService();
	        AddEmployee employee  =new  AddEmployee(getDriver());
	        String httpBody =employee.formatEmployeeAddRequest(employeeName, "","9"+mobileNum+"", "", "", "", "", "");
	        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody );
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        
	      TestReporter.assertTrue(restResponse.getResponse().contains("employeeId"), "Employee added successfully");
	        
	        
	    }
}
