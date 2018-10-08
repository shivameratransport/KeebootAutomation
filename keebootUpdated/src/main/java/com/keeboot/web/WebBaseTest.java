package com.keeboot.web;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.keeboot.AutomationException;
import com.keeboot.BaseTest;
import com.keeboot.utils.Base64Coder;
import com.keeboot.utils.Constants;
import com.keeboot.utils.TestReporter;
import com.keeboot.web.debugging.Highlight;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.saucerest.SauceREST;

/**
 *
 * @author Justin Phlegar & Waightstill W Avery
 * @summary This class is designed to be extended by page classes and
 *          implemented by test classes. It houses test environment data and
 *          associated getters and setters, setup for both local and remote
 *          WebDrivers as well as page class methods used to sync page behavior.
 *          The need for this arose due to the parallel behavior that indicated
 *          that WebDriver instances were crossing threads and testing on the
 *          wrong os/browser configurations
 * @date April 5, 2015
 *
 */
public class WebBaseTest extends BaseTest {
    /*
     * Test Environment Fields
     */
    protected String applicationUnderTest = "";
    protected String browserUnderTest = "";
    protected String browserVersion = "";
    protected String operatingSystem = "";
    protected String pageUrl = "";

    /*
     * WebDriver Fields
     */
    private KeebootDriver driver;
    private ThreadLocal<KeebootDriver> threadedDriver = new ThreadLocal<>();
    private boolean setThreadDriver = true;
    protected ThreadLocal<String> sessionId = new ThreadLocal<>();

    /*
     * URL and Credential Repository Field
     */
    protected ResourceBundle appURLRepository = ResourceBundle.getBundle(Constants.ENVIRONMENT_URL_PATH);
    /*
     * Selenium Grid Hub Field
     */
  protected String defaultUrl = appURLRepository.getString("defaultUrl");
  


   
    public WebBaseTest() {
    };

    /**
     * General constructor for setting up driver for remote or local execution
     *
     * @param application
     * @param browserUnderTest
     * @param browserVersion
     * @param operatingSystem
     * @param runLocation
     * @param environment
     */
    public WebBaseTest(String application, String browserUnderTest, String browserVersion, String operatingSystem,
            String runLocation, String environment) {
        TestReporter.log(String.format(
                "Initializing test... %n Application: '%s'%n Browser: '%s'%n Browser Version: '%s'%n OS: '%s'%n Testing Environment: '%s'",
                application,
                browserUnderTest,
                browserVersion,
                operatingSystem,
                environment));

        this.applicationUnderTest = application;
        setEnvironment(environment);
        // Use setter methods for these fields as the data may be coming from a jenkins parameter
        setBrowserUnderTest(browserUnderTest);
        setBrowserVersion(browserVersion);
        setOperatingSystem(operatingSystem);
        setRunLocation(runLocation);
    }

    /*
     * Getters and setters
     */
    public void setApplicationUnderTest(String aut) {
        applicationUnderTest = aut;
    }

    public String getApplicationUnderTest() {
        return applicationUnderTest;
    }

    public void setPageURL(String url) {
        pageUrl = url;
    }

    public String getPageURL() {
        return pageUrl;
    }

    public String getRestURI() {
        String URI = appURLRepository.getString(getApplicationUnderTest().toUpperCase() + "_" + getEnvironment().toUpperCase());
        return URI;
    }

    public String getRestURI(String appUnderTest) {
        String URI = appURLRepository.getString(appUnderTest.toUpperCase() + "_" + getEnvironment().toUpperCase());
        return URI;
    }

    public void setBrowserUnderTest(String but) {
        if (but.equalsIgnoreCase("jenkinsParameter")) {
            browserUnderTest = System.getProperty("jenkinsBrowser").trim();
        } else {
            browserUnderTest = but;
        }
    }

    public String getBrowserUnderTest() {
        return browserUnderTest;
    }

