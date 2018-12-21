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
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyDeleteVendor extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyDeleteVendor() {
	        return CSVDataProvider.getData("/datasheets/vendor/VerifyDeleteVendor.csv");
	    }

	    @Test(groups = { "vendor  delete verification", "VerifyDeleteVendor" }, dataProvider = "scenario")
	    public void verifyDeleteVendor(String testScenario) throws JSONException {

	        String testName = "VerifyDeleteVendor";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.qe.keeboot.com/vendor";
	        
	        //get call to get all the vendor details
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        //capturing the vendor id to edit
	        JSONObject jObject = new JSONObject(restResponse.getResponse());
	        String vendorId=jObject.getJSONArray("response").getJSONObject(0).getString("vendorId");
	        
	        
	         restResponse = restService.sendDeleteRequest(ApiUrl+"/"+vendorId,HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "owner deleted successfully");
	        
	    }

}
