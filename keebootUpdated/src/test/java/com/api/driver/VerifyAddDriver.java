package com.api.driver;

import org.json.JSONException;
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

public class VerifyAddDriver extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyAddDriver() {
	        return CSVDataProvider.getData("/datasheets/driver/VerifyAddDriver.csv");
	    }

	    @Test(groups = { "verify that driver can be added", "VerifyAddDriver" }, dataProvider = "scenario")
	    public void verifyAddDriver(String testScenario) throws JSONException {

	        String testName = "VerifyAddDriver";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/driver";
	        String mobileNumber = Randomness.randomNumber(10);
	        String DriverName =  Randomness.randomString(5);
	        
	        RestService restService = new RestService();
	        AddDriver driver = new AddDriver(getDriver());
	        
	        
	        String httpBody = driver.formatDriverRequest(DriverName, "Supplier",mobileNumber);
	        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	    }
}
