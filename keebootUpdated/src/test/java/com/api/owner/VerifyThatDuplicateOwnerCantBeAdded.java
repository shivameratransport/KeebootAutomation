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

public class VerifyThatDuplicateOwnerCantBeAdded extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] VerifyThatDuplicateOwnerCantBeAddedInApi() {
        return CSVDataProvider.getData("/datasheets/owner/VerifyAddOwner.csv");
    }

    @Test(groups = { "login verification", "VerifyThatDuplicateOwnerCantBeAdded" }, dataProvider = "scenario")
    public void verifyThatDuplicateOwnerCantBeAdded(String testScenario) throws JSONException {

        String testName = "VerifyThatDuplicateOwnerCantBeAdded";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/owner";
        RestService restService = new RestService();
       int mobileNum =  Randomness.randomNumberBetween(111111111,999999999);
      String ownerName =  Randomness.randomString(5);
       
      //adding new owner
        AddOwner owner = new AddOwner(getDriver());
        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,owner.formatOwnerRequest(ownerName,"9"+mobileNum+"", "", "", ""));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        JSONObject jobject = new JSONObject(restResponse.getResponse());
     String ownerId = jobject.getString("ownerId");
     
     TestReporter.assertTrue((ownerId!=""), "New Owner added successfully");
     
     //adding the same user again to verify the dupliacte error msg
     restService.sendPostRequest(ApiUrl, HeaderType.JSON,owner.formatOwnerRequest(ownerName,"9"+mobileNum+"", "", "", ""));
     Common.validateStatusCode(restResponse.getStatusCode(),200);
     
     TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "Duplicate owners are not allowed");
    }
}
