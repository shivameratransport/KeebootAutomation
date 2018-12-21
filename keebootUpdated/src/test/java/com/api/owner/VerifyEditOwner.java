package com.api.owner;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.owner.AddOwner;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditOwner extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] VerifyEditOwnerInApi() {
        return CSVDataProvider.getData("/datasheets/owner/VerifyEditOwner.csv");
    }

    @Test(groups = { "login verification", "VerifyEditOwner" }, dataProvider = "scenario")
    public void VerifyEditOwnerInApi(String testScenario) throws JSONException {

        String testName = "VerifyEditOwner";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.qe.keeboot.com/owner";
        RestService restService = new RestService();
        
        //get call to get all the owner details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the owner id to edit
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String ownerId=jObject.getJSONArray("response").getJSONObject(0).getString("ownerId");
        
        //editing and updating the address of a particular owner
        AddOwner owner = new AddOwner(getDriver());
        String address =  Randomness.randomString(5);
        restResponse=restService.sendPutRequest(ApiUrl+"/"+ownerId, HeaderType.JSON,owner.formatOwnerRequest("","", "", address, ""));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "Owner details edited and updated successfully");
    }
}
