package com.api.fuelcard;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.fuelcard.AddFuelCard;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditFuelCard extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditFuelCard() {
        return CSVDataProvider.getData("/datasheets/fuelcard/VerifyEditFuelCard.csv");
    }

    @Test(groups = { "VerifyEditFuelCard", "VerifyEditFuelCard" }, dataProvider = "scenario")
    public void verifyEditFuelCard(String testScenario) throws JSONException {

        String testName = "VerifyEditFuelCard";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/fuelcard";
        RestService restService = new RestService();
        
        //get call to get all the fuel card details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the fuel card id to edit
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String fuelcardId=jObject.getJSONArray("response").getJSONObject(0).getString("fuelcardId");
        
      //editing and updating the address of a particular owner
        AddFuelCard fuelCard = new AddFuelCard(getDriver());
        String cardlimit = Randomness.randomNumber(5);
        
        restResponse=restService.sendPutRequest(ApiUrl+"/"+fuelcardId, HeaderType.JSON,fuelCard.formatFuelCardAddRequest("", "", cardlimit, "", ""));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("statusCode"), "fuel card details edited and updated successfully");
    }
}