    public void setBrowserVersion(String bv) {
        if (bv.equalsIgnoreCase("jenkinsParameter")) {
            if (System.getProperty("jenkinsBrowserVersion") == null
                    || System.getProperty("jenkinsBrowserVersion") == "null") {
                browserVersion = "";
            } else {
                browserVersion = System.getProperty("jenkinsBrowserVersion").trim();
            }
        } else {
            browserVersion = bv;
        }
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setOperatingSystem(String os) {
        if (os.equalsIgnoreCase("jenkinsParameter")) {
            operatingSystem = System.getProperty("jenkinsOperatingSystem").trim();
        } else {
            operatingSystem = os;
        }
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getRemoteURL() {
      if (getRunLocation().equalsIgnoreCase("grid")) {
           return defaultUrl;
        } else if (getRunLocation().equalsIgnoreCase("gridoncloud")) {
        return defaultUrl;
        } else if (getRunLocation().equalsIgnoreCase("mobile")) {
          return "";
        } else {
            return "";
        }
    }

    protected void setSeleniumHubURL(String newHubURLName) {
    	defaultUrl = appURLRepository.getString(newHubURLName);
    }

  
    // ************************************
    // ************************************
    // ************************************
    // WEBDRIVER SETUP
    // ************************************
    // ************************************
    // ************************************

    /**
     * Doubling up to cover different threading between before test and before method
     *
     * @param browserUnderTest
     * @param browserVersion
     * @param operatingSystem
     */
    @Parameters({ "browserUnderTest", "browserVersion", "operatingSystem", "application" })
    @BeforeTest()
    public void beforeWebTest(String browserUnderTest, String browserVersion, String operatingSystem, String appUnderTest) {
        setBrowserUnderTest(browserUnderTest);
        setBrowserVersion(browserVersion);
        setOperatingSystem(operatingSystem);
        setApplicationUnderTest(appUnderTest);
        Highlight.setDebugMode(true);
    }

    /**
     * Doubling up to cover different threading between before test and before method
     *
     * @param browserUnderTest
     * @param browserVersion
     * @param operatingSystem
     */
    @Parameters({ "browserUnderTest", "browserVersion", "operatingSystem", "application" })
    @BeforeMethod(alwaysRun = true)
    public void beforeWebMethod(String browserUnderTest, String browserVersion, String operatingSystem, String appUnderTest) {
        setBrowserUnderTest(browserUnderTest);
        setBrowserVersion(browserVersion);
        setOperatingSystem(operatingSystem);
        setApplicationUnderTest(appUnderTest);
    }

    /*
     * Getter and setter for the actual WebDriver
     */
    protected void setDriver(KeebootDriver driverSession) {
        if (isThreadedDriver()) {
            threadedDriver.set(driverSession);
        } else {
            driver = driverSession;
        }
    }

    public KeebootDriver getDriver() {
        if (isThreadedDriver()) {
            return threadedDriver.get();
        } else {
            return driver;
        }
    }

    /**
     * User controls to see the driver to be threaded or not. Only use when
     * using data provider threading
     */
    private boolean isThreadedDriver() {
        return setThreadDriver;
    }

    public void setThreadDriver(boolean setThreadDriver) {
        this.setThreadDriver = setThreadDriver;
    }

    /**
     * Method to retrive the URL and Credential Repository
     */
    protected ResourceBundle getEnvironmentURLRepository() {
        return appURLRepository;
    }

    /**
     * Launches the application under test using a URL passed into method
     *
     * @version 12/16/2014
     * @author Justin Phlegar
     * @return Nothing
     */
    private void launchApplication(String URL) {
        getDriver().get(URL);
    }

    /**
     * Launches the application under test using the URL grabbed from the EnvironmentURLs properties file
     * It will look for a key in the properties file with the
     * prefix of the application under test + "_" + the environment being tested.
     *
     * @version 12/16/2014
     * @author Justin Phlegar
     * @return Nothing
     */
    private void launchApplication() {
        launchApplication(appURLRepository
                .getString(getApplicationUnderTest().toUpperCase() + "_" + getEnvironment().toUpperCase()));
    }

    /**
     * Initializes the webdriver, sets up the run location, driver type,
     * launches the application. Gives the option of using the EnvironmentURL properties
     * file to launch the URL of the application, or you can set the page URL during setup by calling
     * setPageURL("http://urlforthepage.com"). Unless you are wanting the test to start from a specific
     * page in the application under test, you will not set that field & will instead just use the base
     * URL from the properties file
     *
     * @version 12/16/2014
     * @author Jessica Marshall
     */
    public KeebootDriver testStart(String testName) {
        // Uncomment the following line to have TestReporter outputs output to
        // the console
        TestReporter.setPrintToConsole(true);
        setTestName(testName);
        driverSetup();
        // launch the application under test normally
        if (pageUrl.isEmpty()) {
            launchApplication();
            // Otherwise if you have a specific page you want the test to start from
        } else {
            launchApplication(pageUrl);
        }
        return getDriver();
    }

    /**
     * Ends the test and grabs the test result (pass/fail) in case need to use that
     * for additional reporting - such as to a sauce labs run. Allows for different
     * ways of quiting the browser depending on r
     * Use ITestResult from @AfterMethod to determine run status before closing
     * test if reporting to sauce labs
     */
    protected void endTest(String testName, ITestResult testResults) {
        // Sauce labs specific to end test
        if (getRunLocation().equalsIgnoreCase("sauce")) {
            reportToSauceLabs(testResults.getStatus());
        }
        // quit driver
        // && getBrowserUnderTest() != null
        if (!getBrowserUnderTest().equalsIgnoreCase("api")) {
            if (getDriver() != null && isNotEmpty(getDriver().getWindowHandles()) && !getDriver().toString().contains("null")) {
                getDriver().quit();
            }
        }
    }

    /**
     * Ends the test and grabs the test result (pass/fail) in case need to use that
     * for additional reporting - such as to a sauce labs run. Allows for different
     * ways of quiting the browser depending on run status
     * Use ITestContext from @AfterTest or @AfterSuite to determine run status
     * before closing test if reporting to sauce labs
     */
    protected void endTest(String testName, ITestContext testResults) {
        if (getRunLocation().equalsIgnoreCase("sauce")) {
            if (testResults.getFailedTests().size() == 0) {
                reportToSauceLabs(ITestResult.SUCCESS);
            } else {
                reportToSauceLabs(ITestResult.FAILURE);
            }
        }
        // quit driver
        if (getDriver() != null && getDriver().getWindowHandles().size() > 0) {
            if (!getDriver().toString().contains("null")) {
                getDriver().quit();
            }
        }
    }

    /**
     * Ends the test for a sauce labs run by passing in the test results (pass/fail)
     * and quits
     *
     * @param result
     */
    private void reportToSauceLabs(int result) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", getTestName());

        if (result == ITestResult.FAILURE) {
            updates.put("passed", false);
        } else {
            updates.put("passed", true);
        }
       // SauceREST client = new SauceREST(authentication.getUsername(), authentication.getAccessKey());
      //  client.updateJobInfo(driver.getSessionId(), updates);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
        endTest(getTestName(), testResults);
    }

    /**
     * Sets up the driver type, location, browser under test, os
     *
     * @param None
     * @version 12/16/2014
     * @author Justin Phlegar
     * @return Nothing
     * @throws IOException
     * @throws InterruptedException
     */
    private void driverSetup() {
        // local execution
        if (getRunLocation().equalsIgnoreCase("local")) {
            localDriverSetup();

            // Code for running on remote execution such as a selenium grid or saucelabs
        } else if (getRunLocation().equalsIgnoreCase("grid") || getRunLocation().equalsIgnoreCase("sauce") || getRunLocation().equalsIgnoreCase("gridoncloud")) {
            remoteDriverSetup();
        }
        // Code for running on mobile devices
        else if (getRunLocation().equalsIgnoreCase("mobile")) {
           // mobileDriverSetup();
        } else {
            throw new AutomationException(
                    "Parameter for run [Location] was not set to 'Local', 'Grid', 'GridOnCloud' , 'Sauce', 'Mobile'");
        }

        // Set the timeouts to the defaults according to the constants class
        getDriver().setElementTimeout(Constants.ELEMENT_TIMEOUT);
        getDriver().setPageTimeout(Constants.PAGE_TIMEOUT);
        getDriver().setScriptTimeout(Constants.DEFAULT_GLOBAL_DRIVER_TIMEOUT);

        // Microsoft Edge Browser
        if (!browserUnderTest.toLowerCase().contains("edge") && !getRunLocation().toLowerCase().contains("mobile")) {
            getDriver().manage().deleteAllCookies();
            getDriver().manage().window().maximize();
        }
    }

    /**
     * Creates a local web driver instance based on browser, browser version (required only for firefox).
     * It uses driver servers for each browser that are stored within the project.
     * For firefox versions greater than 46, you will need to use the marionette/gecko driver.
     *
     * @author jessica.marshall
     * @date 9/13/2016
     */
    private void localDriverSetup() {

        File file = null;
        DesiredCapabilities caps = new DesiredCapabilities();

        switch (browserUnderTest.toLowerCase().trim()) {
            case "firefox":
                caps = DesiredCapabilities.firefox();
                file = new File(
                        this.getClass()
                                .getResource(Constants.DRIVERS_PATH_LOCAL + "geckodriver.exe").getPath());
                System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
                break;

            case "ie":
            case "internet explorer":
            case "iexplore":
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability("ignoreZoomSetting", true);
                caps.setCapability("enablePersistentHover", false);
                file = new File(
                        this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "IEDriverServer.exe").getPath());
                System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                break;

            case "microsoftedge":
                caps = DesiredCapabilities.edge();
                file = new File(this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "MicrosoftWebDriver.exe")
                        .getPath());
                System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
                break;
            case "chrome":
                caps = DesiredCapabilities.chrome();
                file = new File(
                        this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "chromedriver.exe").getPath());
                System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                // Mac operating system with chrome browser
                if (operatingSystem.equalsIgnoreCase("mac")) {
                    file = new File(
                            this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "mac/chromedriver").getPath());
                    System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                    try {
                        // Ensure the permission on the driver include
                        // executable permissions
                        Process proc = Runtime.getRuntime()
                                .exec(new String[] { "/bin/bash", "-c", "chmod 777 " + file.getAbsolutePath() });
                        proc.waitFor();

                    } catch (IllegalStateException | IOException | InterruptedException ise) {
                        throw new IllegalStateException(
                                "This has been seen to occur when the chromedriver file does not have executable permissions. In a terminal, navigate to the directory to which Maven pulls the drivers at runtime (e.g \"/target/classes/drivers/\") and execute the following command: chmod +rx chromedriver", ise);
                    }
                }

                break;
            case "html":
                caps = DesiredCapabilities.htmlUnit();
                break;
            case "safari":
                caps = DesiredCapabilities.safari();
                break;
            default:
                throw new AutomationException("Parameter not set for browser type");
        }

        setDriver(new KeebootDriver(caps));
    }

    /**
     * Creates the remote webdriver instance based on browser, browser version
     * OS, and the remote grid URL
     *
     * @author jessica.marshall
     * @date 9/13/2016
     */
    private void remoteDriverSetup() {
        // Capabilities for the remote web driver
        DesiredCapabilities caps = new DesiredCapabilities();
        // Browser
        caps.setCapability(CapabilityType.BROWSER_NAME, browserUnderTest);
        // Browser version
        if (!browserVersion.isEmpty()) {
            caps.setCapability(CapabilityType.VERSION, browserVersion);
        }
        // gecko/firefox
        if (browserUnderTest.equalsIgnoreCase("firefox")) {

            // Marionette/gecko capability
            if (browserVersion.isEmpty() || Integer.parseInt(browserVersion) > 47) {
                caps.setCapability("marionette", true);
            } else {
                caps.setCapability("marionette", false);
            }

        }

        // safari
        if (browserUnderTest.equalsIgnoreCase("safari")) {
            SafariOptions options = new SafariOptions();
            options.setUseCleanSession(true);
            caps.setCapability(SafariOptions.CAPABILITY, options);
        }

        // Operating System
        caps.setCapability(CapabilityType.PLATFORM, Platform.fromString(operatingSystem));

        // IE specific capabilities
        if (browserUnderTest.toLowerCase().contains("ie")
                || browserUnderTest.toLowerCase().contains("iexplore") || browserUnderTest.equalsIgnoreCase("internet explorer")) {
            caps.setCapability("ignoreZoomSetting", true);
        }
        caps.setCapability("name", getTestName());
        // Create the remote web driver
        try {
            setDriver(new KeebootDriver(caps, new URL(getRemoteURL())));
        } catch (MalformedURLException e) {
            throw new AutomationException("Problem with creatting the remote web driver: ", e);

        }

        // allows for local files to be uploaded via remote webdriver on grid machines
        getDriver().setFileDetector();
    }

    /**
     * Sets up the driver with capabilities for mobile devices. Uses a remote mobile hub URL
     * Gives user option to either specify the device to test on using deviceID or to give
     * parameters for auto selection of device. If deviceID is null, then will do auto selection using
     * these parameters:
     * operatingSystem -- mobile OS platform, e.g. iOS, Android
     * mobileOSVersion -- Mobile OS version, e.g. 7.1, 4.4
     * browserUnderTest -- Name of mobile web browser to automate. Should be an empty string if automating an app instead
     * mobileAppPath -- The absolute local path or remote http URL to an .ipa or .apk file, or a .zip containing one of these.
     * Leave browserUnderTest blank/null if using this
     *
     * @date 9/28/2016
     * @author jessica.marshall
     */
