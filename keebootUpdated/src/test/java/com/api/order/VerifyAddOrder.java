package com.api.order;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.order.AddOrder;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddOrder extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyAddOrder() {
        return CSVDataProvider.getData("/datasheets/order/VerifyAddOrder.csv");
    }

    @Test(groups = { "add order verification", "VerifyAddOrder" }, dataProvider = "scenario")
    public void verifyAddOrder(String testScenario) throws JSONException {

        String testName = "VerifyAddOrder";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/order";
        RestService restService = new RestService();
        
        //to get the vehicle type and id
        RestResponse restResponse=restService.sendGetRequest("https://api.staging.keeboot.com/vehicle", HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
       JSONObject jObject = new JSONObject(restResponse.getResponse());
       String vehicleId = jObject.getJSONArray("response").getJSONObject(0).getString("vehicleId");
       String vehicleType = jObject.getJSONArray("response").getJSONObject(0).getString("vehicleType");
       
     //get call to get all the customer details
        restResponse=restService.sendGetRequest("https://api.staging.keeboot.com/customer/mini", HeaderType.JSON);
       Common.validateStatusCode(restResponse.getStatusCode(),200);
       
       //capturing the customer id to edit
        jObject = new JSONObject(restResponse.getResponse());
       String customerId=jObject.getJSONArray("response").getJSONObject(0).getString("customerId");
       String customerName =jObject.getJSONArray("response").getJSONObject(0).getString("customerName");
       String aliasOrShortName =jObject.getJSONArray("response").getJSONObject(0).getString("aliasOrShortName");
       
       
       //get call to get all the location details
       restResponse=restService.sendGetRequest("https://api.staging.keeboot.com/location/mini", HeaderType.JSON);
       Common.validateStatusCode(restResponse.getStatusCode(),200);
      
       //capturing the location id to edit
       jObject = new JSONObject(restResponse.getResponse());
       String locationId=jObject.getJSONArray("response").getJSONObject(0).getString("locationId");
       String locationCode =jObject.getJSONArray("response").getJSONObject(0).getString("locationCode");
       String description =jObject.getJSONArray("response").getJSONObject(0).getString("description");
       String locationType =jObject.getJSONArray("response").getJSONObject(0).getString("locationType");
      
        AddOrder order = new AddOrder(getDriver());
       restResponse = restService.sendPostRequest(ApiUrl, HeaderType.JSON, order.formatOrderRequest("Contract", vehicleType, vehicleId, "Cement", "4A2FA200DDA811E899370D580EE580F3", 
    		   customerName, customerId, aliasOrShortName, locationCode, description, locationType, locationId));
      Common.validateStatusCode(restResponse.getStatusCode(),200);
      
      JSONObject jobject = new JSONObject(restResponse.getResponse());
      String orderId = jobject.getJSONObject("response").getString("orderId");
      
      TestReporter.assertTrue((orderId!=""), "New order added successfully");
      
    }
}
