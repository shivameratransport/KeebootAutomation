package com.api.indent;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.indent.AddIndent;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditIndent extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyEditIndent() {
        return CSVDataProvider.getData("/datasheets/indent/VerifyEditIndent.csv");
    }

    @Test(groups = { "indent edit verification", "VerifyEditIndent" }, dataProvider = "scenario")
    public void verifyEditIndent(String testScenario) throws JSONException {

        String testName = "VerifyEditIndent";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/indent";
        RestService restService = new RestService();
        
        //get call to get all the customer details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl+"/mini", HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the customer id to edit
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String indentId=jObject.getJSONArray("response").getJSONObject(0).getString("indentId");
        
        AddIndent indent = new AddIndent(getDriver());
       String customerName = Randomness.randomString(5);
        
         restResponse=restService.sendPutRequest(ApiUrl+"/"+indentId, HeaderType.JSON,indent.formatIndentAddRequest(customerName, "", "", "", "", "", 0, "", "", "", "", "", 0.0, 0.0, true, "", "", ""));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("indentId"), "indent details edited and updated successfully");
    }
}
