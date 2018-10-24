package com.api.profile;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.Common;
import com.keeboot.api.restServices.BaseRestTest;
import com.keeboot.api.restServices.RestResponse;
import com.keeboot.api.restServices.RestService;
import com.keeboot.profile.ProfileEmployee;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;

public class VerifyThatProfileDetailsCanBeUpdated  extends BaseRestTest{
	
	@DataProvider(name = "scenario", parallel = true)

    public Object[][] verifyUserLoggedin() {
        return CSVDataProvider.getData("/datasheets/profile/verifyThatProfileDetailsCanBeUpdated.csv");
    }

    @Test(groups = { "login verification", "verifyUserLogin" }, dataProvider = "scenario")
    public void verifyThatProfileDetailsCanBeUpdatedinapi(String testScenario, String lastName) throws JSONException {

        String testName = "verifyUserLogin";

        TestReporter.logScenario(testScenario);
        testStart(testName);
        
        String ApiUrl = "https://api.keeboot.com/profile/employee";
        RestService restService = new RestService();
        ProfileEmployee profile = new ProfileEmployee(getDriver());
        RestResponse restResponse=restService.sendPutRequest(ApiUrl, HeaderType.JSON, profile.formatProfileUpdateRequest("+919035434312", "shiva", lastName, "123", "BTHPD1683B", "shivakumar@meratransport.com", "123"));
        Common.validateStatusCode(restResponse.getStatusCode(),200);
        
        
        TestReporter.assertTrue(restResponse.getResponse().contains("Profile details updated successfully"), "Profile details can be edited and updated");
        
        
    }
}
