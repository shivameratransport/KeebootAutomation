package com.keeboot.api.restServices;

import static com.keeboot.api.restServices.ResponseCodes.OK;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.keeboot.utils.TestReporter;
import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebBaseTest;

public class BaseRestTest extends WebBaseTest {

    protected void validateResponse(RestResponse response) {
        validateResponse(response, OK, null);
    }

    protected void validateResponse(RestResponse response, int expectedErrorCode) {
        validateResponse(response, expectedErrorCode, null);
    }

    protected void validateResponse(RestResponse response, int expectedErrorCode, String expectedErrorMessage) {
        TestReporter.logAPI(response.getStatusCode() != expectedErrorCode, "Validate status code returned [ " + response.getStatusCode() + " ] was the expected value of [ " + expectedErrorCode + " ]", response);
        TestReporter.assertTrue(true, "Validate status code returned [ " + response.getStatusCode() + " ] was the expected value of [ " + expectedErrorCode + " ] ");
        if (expectedErrorMessage != null) {
            TestReporter.assertTrue(response.getResponse().contains(expectedErrorMessage), "Returned response [ " + response.getResponse() + " ] matches expected response [ " + expectedErrorMessage + " ]");
        }
    }

    @Override
    public KeebootDriver testStart(String testName) {
        TestReporter.setPrintToConsole(true);
        setTestName(testName);
        setDriver(new KeebootDriver());
        return getDriver();
    }

    @Override
    public String getRestURI() {
        String URI = appURLRepository.getString(getApplicationUnderTest().toUpperCase() + "_" + getEnvironment().toUpperCase());
        return URI;
    }

    @Override
    public String getRestURI(String appUnderTest) {
        String URI = appURLRepository.getString(appUnderTest.toUpperCase() + "_" + getEnvironment().toUpperCase());
        return URI;
    }

    /**
     * Added to return all the Cookies for My Vegas or any other URL
     *
     * @param Url
     * @return list of Cookies (HttpCookie)
     *
     **/
    public List<HttpCookie> getBrowserCookies(String Url) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

        // creates url for the given string
        URL url = null;
        try {
            url = new URL(Url);

            // open's a connection with the url specified and returns URLConnection object
            URLConnection urlConnection = url.openConnection();
            // get's the contents from this url specifies
            urlConnection.getContent();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // returns the cookie store(bunch of cookies)
        CookieStore cookieStore = cookieManager.getCookieStore();

        // getting cookies which returns in the form of List of type HttpCookie
        List<HttpCookie> listOfcookies = cookieStore.getCookies();

        for (HttpCookie httpCookie : listOfcookies) {

            System.out.println("Cookie Name : " + httpCookie.getName() + " Cookie Value : " + httpCookie.getValue());
        }
        return listOfcookies;
    }
}