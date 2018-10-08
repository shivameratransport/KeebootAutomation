package com.keeboot.web.webelements.impl;

import static com.keeboot.utils.Constants.MILLISECONDS_TO_POLL_FOR_ELEMENT;
import static com.keeboot.utils.TestReporter.interfaceLog;
import static com.keeboot.utils.TestReporter.logInfo;
import static com.keeboot.utils.TestReporter.logTrace;
import static com.keeboot.web.KeebootDriver.DEFAULT_SYNC_HANDLER;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.keeboot.Beta;
import com.keeboot.web.ExtendedExpectedConditions;
import com.keeboot.web.KeebootDriver;
import com.keeboot.web.debugging.Highlight;
import com.keeboot.web.exceptions.ElementAttributeValueNotMatchingException;
import com.keeboot.web.exceptions.ElementCssValueNotMatchingException;
import com.keeboot.web.exceptions.ElementNotDisabledException;
import com.keeboot.web.exceptions.ElementNotEnabledException;
import com.keeboot.web.exceptions.ElementNotFoundInFrameException;
import com.keeboot.web.exceptions.ElementNotHiddenException;
import com.keeboot.web.exceptions.ElementNotVisibleException;
import com.keeboot.web.exceptions.TextInElementNotPresentException;
import com.keeboot.web.webelements.Element;

/**
 * An implementation of the Element interface. Delegates its work to an
 * underlying WebElement instance for custom functionality.
 */
public class ElementImpl implements Element {

    protected WebElement element;
    protected By by;
    protected KeebootDriver driver;

    public ElementImpl(final KeebootDriver driver, final By by) {
        this.by = by;
        this.driver = driver;
        try {
            logTrace("Entering ElementImpl#init");
            logTrace("Inital search for element [ " + by + "]");
            WebDriverWait wait = new WebDriverWait(driver, 1);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            logTrace("Element [ " + by + "] found and stored");
        } catch (WebDriverException throwAway) {
            logTrace("Element [ " + by + "] NOT found intially, will search again later");
        }
        logTrace("Exiting ElementImpl#init");
    }

    public ElementImpl(final KeebootDriver driver, final By by, final WebElement element) {
        this.by = by;
        this.driver = driver;
        this.element = element;
        logTrace("Exiting ElementImpl#init");
    }

    /**
     * @see org.openqa.selenium.WebElement#click()
     */
    @Override
    public void click() {
        logTrace("Entering ElementImpl#click");
        try {
            getWrappedElement().click();
        } catch (RuntimeException rte) {
            interfaceLog("Clicked [ <font size = 2 color=\"red\"><b> " + getElementLocatorInfo() + " </font></b>]");
            throw rte;
        }
        interfaceLog("Clicked [ <b>" + getElementLocatorInfo() + " </b>]");
        logTrace("Exiting ElementImpl#click");
    }

    @Override
    public void jsClick() {
        logTrace("Entering ElementImpl#jsClick");
        getWrappedDriver().executeJavaScript("arguments[0].scrollIntoView(true);arguments[0].click();", getWrappedElement());
        interfaceLog("Clicked [ <b>" + getElementLocatorInfo() + " </b>]");
        logTrace("Exiting ElementImpl#jsClick");
    }

    @Override
    public void focus() {
        logTrace("Entering ElementImpl#focus");
        new Actions(getWrappedDriver()).moveToElement(getWrappedElement()).perform();
        interfaceLog("Focus on  [ <b>" + getElementLocatorInfo() + " </b>]");
        logTrace("Exiting ElementImpl#focus");
    }

    @Override
    public void focusClick() {
        logTrace("Entering ElementImpl#focusClick");
        new Actions(getWrappedDriver()).moveToElement(getWrappedElement()).click().perform();
        interfaceLog("Focus Clicked [ <b>" + getElementLocatorInfo() + " </b>]");
        logTrace("Exiting ElementImpl#focusClick");
    }

    @Override
    public void onBlur() {
        logTrace("Entering ElementImpl#onBlur");
        String jsFireEvent = "if ('createEvent' in document) { " +
                " var evt = document.createEvent('HTMLEvents'); " +
                " evt.initEvent('change', false, true); " +
                " arguments[0].dispatchEvent(evt); " +
                " } else arguments[0].fireEvent('onblur');";

        try {
            getWrappedDriver().executeJavaScript(jsFireEvent, getWrappedElement());
        } catch (WebDriverException wde) {
        }
        logTrace("Exiting ElementImpl#onBlur");
    }

