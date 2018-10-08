package com.keeboot.web.webelements;

import com.keeboot.web.webelements.impl.LinkImpl;
import com.keeboot.web.webelements.impl.internal.ImplementedBy;

/**
 * Interface that wraps a WebElement in Link functionality.
 */
@ImplementedBy(LinkImpl.class)
public interface Link extends Element {

    /**
     * @summary - Click the button using the default Selenium click
     */
    @Override
    public void click();

    /**
     * @summary - Click the link using a JavascriptExecutor click
     */
    @Override
    public void jsClick();

    public String getURL();
}