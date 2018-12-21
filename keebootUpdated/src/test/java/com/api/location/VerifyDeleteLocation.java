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
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyDeleteLocation extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyDeleteLocation() {
	        return CSVDataProvider.getData("/datasheets/location/VerifyDeleteLocation.csv");
	    }

	    @Test(groups = { "verify if the location can be deleted", "VerifyDeleteLocation" }, dataProvider = "scenario")
	    public void verifyDeleteLocation(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteLocation";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.qe.keeboot.com/location";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
	        
	        JSONObject jObject = new JSONObject(restResponse.getResponse());
	        String locationId =jObject.getJSONArray("response").getJSONObject(0).getString("locationId");
	       
	       restResponse = restService.sendDeleteRequest(ApiUrl+"/"+locationId,HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	    }
}
