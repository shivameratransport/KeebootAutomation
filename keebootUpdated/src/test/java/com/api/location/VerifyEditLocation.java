package com.api.location;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.location.EditLocation;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditLocation extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditLocation() {
        return CSVDataProvider.getData("/datasheets/fuelcard/VerifyEditLocation.csv");
    }

    @Test(groups = { "verify that location can be edited", "VerifyEditLocation" }, dataProvider = "scenario")
    public void verifyEditLocation(String testScenario) throws JSONException {

        String testName = "VerifyEditLocation";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/location";
        RestService restService = new RestService();
        
        //get call to get all the location details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the location id to edit
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String locationId=jObject.getJSONArray("response").getJSONObject(0).getString("locationId");
        
       EditLocation location = new EditLocation(getDriver());
       restResponse=restService.sendPutRequest(ApiUrl+"/"+locationId, HeaderType.JSON,location.formatLocationEditRequest("", "", "", "", "", "", ""));
       Common.validateStatusCode(restResponse.getStatusCode(),200);
       
    }
}
