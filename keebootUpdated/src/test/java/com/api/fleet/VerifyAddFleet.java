package com.api.fleet;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.fleet.AddFleet;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddFleet extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyAddFleet() {
        return CSVDataProvider.getData("/datasheets/fleet/VerifyAddFleet.csv");
    }

    @Test(groups = { "Add fleet verification", "VerifyAddFleet" }, dataProvider = "scenario")
    public void verifyAddFleet(String testScenario) throws JSONException {

        String testName = "VerifyAddFleet";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/vehicle";
        RestService restService = new RestService();
        
        String fleetName =  Randomness.randomString(5);
       String vehicleNumber =  Randomness.randomAlphaNumeric(5);
    //String VehicleType =    Randomness.randomString(5);
        
        AddFleet fleet = new AddFleet(getDriver());
        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,fleet.formatFleetRequest(fleetName, "OWN", vehicleNumber,"6Tyre Full Body (9MT) 22Ft "));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jobject = new JSONObject(restResponse.getResponse());
        String vehicleId = jobject.getString("vehicleId");
        
        TestReporter.assertTrue((!vehicleId.equals("")), "fleet added successfully");
        
        
    }
}
