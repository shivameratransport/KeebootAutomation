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

public class VerifyThatDuplicateFuelCardCantBeAdded extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyThatDuplicateFuelCardCantBeAdded() {
        return CSVDataProvider.getData("/datasheets/fuelcard/VerifyThatDuplicateFuelCardCantBeAdded.csv");
    }

    @Test(groups = { "VerifyThatDuplicateFuelCardCantBeAdded", "VerifyThatDuplicateFuelCardCantBeAdded" }, dataProvider = "scenario")
    public void verifyThatDuplicateFuelCardCantBeAdded(String testScenario) throws JSONException {

        String testName = "VerifyThatDuplicateFuelCardCantBeAdded";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/fuelcard";
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
	      

	     httpBody =fuelcard.formatFuelCardAddRequest(cardNumber+"", "paytm", "", "", vehicleNumber);
	     restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody );
	     Common.validateStatusCode(restResponse.getStatusCode(),200);
	        
	      
	      TestReporter.assertTrue(restResponse.getResponse().contains("errorResponse"), "Duplicate fuel cards can not be added");
        
        
    }
}
