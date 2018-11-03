package com.api.order;

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

public class VerifyCancelOrder extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyCancelOrder() {
	        return CSVDataProvider.getData("/datasheets/order/VerifyCancelOrder.csv");
	    }

	    @Test(groups = { "cancel order verification", "VerifyCancelOrder" }, dataProvider = "scenario")
	    public void verifyCancelOrder(String testScenario) throws JSONException {

	        String testName = "VerifyCancelOrder";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.staging.keeboot.com/order";
	        
	        RestService restService = new RestService();
	        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
	        
	        JSONObject jObject = new JSONObject(restResponse.getResponse());

	        String orderId=jObject.getJSONArray("response").getJSONObject(0).getString("orderId");
	        
	        
	        
	         restResponse = restService.sendPutRequest(ApiUrl+"/"+orderId+"/cancel", HeaderType.JSON);
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        TestReporter.assertTrue(restResponse.getResponse().contains("order cancelled successfully"), "order cancelled successfully");
	    }
}
