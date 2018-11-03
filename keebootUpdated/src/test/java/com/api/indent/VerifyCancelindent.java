package com.api.indent;

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

public class VerifyCancelindent extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyCancelindent() {
	        return CSVDataProvider.getData("/datasheets/indent/VerifyCancelindent.csv");
	    }

	    @Test(groups = { "cancel indent verification", "VerifyCancelindent" }, dataProvider = "scenario")
	    public void verifyCancelindent(String testScenario) throws JSONException {

	        String testName = "VerifyCancelindent";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/indent";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
	        
	        JSONObject jObject = new JSONObject(restResponse.getResponse());

	        String indentId=jObject.getJSONArray("response").getJSONObject(0).getString("indentId");
	        
	        
	        
	         restResponse = restService.sendPutRequest(ApiUrl+"/"+indentId+"/cancel", HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        TestReporter.assertTrue(restResponse.getResponse().contains("indentId"), "indent cancelled successfully");
	        
	    }

}
