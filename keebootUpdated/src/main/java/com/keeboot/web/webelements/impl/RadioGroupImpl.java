package com.keeboot.web.webelements.impl;

import static com.keeboot.utils.TestReporter.interfaceLog;
import static com.keeboot.utils.TestReporter.logTrace;

import java.util.Arrays;
import java.util.List;

import javax.naming.directory.NoSuchAttributeException;

import org.apache.commons.collections.list.FixedSizeList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.webelements.RadioGroup;

/**
 * Wrapper around a WebElement for the Select class in Selenium.
 */
public class RadioGroupImpl extends ElementImpl implements RadioGroup {
    private List<WebElement> radioButtons = null;
    private int numberOfRadioButtons;
    private int currentIndex;
    private List<String> stringOptions;
    private int numberOfOptions;
    private String selectedOption;

    /**
     * Wraps a WebElement as an Element with radioGroup functionality.
     *
     * @param element
     *            - element to wrap up
     * @throws NoSuchAttributeException
     */

    public RadioGroupImpl(KeebootDriver driver, By by) {
        super(driver, by);
        logTrace("Entering RadioGroupImpl#init");
        int timeout = driver.getElementTimeout();
        driver.setElementTimeout(0);
        this.radioButtons = element.findElements(By.tagName("input"));
        if (radioButtons.size() == 0) {
            radioButtons = driver.findWebElements(by);
        }
        driver.setElementTimeout(timeout);
        getNumberOfRadioButtons();
        getAllOptions();
        Assert.assertNotEquals(radioButtons.size(), 0,
                "No radio buttons were found for the element [" + element + "].");
        currentIndex = getCurrentIndex();
        logTrace("Exiting RadioGroupImpl#init");
    }

    /**
     * @summary - Defines the number of radio buttons in the group by the number
     *          of 'input' tags found in the wrapped object
     */
    private void setNumberOfRadioButtons() {
        numberOfRadioButtons = radioButtons.size();
    }

    /**
     * @summary - Defines the number of radio buttons and return the integer
     *          count
     */
    // @Override
    private int getNumberOfRadioButtons() {
        setNumberOfRadioButtons();
        return numberOfRadioButtons;
    }

    /**
     * @summary - Sets the current index for this instance, selects the radio
     *          button by index and sets the selected option for this instance
     */
    @Override
    public void selectByIndex(int index) {
        logTrace("Entering RadioGroupImpl#selectByIndex");
        currentIndex = index;
        try {
            radioButtons.get(currentIndex).click();
        } catch (RuntimeException rte) {
            interfaceLog("Select option <b> [ " + currentIndex
                    + " ] </b> from the radio group [ <b>" + getElementLocatorInfo() + " </b> ]", true);
            logTrace("Exiting RadioGroupImpl#selectByIndex");
            throw rte;
        }
        interfaceLog("Select option <b> [ " + currentIndex + " ] </b> from the radio group [ <b>"
                + getElementLocatorInfo() + " </b> ]", true);

        setSelectedOption();
        logTrace("Exiting RadioGroupImpl#selectByIndex");
    }

    /**
     * @summary - Defines and returns a List<String> of all options for the
     *          radio group
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getAllOptions() {
        logTrace("Entering RadioGroupImpl#getAllOptions");
        stringOptions = FixedSizeList.decorate(Arrays.asList(new String[radioButtons.size()]));
        int loopCounter = 0;

        for (WebElement option : radioButtons) {
            stringOptions.set(loopCounter, option.getAttribute("value"));
            loopCounter++;
        }

        logTrace("Exiting RadioGroupImpl#getAllOptions");
        return stringOptions;
    }

    /**
     * @summary - Defines a List<String> of all options for this instance as
     *          well as the number of options for the radio group
     */

    private void setNumberOfOptions() {
        getAllOptions();
        numberOfOptions = stringOptions.size();
    }

    /**
     * @summary - Defines the number of options and return the integer count
     */
    @Override
    public int getNumberOfOptions() {
        logTrace("Entering RadioGroupImpl#getNumberOfOptions");
        setNumberOfOptions();
        logTrace("Exiting RadioGroupImpl#getNumberOfOptions");
        return numberOfOptions;
    }

    /**
     * @summary - Defines all options for this instance, selects an option by
     *          the string parameter and sets the selected option for this
     *          instance
     */
    @Override
    public void selectByOption(String option) {
        logTrace("Entering RadioGroupImpl#selectByOption");
        getAllOptions();
        for (int loopCounter = 0; loopCounter < stringOptions.size(); loopCounter++) {
            if (stringOptions.get(loopCounter).trim().equalsIgnoreCase(option.trim())) {
                currentIndex = loopCounter;

                try {
                    new ElementImpl(getWrappedDriver(), By.xpath("//input[" + (currentIndex + 1) + "]")).click();
                } catch (RuntimeException rte) {
                    interfaceLog("Select option <b> [ " + option
                            + " ] </b> from the radio group [ <b>" + getElementLocatorInfo() + " </b> ]",
                            true);
                    logTrace("Exiting RadioGroupImpl#selectByOption");
                    throw rte;
                }
                interfaceLog("Select option <b> [ " + option
                        + " ] </b> from the radio group [ <b>" + getElementLocatorInfo() + " </b> ]");

                getSelectedOption();
                break;
            }
        }
        setSelectedOption();
        logTrace("Exiting RadioGroupImpl#selectByOption");
    }

    /**
     * @summary - Defines a List<String> of all options for this instance and
     *          defines the currently selected option, if any
     */

    private void setSelectedOption() {
        getAllOptions();
        selectedOption = stringOptions.get(currentIndex).toString();
    }

    /**
     * @summary - Defines a List<String> of all options for this instance and
     *          defines the currently selected option, if any and returns the
     *          String value of the selected option
     */
    @Override
    public String getSelectedOption() {
        logTrace("Entering RadioGroupImpl#getSelectedOption");
        setSelectedOption();
        logTrace("Entering RadioGroupImpl#getSelectedOption");
        return this.selectedOption;
    }

    /**
     * @summary - Returns the integer index of the currently selected radio
     *          button
     */
    @Override
    public int getSelectedIndex() {
        return currentIndex;
    }

    /**
     * @summary - Loops through all radio buttons for one that possesses an
     *          attribute that indicates the button is selected. NOTE: Within
     *          the method, the field "String[] attributes" is a string array
     *          for possible values that could indicate radio button is
     *          selected/checked. This array can be appended with new attributes
     *          that indicate an option is selected/checked.
     */
    private int getCurrentIndex() {
        String[] attributes = { "checked" };
        int loopCounter = 0;
        int attributeLoopCounter = 0;
        int index = -1;
        String checked = null;

        for (loopCounter = 0; loopCounter < numberOfRadioButtons; loopCounter++) {
            for (attributeLoopCounter = 0; attributeLoopCounter < attributes.length; attributeLoopCounter++) {
                if (!(radioButtons.get(loopCounter).getAttribute(attributes[attributeLoopCounter]) == null)) {
                    checked = radioButtons.get(loopCounter).getAttribute(attributes[attributeLoopCounter]);
                    if (checked.equalsIgnoreCase("true")) {
                        index = loopCounter;
                        break;

                    }
                }
            }
            if (checked != null) {
                break;
            }
        }
        return index;
    }
}