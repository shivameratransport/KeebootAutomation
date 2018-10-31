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
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyDeleteCustomer extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyDeleteCustomer() {
	        return CSVDataProvider.getData("/datasheets/customer/verifyDeleteCustomer.csv");
	    }

	    @Test(groups = { "customer delete verification", "VerifyDeleteCustomer" }, dataProvider = "scenario")
	    public void verifyDeleteCustomer(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteCustomer";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/customer";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl+"/mini", HeaderType.JSON);
	        
	        //capturing the customer id to delete
	        JSONObject jObject = new JSONObject(restResponse.getResponse());
	        String customerId=jObject.getJSONArray("response").getJSONObject(0).getString("customerId");
	         restResponse = restService.sendDeleteRequest(ApiUrl+"/"+customerId,HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "owner deleted successfully");
	        
	    }

}
