package com.api.driver;

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

public class VerifyDeleteDriver extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyDeleteDriver() {
	        return CSVDataProvider.getData("/datasheets/driver/VerifyDeleteDriver.csv");
	    }

	    @Test(groups = { "delete driver verification", "VerifyDeleteDriver" }, dataProvider = "scenario")
	    public void verifyDeleteDriver(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteDriver";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/driver";
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        JSONObject jObject = new JSONObject(restResponse.getResponse());
	        String driverId = jObject.getJSONArray("response").getJSONObject(0).getString("driverId");
	        restResponse = restService.sendDeleteRequest(ApiUrl+"/"+driverId, HeaderType.JSON);
	        
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	    }
}
