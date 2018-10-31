package com.api.customer;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.customer.AddCustomer;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditCustomer extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditCustomer() {
        return CSVDataProvider.getData("/datasheets/customer/VerifyEditCustomer.csv");
    }

    @Test(groups = { "customer edit verification", "VerifyEditCustomer" }, dataProvider = "scenario")
    public void verifyEditCustomer(String testScenario) throws JSONException {

        String testName = "VerifyEditCustomer";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/customer";
        RestService restService = new RestService();
        
        //get call to get all the customer details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl+"/mini", HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the customer id to edit
        AddCustomer customer = new AddCustomer(getDriver());
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String customerId=jObject.getJSONArray("response").getJSONObject(0).getString("customerId");
        
        String customeremail =  Randomness.randomString(5);
        
         restResponse=restService.sendPutRequest(ApiUrl+"/"+customerId, HeaderType.JSON,customer.formatCustomerRequest("", "","", customeremail+"@"+customeremail+".com"));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "customer details edited and updated successfully");
    }
}
