package com.api.indent;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.indent.AddIndent;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddIndent extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyAddIndent() {
        return CSVDataProvider.getData("/datasheets/indent/VerifyAddIndent.csv");
    }

    @Test(groups = { "add indent verification", "VerifyAddIndent" }, dataProvider = "scenario")
    public void verifyAddIndent(String testScenario) throws JSONException {

        String testName = "VerifyAddIndent";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/indent";
        RestService restService = new RestService();
        AddIndent indent = new AddIndent(getDriver());
        
        
      //get call to get all the customer details
        RestResponse restResponse=restService.sendGetRequest("https://api.staging.keeboot.com/customer/mini", HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the customer id to edit
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String customerId=jObject.getJSONArray("response").getJSONObject(0).getString("customerId");
        String customerName =jObject.getJSONArray("response").getJSONObject(0).getString("customerName");
      String vehicleTypeId = Randomness.randomAlphaNumeric(5);
      String goodsTypeId = Randomness.randomAlphaNumeric(5);
        
      String pickupDateTime = Randomness.generateCurrentDatetime();
   
      //get call to get all the location details
    restResponse=restService.sendGetRequest("https://api.staging.keeboot.com/location/mini", HeaderType.JSON);
    Common.validateStatusCode(restResponse.getStatusCode(),200);
   
    //capturing the location id to edit
    jObject = new JSONObject(restResponse.getResponse());
    String locationId=jObject.getJSONArray("response").getJSONObject(0).getString("locationId");
    String locationCode =jObject.getJSONArray("response").getJSONObject(0).getString("locationCode");
    String description =jObject.getJSONArray("response").getJSONObject(0).getString("description");
    
        
         restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,indent.formatIndentAddRequest(customerName, customerId, "Manual", "Spot", "6Tyre Half Body (6MT) 19Ft", vehicleTypeId, 5, "Agricultural Product", goodsTypeId, "LTL",
        		 pickupDateTime, pickupDateTime, 9.33, 69.89, true, locationId, locationCode, description));
         Common.validateStatusCode(restResponse.getStatusCode(),200);
         
         jObject = new JSONObject(restResponse.getResponse());
        String indentId = jObject.getJSONObject("response").getString("indentId");
        
        
        
        TestReporter.assertTrue((indentId!=""), "New indent added successfully");
        
        
    }
}
