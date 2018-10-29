package com.api.fleet;

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

public class VerifyDeleteFleet extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyDeleteFleet() {
	        return CSVDataProvider.getData("/datasheets/fleet/VerifyDeleteFleet.csv");
	    }

	    @Test(groups = { "delete fleet verification", "VerifyDeleteFleet" }, dataProvider = "scenario")
	    public void verifyDeleteFleet(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteFleet";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/vehicle";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        JSONObject jObject = new JSONObject(restResponse.getResponse());
	       String vehicleId = jObject.getJSONArray("response").getJSONObject(0).getString("vehicleId");
	        
	       	restResponse = restService.sendDeleteRequest(ApiUrl+"/"+vehicleId,HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        
	        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "fleet deleted successfully");
	        
	    }
}
