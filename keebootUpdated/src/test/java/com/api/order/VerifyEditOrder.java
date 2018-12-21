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
import com.keeboot.order.AddOrder;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditOrder extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditOrder() {
        return CSVDataProvider.getData("/datasheets/order/VerifyEditOrder.csv");
    }

    @Test(groups = { "order edit verification", "VerifyEditOrder" }, dataProvider = "scenario")
    public void verifyEditOrder(String testScenario) throws JSONException {

        String testName = "VerifyEditOrder";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.qe.keeboot.com/order";
        RestService restService = new RestService();
        
        //get call to get all the customer details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl+"/mini", HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the customer id to edit
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String orderId=jObject.getJSONArray("response").getJSONObject(0).getString("orderId");
        
       
       AddOrder order = new AddOrder(getDriver());
        restResponse=restService.sendPutRequest(ApiUrl+"/"+orderId, HeaderType.JSON, order.formatOrderRequest("Spot", "", "", "", "", "", "", "", "", "", "", ""));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "order details edited and updated successfully");
    }
}
