package com.keeboot;

import static com.keeboot.utils.TestReporter.log;
import static com.keeboot.utils.TestReporter.logTrace;
import static org.apache.commons.lang3.BooleanUtils.toBooleanObject;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.keeboot.utils.TestReporter;

public class BaseTest {
    private static String environment;
    private static String runLocation;
    private String testName;
    private int logLevel;
    private boolean reportToMustard = false;
    private static String application;

    @BeforeSuite(alwaysRun = true)
    @Parameters({ "environment", "runLocation", "logLevel", "reportToMustard", "application" })
    public void beforeSuite(@Optional String environment, @Optional String runLocation, @Optional String logLevel, @Optional String reportToMustard, @Optional String application) {

        if (isNotEmpty(logLevel)) {
            log("Setting Test Reporter log level to [ " + logLevel + " ]");
            this.logLevel = determineLogLevel(logLevel);
            TestReporter.setDebugLevel(determineLogLevel(logLevel));
        }
        logTrace("Entering BaseTest#setup");

        if (isNotEmpty(environment)) {
            log("Setting parameter [ environment ] to [ " + environment + " ]");
            BaseTest.environment = environment;
        } else {
            log("Parameter [ environment ] was not set or empty");
        }

        if (isNotEmpty(runLocation)) {
            log("Setting parameter [ runLocation ] to [ " + runLocation + " ]");
            setRunLocation(runLocation);
        } else {
            log("Parameter [ runLocation ] was not set or empty");
        }

        if (isNotEmpty(reportToMustard)) {
            log("Setting parameter [ reportToMustard ] to [ " + reportToMustard + " ]");
            this.reportToMustard = toBooleanObject(reportToMustard);
        } else {
            log("Parameter [ reportToMustard ] was not set or empty");
        }

        logTrace("Exiting BaseTest#setup");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method testMethod, Object[] testParams) {
        TestReporter.setDebugLevel(logLevel);
        logTrace("Entering BaseTest#beforeMethod");
        testName = testMethod.getDeclaringClass().getSimpleName() + "#" + testMethod.getName();
        log("Starting test [ " + testName + " ]");

        int id = 1;
        for (Object param : Arrays.asList(testParams)) {
            log("Test parameter [ " + id + " ] value [ " + param.toString() + " ]");
            id++;
        }
        logTrace("Exiting BaseTest#beforeMethod");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method testMethod, ITestResult testResults) {
        logTrace("Entering BaseTest#afterMethod");
        testName = testMethod.getDeclaringClass().getSimpleName() + "#" + testMethod.getName();
        String status = null;

        switch (testResults.getStatus()) {
            case ITestResult.FAILURE:
                status = "FAIL";
                break;
            case ITestResult.SKIP:
                status = "SKIP";
                break;
            case ITestResult.SUCCESS:
                status = "PASS";
                break;
            default:
                break;
        }

        log("Ending test [ " + testName + " ] with status [ " + status + " ]. Execution time [ " + ((testResults.getEndMillis() - testResults.getStartMillis()) / 1000.0) + " ] seconds");
        logTrace("Exiting BaseTest#afterMethod");
    }

    protected void setRunLocation(String runLocation) {
        if (runLocation.equalsIgnoreCase("jenkinsParameter")) {
            BaseTest.runLocation = System.getProperty("jenkinsRunLocation".trim());
        } else {
            BaseTest.runLocation = runLocation;
        }
    }

    public String getRunLocation() {
        return runLocation;
    }

    protected void setEnvironment(String environment) {
        BaseTest.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }

    protected void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

    public String getApplication() {
        return application;
    }

    private void setApplication(String application) {
        if (application.equalsIgnoreCase("jenkinsParameter")) {
            BaseTest.application = System.getProperty("jenkinsapplication".trim());
        } else {
            BaseTest.application = application;
        }
    }

    protected void setReportToMustard(boolean reportToMustard) {
        this.reportToMustard = reportToMustard;
    }

    public boolean isReportingToMustard() {
        return reportToMustard;
    }

    private int determineLogLevel(String level) {
        switch (level.toUpperCase()) {
            case "1":
            case "INFO":
                return 1;

            case "2":
            case "DEBUG":
                return 2;

            case "3":
            case "TRACE":
                return 3;

            default:
                return 0;
        }
    }
}