package com.keeboot.web.webelements.impl;

import static com.keeboot.utils.TestReporter.interfaceLog;
import static com.keeboot.utils.TestReporter.logFailure;
import static com.keeboot.utils.TestReporter.logTrace;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebException;
import com.keeboot.web.webelements.Button;

/**
 * Wraps a label on a html form with some behavior.
 */
public class ButtonImpl extends ElementImpl implements Button {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param element
     *            - element to wrap up
     */

    public ButtonImpl(KeebootDriver driver, By by) {
        super(driver, by);
    }

    public ButtonImpl(KeebootDriver driver, By by, WebElement element) {
        super(driver, by, element);
    }

    @Override
    public void click() {
        logTrace("Entering ButtonImpl#click");
        try {
            logTrace("Attempting to invoke method [ Click ] on element [ " + by.toString() + " ] ");
            getWrappedElement().click();
        } catch (RuntimeException rte) {
            interfaceLog("Clicked Button [ <b>" + getElementLocatorInfo() + "</b>]", true);
            throw rte;
        }

        interfaceLog("Clicked Button [ <b>" + getElementLocatorInfo() + "</b>]");
        logTrace("Successfully invoked method [ Click ] on element [ " + by.toString() + " ] ");
        logTrace("Exiting ButtonImpl#click");
    }

    @Override
    public void jsClick() {
        logTrace("Entering ButtonImpl#jsClick");
        try {
            logTrace("Attempting to executed [ jsClick ] on element [ " + by.toString() + " ] ");
            getWrappedDriver().executeJavaScript("arguments[0].click();", getWrappedElement());
            logTrace("Successfully executed [ jsClick ] on element [ " + by.toString() + " ] ");
        } catch (RuntimeException rte) {
            logFailure("Clicked Button [ <b>" + getElementLocatorInfo() + "</b>]");
            logTrace("Failed to execute [ jsClick ] on element [ " + by.toString() + " ] ");
            logTrace("Exiting ButtonImpl#jsClick");
            throw new WebException(rte.getMessage(), driver);
        }
        interfaceLog("Clicked Button [ <b>" + getElementLocatorInfo() + "</b>]");
        logTrace("Exiting ButtonImpl#jsClick");

    }
}