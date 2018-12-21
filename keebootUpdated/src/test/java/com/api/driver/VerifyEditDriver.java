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
import com.keeboot.driver.AddDriver;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditDriver extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditDriver() {
        return CSVDataProvider.getData("/datasheets/driver/VerifyEditDriver.csv");
    }

    @Test(groups = { "edit driver verification", "VerifyEditDriver" }, dataProvider = "scenario")
    public void verifyEditDriver(String testScenario) throws JSONException {

        String testName = "VerifyEditDriver";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.qe.keeboot.com/driver";
        String mobileNumber = Randomness.randomNumber(10);
        
        RestService restService = new RestService();
        
        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        AddDriver driver = new AddDriver(getDriver());
        
        
        String driverId = jObject.getJSONArray("response").getJSONObject(0).getString("driverId");
        restResponse=restService.sendPutRequest(ApiUrl+"/"+driverId, HeaderType.JSON, driver.formatDriverRequest("", "", mobileNumber));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "driver details edited and updated successfully");
    }
}
