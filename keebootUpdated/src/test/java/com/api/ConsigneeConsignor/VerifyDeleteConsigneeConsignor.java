package com.api.ConsigneeConsignor;

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

public class VerifyDeleteConsigneeConsignor extends BaseRestTest{
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyDeleteConsigneeConsignor() {
		
        return CSVDataProvider.getData("/datasheets/consigneeConsignor/VerifyAddConsigneeConsignor.csv");
    }

    @Test(groups = { "verify that consignee consignor can be deleted", "VerifyDeleteConsigneeConsignor" }, dataProvider = "scenario")
    public void verifyDeleteConsigneeConsignor(String testScenario,String type) throws JSONException {

        String testName = "VerifyDeleteConsigneeConsignor";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.qe.keeboot.com/consignment";
       
        
        RestService restService = new RestService();
        
        
        //get call to get all the ConsigneeConsignor details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String ccId=jObject.getJSONArray("response").getJSONObject(0).getString("ccId");
        
       
        restResponse = restService.sendDeleteRequest(ApiUrl+"/"+ccId,HeaderType.JSON);
        
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("0"), "consigneeConsignor added successfully");
        
        
    }
}
