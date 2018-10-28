package com.api.driver;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestService;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddDriver extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyAddDriver() {
	        return CSVDataProvider.getData("/datasheets/location/VerifyAddLocation.csv");
	    }

	    @Test(groups = { "verify that driver can be added", "VerifyAddDriver" }, dataProvider = "scenario")
	    public void verifyAddDriver(String testScenario,String lat,
	    		String lng,String address,String state,String city,String locationType) throws JSONException {

	        String testName = "VerifyAddDriver";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/driver";
	        
	        RestService restService = new RestService();
	    }
}