    /**
     * @see org.openqa.selenium.WebElement#getLocation()
     */
    @Override
    public Point getLocation() {
        logTrace("Entering ElementImpl#getLocation");
        Point point = getWrappedElement().getLocation();
        logInfo("Location of element: X = [ " + point.getX() + " ], Y = [ " + point.getY() + " ] ");
        logTrace("Exiting ElementImpl#getLocation");
        return point;
    }

    /**
     * @see org.openqa.selenium.WebElement#submit()
     */
    @Override
    public void submit() {
        logTrace("Entering ElementImpl#submit");
        getWrappedElement().submit();
        logTrace("Exiting ElementImpl#submit");
    }

    /**
     * @see org.openqa.selenium.WebElement#getAttribute(String)
     */
    @Override
    public String getAttribute(String name) {
        logTrace("Entering ElementImpl#getAttribute");
        String value = getWrappedElement().getAttribute(name);
        logInfo("Attribute value for [ " + name + " ] is [ " + value + " ]");
        logTrace("Exiting ElementImpl#getAttribute");
        return value;
    }

    /**
     * @see org.openqa.selenium.WebElement#getCssValue(String)
     */
    @Override
    public String getCssValue(String propertyName) {
        logTrace("Entering ElementImpl#getCssValue");
        String value = getWrappedElement().getCssValue(propertyName);
        logInfo("CSS property value for [ " + propertyName + " ] is [ " + value + " ]");
        logTrace("Exiting ElementImpl#getCssValue");
        return value;
    }

    /**
     * @see org.openqa.selenium.WebElement#getSize()
     */
    @Override
    public Dimension getSize() {
        logTrace("Entering ElementImpl#getSize");
        Dimension dimension = getWrappedElement().getSize();
        logInfo("Location of element: height = [ " + dimension.getHeight() + " ], width = [ " + dimension.getWidth() + " ] ");
        logTrace("Exiting ElementImpl#getSize");
        return dimension;
    }

    /**
     * @see org.openqa.selenium.WebElement#findElement(By)
     */
    @Override
    public List<WebElement> findElements(By by) {
        logTrace("Entering ElementImpl#findElements");
        List<WebElement> elements = getWrappedElement().findElements(by);
        logTrace("Exiting ElementImpl#findElements");
        return elements;
    }

    /**
     * @see org.openqa.selenium.WebElement#getText()
     */
    @Override
    public String getText() {
        logTrace("Entering ElementImpl#getText");
        String text = getWrappedElement().getText();
        logInfo("Text found in element [ " + text + " ]");
        logTrace("Exiting ElementImpl#getText");
        return text;
    }

    /**
     * @see org.openqa.selenium.WebElement#getTagName()
     */
    @Override
    public String getTagName() {
        logTrace("Entering ElementImpl#getTagName");
        String name = getWrappedElement().getTagName();
        logInfo("Tagname of element [ " + name + " ]");
        logTrace("Exiting ElementImpl#getTagName");
        return name;
    }

    /**
     * @see org.openqa.selenium.WebElement#findElement(By)
     */
    @Override
    public Element findElement(By by) {
        logTrace("Entering ElementImpl#findElement");
        Element element = new ElementImpl(this.driver, by);
        logTrace("Exiting ElementImpl#findElement");
        return element;
    }

    /**
     * @see org.openqa.selenium.WebElement#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        logTrace("Entering ElementImpl#isEnabled");
        boolean enabled = getWrappedElement().isEnabled();
        logTrace("Exiting ElementImpl#isEnabled");
        return enabled;
    }

    /**
     * @see org.openqa.selenium.WebElement#isDisplayed()
     */
    @Override
    public boolean isDisplayed() {
        logTrace("Entering ElementImpl#isDisplayed");
        boolean displayed = getWrappedElement().isDisplayed();
        logTrace("Exiting ElementImpl#isDisplayed");
        return displayed;
    }

    /**
     * @see org.openqa.selenium.WebElement#isSelected()
     */
    @Override
    public boolean isSelected() {
        logTrace("Entering ElementImpl#isSelected");
        boolean selected = getWrappedElement().isSelected();
        logTrace("Exiting ElementImpl#isSelected");
        return selected;
    }

    /**
     * @see org.openqa.selenium.WebElement#clear()
     */
    @Override
    public void clear() {
        logTrace("Entering ElementImpl#clear");
        getWrappedElement().clear();
        interfaceLog("Clear text from Element [ <b>" + getElementLocatorInfo() + " </b> ]");
        logTrace("Exiting ElementImpl#clear");
    }

