package com.keeboot.utils;

import java.io.File;

import com.keeboot.BaseTest;

public class Constants {

    /**
     * File system info
     */
    public final static String DIR_SEPARATOR = File.separator;
    public final static String CURRENT_DIR = determineCurrentPath();

    /**
     * Selenium Constants
     */
    public final static String DRIVERS_PATH_LOCAL = "/drivers/";
    public final static String DRIVERS_PATH_REMOTE = "C:\\Selenium\\WebDrivers\\";
    public final static String SCREENSHOT_FOLDER_GRID = DIR_SEPARATOR + DIR_SEPARATOR + "vugenstor" + DIR_SEPARATOR + "scripts" + DIR_SEPARATOR + "igate" +
            DIR_SEPARATOR + "2013" + DIR_SEPARATOR + "Automation" + DIR_SEPARATOR + "selenium-reports" + DIR_SEPARATOR + "html" + DIR_SEPARATOR + "screenshots";
    public final static String SCREENSHOT_FOLDER_CLOUD = DIR_SEPARATOR + DIR_SEPARATOR + "10.191.112.115" + DIR_SEPARATOR + "grid" + DIR_SEPARATOR + "selenium-reports" + DIR_SEPARATOR + "html" + DIR_SEPARATOR + "screenshots";
    public final static int DEFAULT_GLOBAL_DRIVER_TIMEOUT = 10; // changed timeout from 10 to 60
    public final static int ELEMENT_TIMEOUT = 10;// changed timeout from 10 to 80
    public final static int PAGE_TIMEOUT = 20;// changed timeout from 20 to 100
    public final static int MILLISECONDS_TO_POLL_FOR_ELEMENT = 250;
    public final static int SYNC_TIME_BETWEEN_APPS = 300000;
    public final static int SYNC_TIME_POLLING = 10000;

    /**
     * Test constants
     */
    public final static String ENVIRONMENT_URL_PATH = "EnvironmentURLs";
    public final static String USER_CREDENTIALS_PATH = "UserCredentials";
    public final static String SANDBOX_PATH = "/sandbox/";
    public final static String TNSNAMES_PATH = "/database/";

    /**
     * API Test constants
     */
    public final static String PHOENIX_SERVICE_VERSION = "v1";
    public final static String DMP_SERVICE_VERSION = "v1";
    public final static String DMP_SERVICE_LOCALE = "en";
    public final static String EVA_KEY_QA = "3985dc20-081c-4573-8225-5ffc31a38f2b";
    public final static String OKTA_USERNAME = "gdeep"; // Added below for ICE API login calls.
    /**
     * API Mirage Resort Fees and Taxes
     */

    // FIXME These are not constants. let's discuss where to keep this
    public final static Double MIRAGE_RESORT_FEE = 100.00;
    public final static Double MIRAGE_TAX_PERCENTAGE = 13.38;
    public final static Double MIRAGE_RESORT_FEE_TAX = 13.38;

    /**
     * Defaults to "./" if there's an exception of any sort.
     *
     * @warning Exceptions are swallowed.
     * @return Constants.DIR_SEPARATOR
     */
    final private static String determineCurrentPath() {
        try {
            return (new File(".").getCanonicalPath()) + Constants.DIR_SEPARATOR;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "." + Constants.DIR_SEPARATOR;
    }

    static BaseTest basetest = new BaseTest();
    static String runlocation = basetest.getRunLocation();

    /**
     * determine screenshot path
     *
     * @return screenshot path
     */
    public static String determineScreenShotPath() {
        if (runlocation.equalsIgnoreCase("grid")) {
            return SCREENSHOT_FOLDER_GRID;
        } else {
            return SCREENSHOT_FOLDER_CLOUD;
        }
    }
}