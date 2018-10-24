package com.api.owner;

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

public class VerifyDeleteOwner extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyDeleteOwner() {
	        return CSVDataProvider.getData("/datasheets/employee/VerifyDeleteEmployee.csv");
	    }

	    @Test(groups = { "login verification", "VerifyDeleteOwner" }, dataProvider = "scenario")
	    public void verifyDeleteOwner(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteOwner";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/owner";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
	        
	        JSONObject jObject = new JSONObject(restResponse.getResponse());

	        String ownerId=jObject.getJSONArray("response").getJSONObject(0).getString("ownerId");
	         restResponse = restService.sendDeleteRequest(ApiUrl+"/"+ownerId,HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	    }
}
