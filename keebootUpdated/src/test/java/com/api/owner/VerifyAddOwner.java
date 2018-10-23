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
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddOwner extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] VerifyAddOwnerInApi() {
        return CSVDataProvider.getData("/datasheets/owner/VerifyAddOwner.csv");
    }

    @Test(groups = { "login verification", "VerifyAddOwner" }, dataProvider = "scenario")
    public void VerifyAddOwnerInApi(String testScenario) throws JSONException {

        String testName = "VerifyAddOwner";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/owner";
        RestService restService = new RestService();
        
        AddOwner owner = new AddOwner(getDriver());
        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,owner.formatOwnerRequest("", "", "", "", ""));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jobject = new JSONObject(restResponse.getResponse());
     String ownerId = jobject.getString("ownerId");
     
     TestReporter.assertTrue((ownerId!=""), "New Owner added successfully");
        
    }
}
