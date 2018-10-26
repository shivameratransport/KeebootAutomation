package com.api.fuelcard;

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

public class VerifyDeleteFuelCard extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyDeleteFuelCard() {
	        return CSVDataProvider.getData("/datasheets/fuelcard/VerifyDeleteFuelCard.csv");
	    }

	    @Test(groups = { "VerifyDeleteFuelCard", "VerifyDeleteFuelCard" }, dataProvider = "scenario")
	    public void verifyDeleteFuelCard(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteFuelCard";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/fuelcard";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
	        JSONObject jObject = new JSONObject(restResponse.getResponse());

	        String fuelcardId=jObject.getJSONArray("response").getJSONObject(0).getString("fuelcardId");
	        restResponse = restService.sendDeleteRequest(ApiUrl+"/"+fuelcardId,HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	    }
}
