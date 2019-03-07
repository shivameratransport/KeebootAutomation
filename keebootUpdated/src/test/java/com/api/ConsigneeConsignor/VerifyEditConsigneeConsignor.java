package com.api.ConsigneeConsignor;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.ConsigneeConsignor.EditConsigneeConsignor;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditConsigneeConsignor extends BaseRestTest{
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditConsigneeConsignor() {
		
        return CSVDataProvider.getData("/datasheets/consigneeConsignor/VerifyAddConsigneeConsignor.csv");
    }

    @Test(groups = { "verify that consignee consignor can be edited", "VerifyEditConsigneeConsignor" }, dataProvider = "scenario")
    public void verifyEditConsigneeConsignor(String testScenario,String type) throws JSONException {

        String testName = "VerifyEditConsigneeConsignor";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.qe.keeboot.com/consignment";
       
        
        RestService restService = new RestService();
        EditConsigneeConsignor consigneeConsignor =  new EditConsigneeConsignor(getDriver());
        
        
        //get call to get all the ConsigneeConsignor details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String ccId=jObject.getJSONArray("response").getJSONObject(0).getString("ccId");
        String name=jObject.getJSONArray("response").getJSONObject(0).getString("name");
        String emailId = name +"@" + name +".com";
        
        String httpBody =consigneeConsignor.formatEditConsigneeConsignorRequest("", "", "", "", "", emailId, "", "", "", "", "", "", "", false, "", "", false, false, "");
         restResponse=restService.sendPutRequest(ApiUrl+"/"+ccId, HeaderType.JSON,httpBody);
        
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("0"), "consigneeConsignor added successfully");
        
        
    }
}
