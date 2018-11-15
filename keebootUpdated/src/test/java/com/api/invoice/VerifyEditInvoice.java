package com.api.invoice;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.invoice.AddInvoice;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.Randomness;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyEditInvoice extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)
    public Object[][] verifyEditInvoice() {
        return CSVDataProvider.getData("/datasheets/invoice/VerifyEditInvoice.csv");
    }

    @Test(groups = { "verify that invoice can be edited", "VerifyEditInvoice" }, dataProvider = "scenario")
    public void verifyEditInvoice(String testScenario) throws JSONException {

        String testName = "VerifyEditInvoice";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/invoice";
        RestService restService = new RestService();
        
        //get call to get all the customer details
        RestResponse restResponse=restService.sendGetRequest(ApiUrl+"/mini", HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the customer id to edit
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String invoiceId=jObject.getJSONArray("response").getJSONObject(1).getString("invoiceId");
        
        
        //get call to get all the customer details
         restResponse=restService.sendGetRequest(ApiUrl+"/"+invoiceId, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
         jObject = new JSONObject(restResponse.getResponse());
        String  customerId=jObject.getJSONObject("response").getString("customerId");
        String  customerName=jObject.getJSONObject("response").getString("customerName");
        String tripNumber=jObject.getJSONObject("response").getString("tripNumber");
        String valueForFreight = Randomness.randomNumber(2);
        String valueForLoading = Randomness.randomNumber(2);
        String valueForUnLoading = Randomness.randomNumber(2);
        String  valueForHalting = Randomness.randomNumber(1);
        String  fromAddress=jObject.getJSONObject("response").getString("fromAddress");
        String  toAddress=jObject.getJSONObject("response").getString("toAddress");
        
        AddInvoice invoice = new AddInvoice(getDriver());
     restResponse=restService.sendPutRequest(ApiUrl+"/"+invoiceId, HeaderType.JSON, invoice.formatInvoiceRequest(customerId, "SHIVA KUMAR K", tripNumber, valueForFreight, valueForLoading, valueForUnLoading, valueForHalting, fromAddress, toAddress, customerName, customerName, customerName, tripNumber));
       Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        TestReporter.assertTrue(restResponse.getResponse().contains("successResponse"), "invoice details edited and updated successfully");
    }
}
