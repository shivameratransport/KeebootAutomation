package com.api.vendor;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;
import com.keeboot.vendor.AddVendor;

public class VerifyEditVendor extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditVendor() {
        return CSVDataProvider.getData("/datasheets/vendor/VerifyEditVendor.csv");
    }

    @Test(groups = { "vendor edit verification", "VerifyEditVendor" }, dataProvider = "scenario")
    public void verifyEditVendor(String testScenario) throws JSONException {

        String testName = "VerifyEditVendor";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.qe.keeboot.com/vendor";
        RestService restService = new RestService();
        
        //get call to get all the vendor details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the vendor id to edit
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String vendorId=jObject.getJSONArray("response").getJSONObject(0).getString("vendorId");
        AddVendor vendor = new AddVendor(getDriver());
        String name =  Randomness.randomString(5);
        
        restResponse=restService.sendPutRequest(ApiUrl+"/"+vendorId, HeaderType.JSON,vendor.formatVendorRequest(name, "", ""));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "vendor details edited and updated successfully");
    }
}
