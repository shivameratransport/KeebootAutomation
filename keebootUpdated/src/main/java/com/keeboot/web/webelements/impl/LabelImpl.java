package com.keeboot.web.webelements.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.webelements.Label;

/**
 * Wraps a label on a html form with some behavior.
 */
public class LabelImpl extends ElementImpl implements Label {
    /**
     * Creates an Element for a given WebElement.
     *
     * @param element
     *            element to wrap up
     */

    public LabelImpl(KeebootDriver driver, By by) {
        super(driver, by);
    }

    public LabelImpl(KeebootDriver driver, By by, WebElement element) {
        super(driver, by, element);
    }

    @Override
    public String getFor() {
        return getWrappedElement().getAttribute("for");
    }
}