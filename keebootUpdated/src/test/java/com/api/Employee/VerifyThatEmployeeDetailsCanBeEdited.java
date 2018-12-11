package com.api.Employee;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.employee.UpdateEmployee;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyThatEmployeeDetailsCanBeEdited extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] VerifyThatEmployeeDetailsCanBeEditedinapi() {
        return CSVDataProvider.getData("/datasheets/employee/VerifyThatEmployeeDetailsCanBeEdited.csv");
    }

    @Test(groups = { "login verification", "verifyUserLogin" }, dataProvider = "scenario")
    public void VerifyThatEmployeeDetailsCanBeEditedinapi(String testScenario,String gender) throws JSONException {

        String testName = "VerifyThatEmployeeDetailsCanBeEditedinapi";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/profile/employee";
        RestService restService = new RestService();
        UpdateEmployee employee = new UpdateEmployee(getDriver());
        
        RestResponse restResponse=restService.sendGetRequest(ApiUrl+"s", HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jObject = new JSONObject(restResponse.getResponse());

        String employeeId=jObject.getJSONArray("response").getJSONObject(0).getString("employeeId");
       
         restResponse=restService.sendPutRequest(ApiUrl+"/"+employeeId, HeaderType.JSON, employee.formatEmployeeUpdateRequest("",gender, "", "", "", "", "", "", "", "", "",""));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
    }
}
