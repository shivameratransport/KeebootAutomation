package com.keeboot.web.webelements.impl.internal;

import static com.keeboot.utils.TestReporter.logTrace;

import java.lang.reflect.Field;

import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.by.angular.AngularElementLocator;
import com.keeboot.web.by.angular.FindByNG;
import com.keeboot.web.by.common.CommonElementLocator;
import com.keeboot.web.by.common.FindByCommon;
import com.keeboot.web.exceptions.NullDriverException;

public class CustomElementLocatorFactory implements ElementLocatorFactory {
    private final KeebootDriver driver;

    public CustomElementLocatorFactory(final KeebootDriver driver) {
        if (driver == null) {
            throw new NullDriverException();
        }
        this.driver = driver;
    }

    @Override
    public ElementLocator createLocator(final Field field) {
        logTrace("Entering CustomElementLocatorFactory#createLocator");
        if (field.isAnnotationPresent(FindByNG.class)) {
            logTrace("Attempting to create Angular Element Locator");
            AngularElementLocator element = new AngularElementLocator(driver, field);
            logTrace("Successfully created Angular Element Locator");
            logTrace("Exiting CustomElementLocatorFactory#createLocator");
            return element;
        } else if (field.isAnnotationPresent(FindByCommon.class)) {
            logTrace("Attempting to create Common Element Locator");
            CommonElementLocator element = new CommonElementLocator(driver, field);
            logTrace("Successfully created Common Element Locator");
            logTrace("Exiting CustomElementLocatorFactory#createLocator");
            return element;
        } else {
            logTrace("Attempting to create Default Element Locator");
            DefaultElementLocator element = new DefaultElementLocator(driver.getWebDriver(), field);
            logTrace("Successfully created Default Element Locator");
            logTrace("Exiting CustomElementLocatorFactory#createLocator");
            return element;
        }
    }
}