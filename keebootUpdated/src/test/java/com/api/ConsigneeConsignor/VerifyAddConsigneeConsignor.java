package com.api.ConsigneeConsignor;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.ConsigneeConsignor.AddConsigneeConsignor;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.location.AddLocation;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyAddConsigneeConsignor extends BaseRestTest{
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyAddConsigneeConsignor() {
		
        return CSVDataProvider.getData("/datasheets/consigneeConsignor/VerifyAddConsigneeConsignor.csv");
    }

    @Test(groups = { "verify that consignee consignor can be added", "VerifyAddConsigneeConsignor" }, dataProvider = "scenario")
    public void verifyAddConsigneeConsignor(String testScenario,String type) throws JSONException {

        String testName = "VerifyAddConsigneeConsignor";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.qe.keeboot.com/consignment";
        String name =  Randomness.randomString(5);
        String spocName = Randomness.randomString(5);
        String typeId = "0B180BFEEA164B1F84B264D6084585F9";
        String mobileNumber = Randomness.randomNumber(10);
        
        RestService restService = new RestService();
        AddConsigneeConsignor consigneeConsignor =  new AddConsigneeConsignor(getDriver());
        
        String httpBody =consigneeConsignor.formatConsigneeConsignorRequest(name, type, spocName, mobileNumber, typeId);
        RestResponse restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON,httpBody);
        
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("ccId"), "consigneeConsignor added successfully");
        
        
    }
}
