package com.keeboot.web;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;

import com.keeboot.utils.Sleeper;
import com.keeboot.utils.TestReporter;
import com.keeboot.web.exceptions.PageInitialization;
import com.keeboot.web.webelements.Element;

/**
 * Several different methods of waiting for a page to finish loading.
 *
 *
 * @version 10/16/2014
 * @author Justin Phlegar
 *
 */
public class PageLoaded {
    /**
     * This waits for a specified element on the page to be found on the page by
     * the driver Uses the default test time out set by WebDriverSetup
     *
     * @param clazz
     *            the class calling this method - used so can initialize the
     *            page class repeatedly
     * @param oDriver
     *            The webDriver
     * @param obj
     *            The element you are waiting to display on the page
     * @version 10/16/2014
     * @author Justin Phlegar
     * @return False if the element is not found after the timeout, true if is
     *         found
     */
    @SuppressWarnings("rawtypes")
    public static boolean isElementLoaded(Class clazz, KeebootDriver oDriver, Element obj) {
        return isElementLoaded(clazz, oDriver, obj, oDriver.getElementTimeout());
    }

    /**
     * Overloaded method where you can specify the timeout This waits for a
     * specified element on the page to be found on the page by the driver
     *
     *
     * @param clazz
     *            the class calling this method - used so can initialize the
     *            page class repeatedly
     * @param oDriver
     *            The webDriver
     * @param obj
     *            The element you are waiting to display on the page
     * @version 12/16/2014
     * @author Jessica Marshall
     * @return False if the element is not found after the timeout, true if is
     *         found
     */
    @SuppressWarnings("rawtypes")
    public static boolean isElementLoaded(Class clazz, KeebootDriver oDriver, Element obj, int timeout) {
        int count = 0;
        int driverTimeout = oDriver.getElementTimeout();
        // set the timeout for looking for an element to 1 second as we are
        // doing a loop and then refreshing the elements
        oDriver.setElementTimeout(1, TimeUnit.MILLISECONDS);

        try {

            while (!obj.elementWired()) {
                if (count == timeout) {
                    break;
                }
                count++;

            }

        } catch (NullPointerException | NoSuchElementException | StaleElementReferenceException | PageInitialization e) {
            return false;
        } finally {
            // set the timeout for looking for an element back to the default timeout
            oDriver.setElementTimeout(driverTimeout, TimeUnit.SECONDS);
        }

        if (count < timeout) {
            return true;
        }

        return false;

    }

    /**
     * This uses the HTML DOM readyState property to wait until a page is
     * finished loading. It will wait for the ready state to be either
     * 'interactive' or 'complete'.
     *
     * @version 12/16/2014
     * @author Jessica Marshall
     * @return False if the element is not found after the timeout, true if is
     *         found
     */
    public static boolean isDomInteractive(KeebootDriver oDriver) {
        return isDomInteractive(oDriver, oDriver.getPageTimeout());
    }

    /**
     * Overloaded method - gives option of specifying a timeout. This uses the
     * HTML DOM readyState property to wait until a page is finished loading. It
     * will wait for the ready state to be either 'interactive' or 'complete'.
     *
     * @param oDriver
     *            The webDriver
     * @param timeout
     *            Integer value of number seconds to wait for a page to finish
     *            loaded before quiting
     * @version 12/16/2014
     * @author Jessica Marshall
     * @return False if the element is not found after the timeout, true if is
     *         found
     */
    public static boolean isDomInteractive(KeebootDriver oDriver, int timeout) {
        int count = 0;
        Object obj = null;

        do {
            // this returns a boolean
            obj = oDriver.executeJavaScript(
                    // "var result = document.readyState; return (result == 'complete' || result == 'interactive');");
                    "var result = document.readyState; return (result == 'interactive');");// result == 'complete' ||
            if (count == timeout) {
                break;
            }
            Sleeper.sleep(500);
            count++;

        } while (obj.equals(false));

        if (count < (timeout * 2)) {
            return true;
        }

        return false;

    }

    /**
     * This uses protractor method to wait until a page is ready -
     * notifyWhenNoOutstandingRequests
     *
     * @version 10/16/2014
     * @author Justin Phlegar
     *
     */
    public static void isAngularComplete(KeebootDriver oDriver) {
        try {
            oDriver.executeAsyncJavaScript("var callback = arguments[arguments.length - 1];"
                    + "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
        } catch (WebDriverException wde) {
            // [Govind: 2018-03-23]: Changed from logFailure to logTrace as it is showing Error in TestNG report always.
            TestReporter.logTrace(
                    "Unable to perform Angular sync. This is most likely because the $browser service is not injected within the Angular Controller. Performing a IsDomComplete instead");
            isDomComplete(oDriver);
        }
    }

    /**
     * A more strict version of isDomInteractive. This uses the HTML DOM
     * readyState property to wait until a page is finished loading. It will
     * wait for the ready state to be 'complete'.
     *
     * @param oDriver
     *            The webDriver
     * @version 12/16/2014
     * @author Jessica Marshall
     * @return False if the element is not found after the timeout, true if is
     *         found
     */
    public static boolean isDomComplete(KeebootDriver oDriver) {
        return isDomComplete(oDriver, oDriver.getPageTimeout());
    }

    /**
     * Overloaded method - gives option of specifying a timeout. A more strict
     * version of isDomInteractive This uses the HTML DOM readyState property to
     * wait until a page is finished loading. It will wait for the ready state
     * to be 'complete'.
     *
     * @param oDriver
     *            The webDriver
     * @param timeout
     *            Integer value of number seconds to wait for a page to finish
     *            loaded before quiting
     * @version 12/16/2014
     * @author Jessica Marshall
     * @return False if the element is not found after the timeout, true if is
     *         found
     */
    public static boolean isDomComplete(KeebootDriver oDriver, int timeout) {
        int count = 0;
        Object obj = null;

        do {
            // this returns a boolean
            obj = oDriver.executeJavaScript("var result = document.readyState; return (result == 'complete');");

            if (count == timeout) {
                break;
            } else {
                Sleeper.sleep(500);
                count++;
            }

        } while (obj.equals(false));

        if (count < timeout * 2) {
            return true;
        } else {
            return false;
        }
    }
}