    /**
     * @see org.openqa.selenium.WebElement#sendKeys(CharSequence...)
     */
    @Override
    public void sendKeys(CharSequence... keysToSend) {
        logTrace("Entering ElementImpl#sendKeys");
        String keys = "";
        if (!(keysToSend.toString()).equals("")) {
            getWrappedElement().sendKeys(keysToSend);

            for (CharSequence key : keysToSend) {
                if (key instanceof Keys) {
                    if (keys.isEmpty()) {
                        keys = "Key." + ((Keys) key).name();
                    } else {
                        keys += " + Key." + ((Keys) key).name();
                    }
                } else {
                    if (keys.isEmpty()) {
                        keys = key.toString();
                    } else {
                        keys += key.toString();
                    }
                }
            }

            interfaceLog(" Send Keys [ <b>" + keys + "</b> ] to Textbox [ <b>"
                    + getElementIdentifier() + " </b> ]");
        }
        logTrace("Exiting ElementImpl#sendKeys");
    }

    @Override
    public WebElement getWrappedElement() {
        logTrace("Entering ElementImpl#getWrappedElement");
        WebElement tempElement = null;
        try {
            logTrace("Validate element [ " + by.toString() + " ] is not null");
            if (element == null) {
                logTrace("Element [ " + by.toString() + " ] is null, attempt to reload the element");
                tempElement = reload();
                logTrace("Successfully reloaded element [ " + by.toString() + " ]");
            } else {
                tempElement = element;
            }

            logTrace("Validate element [ " + by.toString() + " ] is not stale");
            tempElement.isEnabled();
            logTrace("Successfully validated element [ " + by.toString() + " ] is usable");
            logTrace("Exiting ElementImpl#getWrappedElement");
            return tempElement;
        } catch (StaleElementReferenceException | NullPointerException e) {

            try {
                logTrace("Element [ " + by.toString() + " ] is stale, attempt to reload the element");
                tempElement = reload();
                logTrace("Successfully reloaded element [ " + by.toString() + " ]");
                logTrace("Exiting ElementImpl#getWrappedElement");
                return tempElement;
            } catch (NullPointerException sere) {
                logTrace("Exiting ElementImpl#getWrappedElement");
                return element;
            }
        } catch (NoSuchElementException nsee) {
            logTrace("Failed to reload element [ " + by.toString() + " ]");
            logTrace("Exiting ElementImpl#getWrappedElement");
            throw nsee;
        }
    }

    @Override
    public KeebootDriver getWrappedDriver() {
        if (driver != null) {
            return driver;
        }
        WebDriver ldriver = null;
        Field privateStringField = null;
        if (element == null) {
            getWrappedElement();
        }
        if (driver == null) {
            if (element instanceof ElementImpl) {
                try {
                    WebElement wrappedElement = ((WrapsElement) element).getWrappedElement();

                    privateStringField = wrappedElement.getClass().getDeclaredField("parent");
                    privateStringField.setAccessible(true);
                    ldriver = (WebDriver) privateStringField.get(wrappedElement);
                    KeebootDriver oDriver = new KeebootDriver();
                    oDriver.setDriver(ldriver);
                    return oDriver;

                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e) {
                }
            } else {

                try {
                    privateStringField = element.getClass().getDeclaredField("parent");
                    privateStringField.setAccessible(true);
                    ldriver = (WebDriver) privateStringField.get(element);
                    KeebootDriver oDriver = new KeebootDriver();
                    oDriver.setDriver(ldriver);
                    return oDriver;
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
        return driver;
    }

    /**
     * @see org.openqa.selenium.internal.Locatable#getCoordinates();
     */
    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) getWrappedElement()).getCoordinates();
    }

    @Override
    public boolean elementWired() {
        return (getWrappedElement() != null);
    }

    public String getElementIdentifier() {
        String locator = "";
        int startPosition = 0;
        startPosition = by.toString().lastIndexOf(": ") + 2;
        locator = by.toString().substring(startPosition, by.toString().length());
        return locator.trim();

    }

    @Override
    public String getElementLocatorInfo() {
        return by.toString();
    }

    @Override
    public void highlight() {
        logTrace("Entering ElementImpl#highlight");
        Highlight.highlight(getWrappedDriver(), getWrappedElement());
        logTrace("Exiting ElementImpl#highlight");
    }

