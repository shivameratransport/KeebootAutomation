package com.api.customer;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.customer.AddCustomer;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddCustomer extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyAddCustomer() {
        return CSVDataProvider.getData("/datasheets/customer/VerifyAddCustomer.csv");
    }

    @Test(groups = { "add customer verification", "VerifyAddCustomer" }, dataProvider = "scenario")
    public void verifyAddCustomer(String testScenario) throws JSONException {

        String testName = "VerifyAddCustomer";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/customer";
        RestService restService = new RestService();
        AddCustomer customer = new AddCustomer(getDriver());
        
        String customerName =  Randomness.randomString(5);
        String mobileNumber =   Randomness.randomNumber(10);
        
        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,customer.formatCustomerRequest("Individual", customerName,mobileNumber, customerName+"@"+customerName+".com"));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jobject = new JSONObject(restResponse.getResponse());
        String customerId = jobject.getString("customerId");
        
        TestReporter.assertTrue((customerId!=""), "New Customer added successfully");
    }
}