//    private void mobileDriverSetup() {
//        DesiredCapabilities caps = new DesiredCapabilities();
//        // if a device ID is specified, go to that device
//        if (deviceID.isEmpty()) {
//            // Which mobile OS platform to use, e.g. iOS, Android
//            caps.setCapability("platformName", operatingSystem);
//            // Mobile OS version, e.g. 7.1, 4.4
//            caps.setCapability("platformVersion", mobileOSVersion);
//            // Name of mobile web browser to automate. Should be an empty string if automating an app instead
//            caps.setCapability("browserName", browserUnderTest);
//            // The absolute local path or remote http URL to an .ipa or .apk file, or a .zip containing one of these.
//            // leave browserUnderTest blank/null if using this
//            caps.setCapability("app", mobileAppPath);
//        } else {
//            caps.setCapability(CapabilityType.PLATFORM, Platform.ANY);
//            caps.setCapability("deviceName", deviceID);
//        }
//
//        try {
//            setDriver(new KeebootDriver(caps, new URL(getRemoteURL())));
//        } catch (MalformedURLException e) {
//            throw new AutomationException("Could not generate the moblile remote driver", e);
//        }
//    }

    /**
     * Used to get the Platform used by Selenium
     *
     * @param os
     * @return
     */
    /*
     * private Platform getGridPlatformByOS(String os) {
     * Platform.fromString(os);
     * switch (os.toLowerCase()) {
     * case "android":
     * return Platform.ANDROID;
     * case "windows":
     * return Platform.WINDOWS;
     * case "win7":
     * case "windows 7":
     * return Platform.VISTA;
     * case "windows 8":
     * case "win8":
     * return Platform.WIN8;
     * case "windows 8.1":
     * case "win8.1":
     * return Platform.WIN8_1;
     * case "win10":
     * case "windows 10":
     * return Platform.WIN10;
     * case "xp":
     * return Platform.XP;
     * case "linux":
     * return Platform.LINUX;
     * case "mac":
     * return Platform.MAC;
     * case "el capitan":
     * case "el_capitan":
     * return Platform.EL_CAPITAN;
     * case "mavericks":
     * return Platform.MAVERICKS;
     * case "mountain lion":
     * case "mountain_lion":
     * return Platform.MOUNTAIN_LION;
     * case "sierra":
     * return Platform.SIERRA;
     * case "snow leopard":
     * case "snow_leopard":
     * return Platform.SNOW_LEOPARD;
     * case "yosemite":
     * return Platform.YOSEMITE;
     * default:
     * throw new AutomationException("OS is not in supported list of platforms: " + os);
     * }
     * }
     */

    // @AfterSuite(alwaysRun = true)
    // public void afterSuite() {
    // // SendReport emailTrigger = new SendReport();
    // // emailTrigger.sendMail();

    /**
     * Get EVA Key based on Environment parameter set in XML
     *
     * @author Chidanand RG
     * @date 08/13/2018
     */

    public String getEvaEnvironmentKey() {
        String var = "EVA_KEY_" + getEnvironment().toUpperCase();
        Constants con = new Constants();
        try {
            return (String) con.getClass().getDeclaredField(var).get(con);
        } catch (Exception e) {
            TestReporter.log("KEY: " + var + " cannot be found in the Constants");
            return "";
        }
    }
}