    @Override
    public void scrollIntoView() {
        logTrace("Entering ElementImpl#scrollIntoView");
        getWrappedDriver().executeJavaScript("arguments[0].scrollIntoView(true);", element);
        logTrace("Exiting ElementImpl#scrollIntoView");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ArrayList getAllAttributes() {
        return (ArrayList) driver.executeJavaScript(
                "var s = []; var attrs = arguments[0].attributes; for (var l = 0; l < attrs.length; ++l) { var a = attrs[l]; s.push(a.name + ':' + a.value); } ; return s;",
                getWrappedElement());
    }

    @Beta
    @Override
    public <X> X getScreenshotAs(OutputType<X> target) {
        return ((TakesScreenshot) driver.getWebDriver()).getScreenshotAs(target);
    }

    /**
     * Used to determine if the desired element is visible on the screen
     * Will wait for default element timeout unless new timeout is passed in
     * If object is not visible within the time, handle the error based default handler
     * or by boolean passed in
     *
     * @author Justin
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncVisible(10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncVisible(10, false)
     */
    @Override
    public boolean syncVisible(Object... args) {
        logTrace("Entering ElementImpl#syncVisible");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        interfaceLog("<i>Syncing to element [<b>" + getElementLocatorInfo()
                + "</b> ] to be <b>VISIBLE</b> within [ <b>" + timeout + "</b> ] seconds.</i>");

        StopWatch stopwatch = new StopWatch();
        boolean found = false;
        long timeLapse;

        WebDriverWait wait = new WebDriverWait(driver, 1);
        stopwatch.start();
        while (((stopwatch.getTime()) / 1000.0) < timeout && !found) {
            try {
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExtendedExpectedConditions.elementToBeVisible(reload()));
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
                System.out.println(te.getMessage());
            }
        }
        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            logTrace("Element not <b>VISIBLE</b> and failTestOnSync is [ TRUE ]");
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not <b>VISIBLE</b> on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementNotVisibleException(
                    "Element [ " + getElementLocatorInfo() + " ] is not VISIBLE on the page after [ "
                            + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not <b>VISIBLE</b> on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncVisible");
            return found;
        }

        interfaceLog("<i>Element [<b>" + getElementLocatorInfo() + " </b>] is <b>VISIBLE</b> on the page after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncVisible");
        return found;

    }

    /**
     * Used to determine if the desired element is hidden on the screen
     * Will wait for default element timeout unless new timeout is passed in
     * If object is not hidden within the time, handle the error based default handler
     * or by boolean passed in
     *
     * @author Justin
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncHidden(10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncHidden(10, false)
     */
    @Override
    public boolean syncHidden(Object... args) {
        logTrace("Entering ElementImpl#syncHidden");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to element [<b>" + getElementLocatorInfo()
                + "</b> ] to be <b>HIDDEN</b> within [ <b>" + timeout + "</b> ] seconds.</i>");

        boolean found = false;
        long timeLapse;
        stopwatch.start();
        WebDriverWait wait = new WebDriverWait(driver, 1);
        while (((stopwatch.getTime()) / 1000.0) < timeout && !found) {
            try {
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExtendedExpectedConditions.elementToBeHidden(reload()));
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
            }
        }

        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            logTrace("Element not <b>HIDDEN</b> and failTestOnSync is [ TRUE ]");
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not <b>HIDDEN</b> on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementNotHiddenException(
                    "Element [ " + getElementLocatorInfo() + " ] is not HIDDEN on the page after [ "
                            + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not <b>HIDDEN</b> on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncHidden");
            return found;
        }

        interfaceLog("<i>Element [<b>" + getElementLocatorInfo() + " </b>] is <b>HIDDEN</b> on the page after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        logTrace("Exiting ElementImpl#syncHidden");
        return found;
    }

