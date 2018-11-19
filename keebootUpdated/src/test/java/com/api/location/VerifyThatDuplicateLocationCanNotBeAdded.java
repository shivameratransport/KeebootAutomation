package com.api.location;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.location.AddLocation;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyThatDuplicateLocationCanNotBeAdded extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyThatDuplicateLocationCanNotBeAdded() {
	        return CSVDataProvider.getData("/datasheets/location/VerifyAddLocation.csv");
	    }

	    @Test(groups = { "verify that Duplicate location can't be added", "VerifyThatDuplicateLocationCanNotBeAdded" }, dataProvider = "scenario")
	    public void verifyThatDuplicateLocationCanNotBeAdded(String testScenario,String lat,
	    		String lng,String address,String state,String city,String locationType) throws JSONException {

	        String testName = "VerifyThatDuplicateLocationCanNotBeAdded";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/location";
	        
	        RestService restService = new RestService();
	        AddLocation location = new AddLocation(getDriver());
	        Boolean isActive= true;
	        String LocationCode =  Randomness.randomString(5);
	        double latittude =  Double.parseDouble(lat);
	        double longittude =  Double.parseDouble(lng);
	        String httpBody =location.formatLocationAddRequest("", 1000.0, latittude, longittude, address, LocationCode, state, city, locationType, isActive);
	        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody);
	        
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        TestReporter.assertTrue(restResponse.getResponse().contains("locationId"), "location added successfully");
	        
	        restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        
	        TestReporter.assertTrue(restResponse.getResponse().contains("errorResponse"), "Duplicate Location can not be added");
	    }
}
