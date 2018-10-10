package com.web.login;

import com.keeboot.web.WebBaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.keeboot.login.LoginPage;
import com.keeboot.utils.TestReporter;
import com.keeboot.utils.dataProviders.CSVDataProvider;


public class VerifyLoginBtnDisplayed extends WebBaseTest {
		@DataProvider(name = "dataScenario", parallel = true)
	    public Object[][] scenarios() {
	        return CSVDataProvider.getData("/datasheets/login/VerifyLoginBtnDisplayed.csv");
	    }

	    @Test(dataProvider = "dataScenario")
	    public void verifyLoginBtnDisplayed(String testScenario) {

	        // Start the test and setting the scenario name.
	        String testName = "VerifyLoginBtnDisplayed";
	        
	        TestReporter.logScenario(testScenario);
	        
	        testStart(testName);
	        
	        LoginPage loginPage = new LoginPage(getDriver());
	        
	        loginPage.verifyLoginBtn();
	        TestReporter.assertTrue( loginPage.verifyLoginBtn(), "Login button displayed");
	    }
	}