    /**
     * Used to determine if the desired element is enabled on the screen
     * Will wait for default element timeout unless new timeout is passed in
     * If object is not enabled within the time, handle the error based default handler
     * or by boolean passed in
     *
     * @author Justin
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncEnabled(10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncEnabled(10, false)
     */
    @Override
    public boolean syncEnabled(Object... args) {
        logTrace("Entering ElementImpl#syncEnabled");
        int requestedTimeout = getWrappedDriver().getElementTimeout();
        int currentElementTimeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        driver.setElementTimeout(1);

        try {
            if (args[0] != null) {
                requestedTimeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        boolean found = false;
        long timeLapse;
        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to element [<b>" + getElementLocatorInfo()
                + "</b> ] to be <b>ENABLED</b> within [ <b>" + requestedTimeout + "</b> ] seconds.</i>");
        stopwatch.start();
        WebDriverWait wait = new WebDriverWait(driver, 1);

        while (((stopwatch.getTime()) / 1000.0) < requestedTimeout && !found) {
            try {
                if (Highlight.getDebugMode()) {
                    Highlight.highlightDebug(driver, reload());
                }
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExpectedConditions.elementToBeClickable(reload())) != null;
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
            } catch (WebDriverException we) {
                if (!we.getMessage().toLowerCase().contains("is not clickable at point")) {
                    throw we;
                }
            }
        }

        driver.setElementTimeout(currentElementTimeout);

        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            Highlight.highlightError(driver, element);
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not <b>ENABLED</b> on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementNotEnabledException(
                    "Element [ " + getElementLocatorInfo() + " ] is not ENABLED on the page after [ "
                            + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not <b>ENABLED</b> on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncEnabled");
            return found;
        }

        interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                + " </b>] is <b>ENABLED</b> on the page after [ "
                + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncEnabled");
        return found;
    }

    /**
     * Used to determine if the desired element is disabled on the screen
     * Will wait for default element timeout unless new timeout is passed in
     * If object is not disabled within the time, handle the error based default handler
     * or by boolean passed in
     *
     * @author Justin
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncDisabled(10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncDisabled(10, false)
     */
    @Override
    public boolean syncDisabled(Object... args) {
        logTrace("Entering ElementImpl#syncDisabled");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        boolean found = false;
        long timeLapse;
        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to element [<b>" + getElementLocatorInfo()
                + "</b> ] to be <b>DISABLED</b> within [ <b>" + timeout + "</b> ] seconds.</i>");
        stopwatch.start();
        WebDriverWait wait = new WebDriverWait(driver, 1);

        while (((stopwatch.getTime()) / 1000.0) < timeout && !found) {
            try {
                if (Highlight.getDebugMode()) {
                    Highlight.highlightDebug(driver, reload());
                }
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(reload()))) != null;
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
            } catch (WebDriverException we) {
                if (!we.getMessage().toLowerCase().contains("is not clickable at point")) {
                    throw we;
                }
            }
        }

        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            Highlight.highlightError(driver, reload());
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not <b>DISABLED</b> on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementNotDisabledException(
                    "Element [ " + getElementLocatorInfo() + " ] is not DISABLED on the page after [ "
                            + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not <b>DISABLED</b> on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncDisabled");
            return found;
        }

        interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                + " </b>] is <b>DISABLED</b> on the page after [ "
                + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncDisabled");
        return found;
    }

    /**
     * Sync for the Element's text or it's value attribute contains the desired text.
     * Additional parameters can be added to override the default timeout and if the
     * test should fail if the sync fails
     *
     * @param text
     *            (Required) The text the element should contain in either its text or value attribute
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncTextInElement("text", 10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncTextInElement("text", 10, false)
     */
    @Override
    public boolean syncTextInElement(String text, Object... args) {
        logTrace("Entering ElementImpl#syncTextInElement");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        boolean found = false;
        long timeLapse;
        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to text [<b>" + text + "</b> ] in element [<b>"
                + getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");

        WebDriverWait wait = new WebDriverWait(driver, 0);
        stopwatch.start();
        if (Highlight.getDebugMode()) {
            Highlight.highlightDebug(driver, reload());
        }
        do {
            try {
                if (wait.until(ExpectedConditions.textToBePresentInElement(reload(), text)) != null) {
                    found = true;
                } else if (wait.until(ExpectedConditions.textToBePresentInElementValue(reload(), text)) != null) {
                    found = true;
                }
                if (found) {
                    break;
                }
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
            }
        } while (stopwatch.getTime() / 1000.0 < timeout);

        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            Highlight.highlightError(driver, element);
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] did not contain the text [ " + text
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new TextInElementNotPresentException(
                    "Element [ " + getElementLocatorInfo() + " ] did not contain the text [ " + text
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] did not contain the text [ " + text
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncTextInElement");
            return found;
        }
        interfaceLog(
                "<i>Element [<b>" + getElementLocatorInfo() + " </b>] contains the text [ " + text
                        + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncTextInElement");
        return found;
    }

    /**
     * Sync for the Element's text or it's value attribute contains the desired text.
     * Additional parameters can be added to override the default timeout and if the
     * test should fail if the sync fails
     *
     * @param regex
     *            (Required) The text the element should contain in either its text or value attribute
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncTextMatchesInElement("text", 10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncTextMatchesInElement("text", 10, false)
     */
    @Override
    public boolean syncTextMatchesInElement(String regex, Object... args) {
        logTrace("Entering ElementImpl#syncTextMatchesInElement");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        boolean found = false;
        long timeLapse;
        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to text regular expression [<b>" + regex + "</b> ] in element [<b>"
                + getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");
        stopwatch.start();
        WebDriverWait wait = new WebDriverWait(driver, 0);
        do {

            try {
                if (Highlight.getDebugMode()) {
                    Highlight.highlightDebug(driver, reload());
                }
                if (wait.until(ExtendedExpectedConditions.textToMatchInElement(reload(), regex)) != null) {
                    found = true;
                } else if (wait.until(ExtendedExpectedConditions.textToMatchInElementAttribute(reload(), "value", regex)) != null) {
                    found = true;
                }
                if (found) {
                    break;
                }
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
            }
        } while (stopwatch.getTime() / 1000.0 < timeout);
        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            Highlight.highlightError(driver, reload());
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] did not contain the text [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new TextInElementNotPresentException(
                    "Element [ " + getElementLocatorInfo() + " ] did not contain the text [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] did not contain the text [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncTextMatchesInElement");
            return found;
        }

        interfaceLog(
                "<i>Element [<b>" + getElementLocatorInfo() + " </b>] contains the text [ " + regex
                        + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncTextMatchesInElement");
        return found;
    }

    /**
     * Sync for the Element's text or it's value attribute contains the desired text.
     * Additional parameters can be added to override the default timeout and if the
     * test should fail if the sync fails
     *
     * @param attribute
     *            (Required) - Element attribute to view
     * @param value
     *            (Required) - The text the element attribute should contain in either its text or value attribute
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncAttributeContainsValue("text", 10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncAttributeContainsValue("text", 10, false)
     */
    @Override
    public boolean syncAttributeContainsValue(String attribute, String value, Object... args) {
        logTrace("Entering ElementImpl#syncAttributeContainsValue");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }
        boolean found = false;
        long timeLapse;
        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to attribute [<b> " + attribute + "</b> ] to contain [<b> " + value + "</b> ] in element [<b>"
                + getElementLocatorInfo() + "</b> ] to be displayed within [ <b> " + timeout + "</b> ] seconds.</i>");
        stopwatch.start();
        WebDriverWait wait = new WebDriverWait(driver, 1);

        while (((stopwatch.getTime()) / 1000.0) < timeout && !found) {
            try {
                if (Highlight.getDebugMode()) {
                    Highlight.highlightDebug(driver, reload());
                }
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExtendedExpectedConditions.textToBePresentInElementAttribute(reload(), attribute, value));
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
            }
        }

        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            Highlight.highlightError(driver, reload());
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] attribute [<b>" + attribute + "</b> ] did not contain the text [ " + value
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementAttributeValueNotMatchingException(
                    "Element [ " + getElementLocatorInfo() + " ]attribute [" + attribute + "] did not contain the text [ " + value
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] attribute [<b>" + attribute + "</b> ] did not contain the text [ " + value
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncAttributeContainsValue");
            return found;
        }

        interfaceLog(
                "<i>Element [<b>" + getElementLocatorInfo() + " </b>] attribute [<b>" + attribute + "</b> ] contains the text [ " + value
                        + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncAttributeContainsValue");
        return found;
    }

    /**
     * Sync for the Element's text or it's value attribute contains the desired text.
     * Additional parameters can be added to override the default timeout and if the
     * test should fail if the sync fails
     *
     * @param attribute
     *            (Required) - Element attribute to view
     * @param regex
     *            (Required) - The regular expression that should match the text of the element attribute
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncAttributeMatchesValue("text", 10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncAttributeMatchesValue("text", 10, false)
     */
    @Override
    public boolean syncAttributeMatchesValue(String attribute, String regex, Object... args) {
        logTrace("Entering ElementImpl#syncAttributeMatchesValue");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        boolean found = false;
        long timeLapse;
        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to attribute [<b> " + attribute + "</b> ] to match the regular expression of [<b> " + regex + "</b> ] in element [<b>"
                + getElementLocatorInfo() + "</b> ] to be displayed within [ <b> " + timeout + "</b> ] seconds.</i>");
        WebDriverWait wait = new WebDriverWait(driver, 1);

        stopwatch.start();
        while (((stopwatch.getTime()) / 1000.0) < timeout && !found) {
            try {
                if (Highlight.getDebugMode()) {
                    Highlight.highlightDebug(driver, reload());
                }
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExtendedExpectedConditions.textToMatchInElementAttribute(reload(), attribute, regex));
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
                found = false;
            }
        }

        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            Highlight.highlightError(driver, reload());
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] attribute [<b>" + attribute + "</b> ] did not match the regular expression of [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementAttributeValueNotMatchingException(
                    "Element [ " + getElementLocatorInfo() + " ]attribute [" + attribute + "] did not match the regular expression of [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] attribute [<b>" + attribute + "</b> ] did not match the regular expression of [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncAttributeMatchesValue");
            return found;
        }
        interfaceLog(
                "<i>Element [<b>" + getElementLocatorInfo() + " </b>] attribute [<b>" + attribute + "</b> ] matches the regular expression of [ " + regex
                        + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncAttributeMatchesValue");
        return found;
    }

    /**
     * Sync for the Element's text or it's value attribute contains the desired text.
     * Additional parameters can be added to override the default timeout and if the
     * test should fail if the sync fails
     *
     * @param cssProperty
     *            (Required) - Element CSS Property to view
     * @param value
     *            (Required) - The text the element attribute should contain in either its text or value attribute
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncCssContainsValue("text", 10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncCssContainsValue("text", 10, false)
     */
    @Override
    public boolean syncCssPropertyContainsValue(String cssProperty, String value, Object... args) {
        logTrace("Entering ElementImpl#syncCssPropertyContainsValue");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        boolean found = false;
        long timeLapse;
        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to CSS Property [<b> " + cssProperty + "</b> ] to contain [<b> " + value + "</b> ] in element [<b>"
                + getElementLocatorInfo() + "</b> ] to be displayed within [ <b> " + timeout + "</b> ] seconds.</i>");

        WebDriverWait wait = new WebDriverWait(driver, 1);
        stopwatch.start();
        while (((stopwatch.getTime()) / 1000.0) < timeout && !found) {
            try {
                if (Highlight.getDebugMode()) {
                    Highlight.highlightDebug(driver, reload());
                }
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExtendedExpectedConditions.textToBePresentInElementCssProperty(reload(), cssProperty, value));
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
                found = false;
            }
        }

        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            Highlight.highlightError(driver, reload());
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] CSS Property [<b>" + cssProperty + "</b> ] did not contain the text [ " + value
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementCssValueNotMatchingException("Element [ " + getElementLocatorInfo() + " ] CSS Property [" + cssProperty + " ] did not contain the text [ " + value
                    + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.", driver);
        } else if (!found) {
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] CSS Property [<b>" + cssProperty + "</b> ] did not contain the text [ " + value
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncCssPropertyContainsValue");
            return found;
        }

        interfaceLog(
                "<i>Element [<b>" + getElementLocatorInfo() + " </b>] CSS Property [<b>" + cssProperty + "</b> ] contains the text [ " + value
                        + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncCssPropertyContainsValue");
        return found;
    }

    /**
     * Sync for the Element's text or it's value attribute contains the desired text.
     * Additional parameters can be added to override the default timeout and if the
     * test should fail if the sync fails
     *
     * @param cssProperty
     *            (Required) - Element CSS Property to match
     * @param regex
     *            (Required) - The regular expression that should match the text of the element CSS Property
     * @param args
     *            Optional arguments </br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called
     *            with syncCssMatchesValue("text", 10)</br>
     *            &nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and
     *            fail the script. If FALSE, the script will
     *            not fail, instead a FALSE will be returned
     *            to the calling function. Called with
     *            syncCssMatchesValue("text", 10, false)
     */
    @Override
    public boolean syncCssPropertyMatchesValue(String cssProperty, String regex, Object... args) {
        logTrace("Entering ElementImpl#syncCssPropertyMatchesValue");
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        boolean found = false;
        long timeLapse;
        StopWatch stopwatch = new StopWatch();
        interfaceLog("<i>Syncing to CSS Property [<b> " + cssProperty + "</b> ] to contain [<b> " + regex + "</b> ] in element [<b>"
                + getElementLocatorInfo() + "</b> ] to be displayed within [ <b> " + timeout + "</b> ] seconds.</i>");
        WebDriverWait wait = new WebDriverWait(driver, 0);
        stopwatch.start();
        while (((stopwatch.getTime()) / 1000.0) < timeout && !found) {
            try {
                if (Highlight.getDebugMode()) {
                    Highlight.highlightDebug(driver, reload());
                }
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExtendedExpectedConditions.textToMatchInElementCssProperty(reload(), cssProperty, regex));
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
                found = false;
            }
        }

        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            Highlight.highlightError(driver, reload());
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] CSS Property [<b>" + cssProperty + "</b> ] did not match the regular expression of [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementCssValueNotMatchingException(
                    "Element [ " + getElementLocatorInfo() + " ] CSS Property [" + cssProperty + "] did not match the regular expression of [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog(
                    "<i>Element [<b>" + getElementLocatorInfo() + " </b>] CSS Property [<b>" + cssProperty + "</b> ] did not match the regular expression of [ " + regex
                            + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncCssPropertyMatchesValue");
            return found;
        }
        interfaceLog(
                "<i>Element [<b>" + getElementLocatorInfo() + " </b>] CSS Property [<b>" + cssProperty + "</b> ] matches the regular expression of [ " + regex
                        + " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        if (Highlight.getDebugMode()) {
            Highlight.highlightSuccess(driver, reload());
        }
        logTrace("Exiting ElementImpl#syncCssPropertyMatchesValue");
        return found;
    }

    @Beta
    protected WebElement reload() {
        logTrace("Entering ElementImpl#reload");
        WebElement el = null;
        logTrace("Search DOM for element [ " + by.toString() + " ]");

        try {
            WebDriverWait wait = new WebDriverWait(getWrappedDriver().getWebDriver(), getWrappedDriver().getElementTimeout());
            el = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (WebDriverException wde) {
            throw new NoSuchElementException("Failed locate element [ " + by.toString() + " ]");
        }
        logTrace("Found element [ " + by.toString() + " ]");
        logTrace("Exiting ElementImpl#reload");
        return el;
    }

    @Override
    @Beta
    public boolean syncInFrame(Object... args) {
        logTrace("Entering ElementImpl#syncInFrame");
        final String action = "<b>FOUND IN FRAME</b>";
        int originalDriverTimeout = getWrappedDriver().getElementTimeout();
        int timeout = getWrappedDriver().getElementTimeout();
        boolean failTestOnSync = DEFAULT_SYNC_HANDLER;
        getWrappedDriver().setElementTimeout(0);
        try {
            if (args[0] != null) {
                timeout = Integer.valueOf(args[0].toString());
            }
            if (args[1] != null) {
                failTestOnSync = Boolean.parseBoolean(args[1].toString());
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        interfaceLog("<i>Syncing to element [<b>" + getElementLocatorInfo()
                + "</b> ] to be " + action + " within [ <b>" + timeout + "</b> ] seconds.</i>");

        StopWatch stopwatch = new StopWatch();
        boolean found = false;
        long timeLapse;

        WebDriverWait wait = new WebDriverWait(driver, 1);
        stopwatch.start();
        while (((stopwatch.getTime()) / 1000.0) < timeout && !found) {
            try {
                found = wait.pollingEvery(MILLISECONDS_TO_POLL_FOR_ELEMENT, TimeUnit.MILLISECONDS).until(ExtendedExpectedConditions.elementToFoundInFrame(by));
            } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException te) {
            }
        }
        stopwatch.stop();
        timeLapse = stopwatch.getTime();
        stopwatch.reset();

        if (!found && failTestOnSync) {
            logTrace("Element not " + action + " and failTestOnSync is [ TRUE ]");
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not " + action + " on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            throw new ElementNotFoundInFrameException(
                    "Element [ " + getElementLocatorInfo() + " ] is not FOUND IN FRAME on the page after [ "
                            + (timeLapse) / 1000.0 + " ] seconds.",
                    driver);
        } else if (!found) {
            interfaceLog("<i>Element [<b>" + getElementLocatorInfo()
                    + " </b>] is not " + action + " on the page after [ "
                    + (timeLapse) / 1000.0 + " ] seconds.</i>");
            logTrace("Exiting ElementImpl#syncInFrame");
            getWrappedDriver().setElementTimeout(originalDriverTimeout);
            return found;
        }

        interfaceLog("<i>Element [<b>" + getElementLocatorInfo() + " </b>] is " + action + " on the page after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
        getWrappedDriver().setElementTimeout(originalDriverTimeout);
        logTrace("Exiting ElementImpl#syncInFrame");
        return found;
    }

    @Override
    public Rectangle getRect() {
        // TODO Auto-generated method stub
        return null;
    }

}
