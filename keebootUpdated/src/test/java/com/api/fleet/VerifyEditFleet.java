package com.api.fleet;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.fleet.EditFleet;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditFleet extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditFleet() {
        return CSVDataProvider.getData("/datasheets/fleet/VerifyEditFleet.csv");
    }

    @Test(groups = { "edit fleet verification", "VerifyEditFleet" }, dataProvider = "scenario")
    public void verifyEditFleet(String testScenario) throws JSONException {

        String testName = "VerifyEditFleet";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/vehicle";
        RestService restService = new RestService();
        EditFleet fleet = new EditFleet(getDriver());
        String model = Randomness.randomNumber(4);
        
        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
       JSONObject jObject = new JSONObject(restResponse.getResponse());
       String vehicleId = jObject.getJSONArray("response").getJSONObject(0).getString("vehicleId");
       
       restResponse=restService.sendPutRequest(ApiUrl+"/"+vehicleId, HeaderType.JSON,fleet.formatFleetRequest("", "", "", "", "", model, "", "", ""));
       Common.validateStatusCode(restResponse.getStatusCode(),200);
       TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "fleet details edited and updated successfully");

    }
}
