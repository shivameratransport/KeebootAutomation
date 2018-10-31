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

public class VerifyAddVendor  extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyAddVendor() {
        return CSVDataProvider.getData("/datasheets/owner/VerifyAddOwner.csv");
    }

    @Test(groups = { "vendor add verification", "VerifyAddVendor" }, dataProvider = "scenario")
    public void verifyAddVendor(String testScenario) throws JSONException {

        String testName = "VerifyAddVendor";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/vendor";
        RestService restService = new RestService();
        AddVendor vendor = new AddVendor(getDriver());
        String name =  Randomness.randomString(5);
        String mobileNumber = Randomness.randomNumber(10);
        
        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,vendor.formatVendorRequest(name, mobileNumber, "Supplier"));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jobject = new JSONObject(restResponse.getResponse());
        String vendorId = jobject.getJSONObject("response").getString("vendorId");
        
        TestReporter.assertTrue((vendorId!=""), "New vendor added successfully");
        
        
    }
}
