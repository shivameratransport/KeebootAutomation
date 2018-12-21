package com.api.fuelcard;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.fuelcard.AddFuelCard;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddFuelCard extends BaseRestTest{
	 @DataProvider(name = "scenario", parallel = true)

	    public Object[][] verifyAddFuelCard() {
	        return CSVDataProvider.getData("/datasheets/fuelcard/VerifyAddFuelCard.csv");
	    }

	    @Test(groups = { "login verification", "verifyUserLogin" }, dataProvider = "scenario")
	    public void verifyAddFuelCard(String testScenario) throws JSONException {

	        String testName = "VerifyAddFuelCard";

	        TestReporter.logScenario(testScenario);
	        testStart(testName);
	        
	        String ApiUrl = "https://api.qe.keeboot.com/fuelcard";
	        
	        int cardNumber =  Randomness.randomNumberBetween(10000,99999);
	        String  vehicleNumber =   Randomness.randomNumber(5);
	        
	        RestService restService = new RestService();
	        
	        AddFuelCard fuelcard = new AddFuelCard(getDriver());
	        
	        String httpBody =fuelcard.formatFuelCardAddRequest(cardNumber+"", "paytm", "", "", vehicleNumber);
	        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody );
	        Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	        JSONObject jobject = new JSONObject(restResponse.getResponse());
	      int serialNo=jobject.getJSONObject("response").getInt("slNo");
	   
	      
	      TestReporter.assertTrue(serialNo!=0, "A new fuel card has been added");
	    }
}
