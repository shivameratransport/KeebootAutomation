package com.api.location;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.location.AddLocation;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddLocation extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyAddLocation() {
	        return CSVDataProvider.getData("/datasheets/fuelcard/VerifyAddFuelCard.csv");
	    }

	    @Test(groups = { "verify that location can be added", "VerifyAddLocation" }, dataProvider = "scenario")
	    public void verifyAddLocation(String testScenario) throws JSONException {

	        String testName = "VerifyAddFuelCard";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/location";
	        
	        RestService restService = new RestService();
	        AddLocation location = new AddLocation(getDriver());
	        
	        String httpBody =location.formatLocationAddRequest("", "", "", "", "", "", "", "", "", "True");
	        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody);
	        
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        JSONObject jobject = new JSONObject(restResponse.getResponse());
	        
	    }
}