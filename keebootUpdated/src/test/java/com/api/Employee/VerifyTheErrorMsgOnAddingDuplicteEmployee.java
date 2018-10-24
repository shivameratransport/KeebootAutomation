package com.api.Employee;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.employee.AddEmployee;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyTheErrorMsgOnAddingDuplicteEmployee extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyUserLoggedin() {
	        return CSVDataProvider.getData("/datasheets/employee/VerifyThatEmployeeDetailsCanBeEdited.csv");
	    }

	    @Test(groups = { "login verification", "verifyUserLogin" }, dataProvider = "scenario")
	    public void verifyThatCustomerIsActiveOrInactiveApi(String testScenario) throws JSONException {

	        String testName = "verifyUserLogin";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/profile/employee";
	        
	        
	        RestService restService = new RestService();
	        AddEmployee employee  =new  AddEmployee(getDriver());
	        
	        int mobileNum =  Randomness.randomNumberBetween(1111111111,999999999);
	        String employeeName =  Randomness.randomString(5);
	        
	        
	        String httpBody =employee.formatEmployeeAddRequest(employeeName, "", mobileNum+"", "", "", "", "", "");
	        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody );
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        
	      TestReporter.assertTrue(restResponse.getResponse().contains("employeeId"), "Employee added successfully");
	      
	      //trying to add the duplicate entry of employee
	      httpBody =employee.formatEmployeeAddRequest(employeeName, "",  mobileNum+"", "", "", "", "", "");
	       restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody );
	       Common.validateStatusCode(restResponse.getStatusCode(),200);
	       
	       
	       TestReporter.assertTrue(restResponse.getResponse().contains("Requested employee details already exist"), "Employee  already exists, duplicate entries not allowed");
	      
	        
	    }
}
