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

public class VerifyAddInvoice extends BaseRestTest { 
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyAddInvoice() {
        return CSVDataProvider.getData("/datasheets/invoice/VerifyAddInvoice.csv");
    }

    @Test(groups = { "add invoice verification", "VerifyAddInvoice" }, dataProvider = "scenario")
    public void verifyAddInvoice(String testScenario) throws JSONException {

        String testName = "VerifyAddInvoice";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.staging.keeboot.com/invoice";
        RestService restService = new RestService();
        
        //get call to get all the customer details
        RestResponse restResponse=restService.sendGetRequest("https://api.staging.keeboot.com/customer/mini", HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        //capturing the customer id
        JSONObject jObject = new JSONObject(restResponse.getResponse());
        String customerId=jObject.getJSONArray("response").getJSONObject(0).getString("customerId");
        String customerName =jObject.getJSONArray("response").getJSONObject(0).getString("customerName");
        
        //to get the from address and to address and trip id associated with that customer
        restResponse=restService.sendGetRequest("https://api.staging.keeboot.com/trip/by-customer/"+customerId, HeaderType.JSON);
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        jObject = new JSONObject(restResponse.getResponse());
        String fromAddress = jObject.getJSONArray("response").getJSONObject(0).getJSONArray("customers").getJSONObject(0).getJSONObject("from").getString("address");
        String toAddress = jObject.getJSONArray("response").getJSONObject(0).getJSONArray("customers").getJSONObject(0).getJSONObject("to").getString("address");
       String tripId = jObject.getJSONArray("response").getJSONObject(0).getString("tripId");
       String valueForFreight = Randomness.randomNumber(2);
       String valueForLoading = Randomness.randomNumber(2);
       String valueForUnLoading = Randomness.randomNumber(2);
       String  valueForHalting = Randomness.randomNumber(1);
       
       AddInvoice invoice = new AddInvoice(getDriver());
       restResponse=restService.sendPostRequest(ApiUrl, HeaderType.JSON, invoice.formatInvoiceRequest(customerId, "SHIVA KUMAR K", tripId, valueForFreight, valueForLoading, valueForUnLoading, valueForHalting, fromAddress, toAddress, customerName, customerName, customerName, tripId));
       Common.validateStatusCode(restResponse.getStatusCode(),200);
       
       TestReporter.assertTrue(restResponse.getResponse().contains("successResponse"), "invoice created successfully");
    }
}
