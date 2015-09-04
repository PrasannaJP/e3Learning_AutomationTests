package com.webuiframework.oua.uitests.control;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.webuiframework.oua.uitests.utils.TestBaseWebDriver;
import com.webuiframework.oua.uitests.utils.Timer;
import com.webuiframework.oua.uitests.utils.WebDriverWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.webuiframework.oua.uitests.utils.LinqUtils.first;
import static com.webuiframework.oua.uitests.utils.LinqUtils.firstIndex;
import static com.webuiframework.oua.uitests.utils.LinqUtils.select;
import static com.webuiframework.oua.uitests.utils.Sleeper.sleepTight;
import static com.webuiframework.oua.uitests.utils.TestBaseWebDriver.logFindElementLocator;
import static com.webuiframework.oua.uitests.utils.Timer.alwaysDoneAction;
import static com.webuiframework.oua.uitests.utils.WebDriverWrapper.*;



public class Elements<ParentPanel> {

    /**
     * Name of the Group Element for Report
     */
    protected String name;
    /**
     * Locator of the element if applicable
     */
    protected String locator;
    /**
     * Locator of the element if applicable
     */
    protected By bylocator;
    
    protected String ByLocator;
    /**
     * Contains name of the element used for locating its parameters in
     * properties file
     */
    protected final Properties properties = new Properties();

    /*{
        PropertyReader.getProperties(properties, this.getClass().getName());
        String panelLocator = getProperty("main");
        if (panelLocator != null) {
            this.locator = panelLocator;
            this.bylocator = getByLocator();
        }
    }*/

    /**
     * Parent panel which contains current element
     */
    protected ParentPanel parent;

    //constructors

    /**
     * Common constructor without any parameters. Locates own properties of the
     * element by class name and tries to use it to initialize.
     */
    public Elements() {
    }

    /**
     * Initializes element's with given locator. Locates own properties of the
     * element by class name, takes given locator and tries to initialize.
     *
     * @param name    - Element name
     * @param locator - start it with locator type "id=", "css=", "xpath=" and
     *                etc. Locator without type is assigned to xpath
     * @param panel   - Parent panel instance
     */
    public Elements(String name, String locator,String ByLocator, ParentPanel panel) {
        this.name = name;
        this.locator = locator;
        this.bylocator = getByLocator(ByLocator);
        this.parent = panel;
    }

    /**
     * Gets element's locator
     *
     * @return Locator of the element
     */
    public String getLocator() {
        return locator;
    }

    /**
     * Gets element's By locator
     *
     * @return By Locator of the elements
     */
    public By getByLocator(String ByLocator) {
    	String locator_body = locator;
        String type = ByLocator;
        switch (type) {
            case "css":
                return By.cssSelector(locator_body);
            case "id":
                return By.id(locator_body);
            case "link":
                return By.linkText(locator_body);
            case "xpath":
                return By.xpath(locator_body);
            case "text":
                return By.xpath(String.format("//*[contains(text(), '%s')]", locator_body));
            case "name":
                return By.name(locator_body);
            default:
                return By.xpath(locator);
        }
    }

    /**
     * Find webelement from web page. We use locator for this. Where locator -
     * start it with locator type "id=", "css=", "xpath=" and etc. Locator
     * without type is assigned to xpath
     *
     * @return List of WebElements
     */
    public List<WebElement> getWebElements() {
        if (logFindElementLocator) {
        }
        return getDriver().findElements(bylocator);
    }

    /**
     * Find webelement from web page. We use locator for this. Where locator -
     * start it with locator type "id=", "css=", "xpath=" and etc. Locator
     * without type is assigned to xpath
     *
     * @param seconds to wait until elements found.
     * @return List of WebElements
     */

    public List<WebElement> getWebElements(int seconds) {
        setTimeout(seconds);
        List<WebElement> webElementList = new Timer(seconds * 1000)
            .getResult(() -> getDriver().findElements(bylocator));
        setTimeout(TIMEOUT);
        return webElementList;
    }

    /**
     * Is at least one of elements exists (on the web page) or not?
     *
     * @return true if we can find at least one of elements on the web page, otherwise false
     */
    public boolean isExists() {
        return !getWebElements().isEmpty();
    }

    /**
     * Is at least one of elements exists (on the web page) or not?
     *
     * @param seconds to wait until elements become existed.
     * @return true if we can find at least one of elements on the web page, otherwise false
     */
    public boolean isExists(int seconds) {
        return !getWebElements(seconds).isEmpty();
    }

    /**
     * Get Element from Elements by index using last xpath tag for numerate
     *
     * @param elementIndex index of element
     * @return Element
     */
    public Element getElement(int elementIndex,String ByLocator) {
        return new Element<>(String.format("Element #%s", elementIndex), String.format("%s[%d]", getXPath(ByLocator).replace("//", "/descendant::"),elementIndex + 1),ByLocator, parent);
    }

    /**
     * Get Element from Elements by index using xpath tag for numerate
     *
     * @param elementIndex index of element
     * @param tag - xpath tag for numerate
     * @return Element
     */
    public Element getElement(int elementIndex,String ByLocator, String tag) {
        String xpath = getXPath(ByLocator);
        StringBuilder b = new StringBuilder(getXPath(ByLocator));
        b.replace(xpath.lastIndexOf(tag), xpath.lastIndexOf(tag)+1+String.valueOf(elementIndex+1).length(), String.format("%s[%d]", tag, elementIndex+1));
        return new Element<>(String.format("Element #%s", elementIndex), b.toString(),ByLocator, parent);
    }

    /**
     * Get First Visible Element from Elements
     *
     * @return Element
     */
    public Element getVisibleElement() {
        int elementIndex = 0;
        for (WebElement webEl : getWebElements()) {
            if (webEl.isDisplayed()) {
                return new Element<>(String.format("Element #%s", elementIndex), String.format("%s[%d]", getXPath(ByLocator).replace("//", "/descendant::"), elementIndex + 1),ByLocator, parent);
            }
            elementIndex++;
        }
        throw new NoSuchElementException("No visible elements available.");
    }

    //  Common functions

    /**
     * Gets count of WebElements
     *
     * @return count of WebElements
     */
    public int getWebElementsCount() {
        return (Integer) getWebElements().size();
    }

    /**
     * Gets count of WebElements
     *
     * @param seconds to wait until elements found.
     * @return count of WebElements
     */
    public int getWebElementsCount(int seconds) {
        return (Integer) getWebElements(seconds).size();
    }

    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * <code>null</code> if the property is not found.
     *
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get Parent Class Name
     *
     * @return Parent Canonical Class Name
     */
    protected String getParentClassName() {
        if (parent == null) {
            return "";
        }
        if (TestBaseWebDriver.simpleClassName) {
            return parent.getClass().getSimpleName();
        }
        return parent.getClass().getCanonicalName();
    }

    /**
     * Click on the WebElement by index
     *
     * @param elementIndex - index of the element in the List of WebElements
     * @return Parent instance
     */
    public ParentPanel clickBy(int elementIndex) {
        alwaysDoneAction(() -> getWebElements().get(elementIndex).click());
        return parent;
    }

    /**
     * Click on the WebElement by text
     *
     * @param elementText - text of the element in the List of WebElements
     * @return Parent instance
     */
    public ParentPanel clickByText(String elementText) {
        alwaysDoneAction(() -> getWebElements().get(getIndexByText(elementText)).click());
        return parent;
    }

    /**
     * Click on the WebElement by index until expectedElement is NOT DISPLAYED
     *
     * @param expectedElement - expected Element
     * @param elementIndex    - index of WebElement
     * @param tryCount        - number ot click attempts
     * @return Parent instance
     */
    public ParentPanel clickByWhileObjectNotDisplayed(int elementIndex, Element expectedElement, int tryCount) {
        int i = 0;
        do {
            getWebElements().get(elementIndex).click();
            i++;
            if (i >= tryCount) {
                break;
            }
        }
        while (!(expectedElement.isDisplayed()));
        return parent;
    }

    /**
     * Focus on the WebElement by index
     *
     * @param elementIndex    - index of WebElement
     * @return Parent instance
     */
    public ParentPanel focus(int elementIndex) {
        Dimension size = getWebElements().get(elementIndex).getSize(); //for scroll to object
        Actions builder = new Actions(WebDriverWrapper.getDriver());
        org.openqa.selenium.interactions.Action focus = builder.moveToElement(getWebElements().get(elementIndex), size.width / 2, size.height / 2).build();
        focus.perform();
        return parent;
    }

    /**
     * Mouse Over on the the element by index.
     *
     * @param elementIndex    - index of WebElement
     * @return Parent instance
     */
    public ParentPanel mouseOver(int elementIndex) {
        getWebElements().get(elementIndex).getSize(); //for scroll to object
        Actions builder = new Actions(WebDriverWrapper.getDriver());
        builder.moveToElement(getWebElements().get(elementIndex)).build().perform();
        return parent;
    }

    private JavascriptExecutor jsExecutor() { return (JavascriptExecutor) getDriver(); }
    /**
     * Click on the WebElement by JS
     *
     * @param elementIndex    - index of WebElement
     * @return Parent instance
     */
    public ParentPanel clickByJS(int elementIndex) {
        jsExecutor().executeScript("arguments[0].click();", getWebElements().get(elementIndex));
        return parent;
    }

    /**
     * Click on the Element by index until expectedElements is NOT DISPLAYED
     *
     * @param expectedElements - expected Elements
     * @param elementIndex     - index of WebElement
     * @param tryCount        - number ot click attempts
     * @return Parent instance
     */
    public ParentPanel clickByWhileObjectNotDisplayed(int elementIndex, Elements expectedElements, int tryCount) {
        int i = 0;
        do {
            getWebElements().get(elementIndex).click();
            sleepTight(1000);
            i++;
            if (i >= tryCount) {
                break;
            }
        }
        while (!(expectedElements.isVisibleWebElementAvailable()));
        return parent;
    }


    /**
     * Get WebElement by index
     *
     * @param elementIndex - index of WebElement
     * @return WebElement
     */
    public WebElement getWebElement(int elementIndex) {
        return getWebElements().get(elementIndex);
    }

    /**
     * Get First visible WebElement
     *
     * @return WebElement
     */
    public WebElement getVisibleWebElement() {
        WebElement firstElement = first(
                getWebElements(),
                WebElement::isDisplayed);
        if (firstElement != null)
            return firstElement;
        throw new NoSuchElementException("No visible elements available.");
    }

    /**
     * Is First visible WebElement available
     *
     * @return Whether or not first visible WebElement available(in the Elements)
     */
    public boolean isVisibleWebElementAvailable() {
        return first(getWebElements(), WebElement::isDisplayed) != null;
    }


    /**
     * Get WebElement by text attribute
     *
     * @param sText - text() attribute of the WebElement in the List of WebElements
     * @return WebElement
     */
    public WebElement getWebElementByText(String sText) {
        WebElement element = first(getWebElements(),
                el -> el.getText().equals(sText));
        if (element != null)
            return element;
        throw new NoSuchElementException(String.format("Cannot find element with text '%s'. ", sText));
    }

    /**
     * Get WebElement by text attribute contains
     *
     * @param sText - text() attribute of the WebElement in the List of WebElements
     * @return WebElement
     */
    public WebElement getWebElementByTextContains(String sText) {
        WebElement element = first(getWebElements(),
                el -> el.getText().contains(sText));
        if (element != null)
            return element;
        throw new NoSuchElementException(String.format("Cannot find element with text contains '%s'. ", sText));
    }

    /**
     * Get WebElement index by text of current attribute
     *
     * @param sText      - value of attribute of the element in the List of WebElements
     * @param sAttribute - type of attribute of the element in the List of WebElements
     * @return index
     */
    public int getIndexByAttribute(String sText, String sAttribute) {
        int index = firstIndex(getWebElements(),
                el -> el.getAttribute(sAttribute).equals(sText));
        if (index > -1)
            return index;
        throw new NoSuchElementException(String.format("Cannot find element with text '%s' by attribute '%s'. ", sText, sAttribute));
    }

    /**
     * Get WebElement index by text attribute
     *
     * @param sText - text() attribute of the element in the List of WebElements
     * @return index
     */
    public int getIndexByText(String sText) {
        int index = firstIndex(getWebElements(),
                el -> el.getText().equals(sText));
        if (index > -1)
            return index;
        throw new NoSuchElementException(String.format("Cannot find element with text '%s'. ", sText));
    }

    /**
     * Get WebElement index by text attribute contains
     *
     * @param sText - text() attribute of the element in the List of WebElements
     * @return index
     */
    public int getIndexByTextContains(String sText) {
        int index = firstIndex(getWebElements(),
                el -> el.getText().contains(sText));
        if (index > -1)
            return index;
        throw new NoSuchElementException(String.format("Cannot find element with text contains '%s'. ", sText));
    }

    /**
     * Get WebElement index by text attribute starts with
     *
     * @param sText - text() attribute of the element in the List of WebElements
     * @return index of WebElement
     */
    public int getIndexByTextStartsWith(String sText) {
        int index = firstIndex(getWebElements(),
                el -> el.getText().startsWith(sText));
        if (index > -1)
            return index;
        throw new NoSuchElementException(String.format("Cannot find element with text starts with '%s'. ", sText));

    }

    /**
     * Get text of WebElement by index
     *
     * @param elementIndex - index of the element in the List of WebElements
     *
     * @return text of WebElement
     */
    public String getText(int elementIndex) {
        return getWebElements().get(elementIndex).getText();
    }

    /**
     * Get List of WebElement text
     *
     * @return ArrayList
     */
    public List<String> getTextList() {
        return (List<String>) select(getWebElements(), WebElement::getText);
    }

    /**
     * Get List of WebElement attributes
     *
     * @param attributeName - name of attribute for getting value
     * @return List of WebElement attributes
     */
    public List<String> getAttribureList(String attributeName) {
        return (List<String>) select(getWebElements(),
                el -> el.getAttribute(attributeName));
    }

    /**
     * Get full xpath string
     *
     * @return Xpath of the element
     */
    public String getXPath(String ByLocator) {
        String sLocator = locator;
        String sType = ByLocator;
        switch (sType) {
            case "css":
                return "";
            case "id":
                return String.format("//*[@id=\"%s\"]", sLocator);
            case "link":
                return String.format("//*[@link=\"%s\"]", sLocator);
            case "xpath":
                return String.format("%s", sLocator);
            case "text":
                return String.format("//*[contains(text(), '%s')]", sLocator);
            case "name":
                return String.format("//*[@name=\"%s\"]", sLocator);
            default:
                return "";
        }
    }

    /**
     * Replace each substring of this string "$VALUE" to [value] in [str]
     *
     * @param str   - input string for replacement
     * @param value -The replacement sequence of char values
     * @return The resulting string
     */
    public String insertValue(String str, String value) {
        return str.replace("$VALUE", value);
    }

    /**
     * Get Control Name
     *
     * @return Get Element's Group Name
     */
    public String getName() {
        return name;
    }

    /**
     * Wait until first element is visible.
     *
     * @param timeoutSec seconds to wait until element become visible
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForFirstVisibleElement(final int timeoutSec, final boolean checkCondition) {
        boolean isVisible;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(bylocator));
            isVisible = true;
        } catch (TimeoutException e) {
            isVisible = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
        }
        return parent;
    }

    /**
     * Wait until first element is visible
     *
     * @return Parent instance
     */
    public ParentPanel waitForFirstVisibleElement() {
        return waitForFirstVisibleElement(TIMEOUT, CHECKCONDITION);
    }
    /**
     * Wait until first element is visible
     * @param timeoutSec seconds to wait until all elements exist
     * @return Parent instance
     */
    public ParentPanel waitForFirstVisibleElement(final int timeoutSec) {
        return waitForFirstVisibleElement(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until all elements exist.
     *
     * @param timeoutSec seconds to wait until all elements exist
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsExist(final int timeoutSec, final boolean checkCondition) {
        boolean exist;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(bylocator));
            exist = true;
        } catch (TimeoutException e) {
            exist = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
        }
        return parent;
    }

    /**
     * Wait until all elements exist
     *
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsExist() {
        return waitForAllElementsExist(TIMEOUT, CHECKCONDITION);
    }
    /**
     * Wait until all elements exist
     * @param timeoutSec seconds to wait until all elements become Not Visible
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsExist(final int timeoutSec) {
        return waitForAllElementsExist(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until all elements is invisible
     *
     * @param timeoutSec seconds to wait until all elements become Not Visible
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsNotVisible(final int timeoutSec, final boolean checkCondition) {
        boolean isNotVisible;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElements(getWebElements())));
            isNotVisible = true;
        } catch (TimeoutException e){
            isNotVisible = false;
        } catch (NoSuchElementException elementException) {
            isNotVisible = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
        }
        return parent;
    }

    /**
     * Wait until all elements is invisible
     *
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsNotVisible() {
        return waitForAllElementsNotVisible(TIMEOUT, CHECKCONDITION);
    }
    /**
     * Wait until all elements is invisible
     * @param timeoutSec seconds to wait until all elements become Visible
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsNotVisible(final int timeoutSec) {
        return waitForAllElementsNotVisible(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until all elements is visible
     *
     * @param timeoutSec seconds to wait until all elements become Visible
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsVisible(final int timeoutSec, final boolean checkCondition) {
        boolean isVisible;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        setTimeout(1);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(getWebElements()));
            isVisible = true;
        } catch (TimeoutException e) {
            isVisible = false;
        }
        setTimeout(TIMEOUT);
        if (checkCondition){
        }
        return parent;
    }

    /**
     * Wait until all elements is visible
     *
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsVisible() {
        return waitForAllElementsVisible(TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until all elements is visible
     * @param timeoutSec seconds to wait until elements is changed list of text
     * @return Parent instance
     */
    public ParentPanel waitForAllElementsVisible(final int timeoutSec) {
        return waitForAllElementsVisible(timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until elements is changed list of text
     *
     * @param list expected list of text
     * @param timeoutSec seconds to wait until elements is changed list of text
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForElementsTextChanged(final List<String> list, final int timeoutSec, final boolean checkCondition) {
        boolean isChanged;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            isChanged = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return !getTextList().containsAll(list);
                        }
                    }
            );
        } catch (TimeoutException e) {
            isChanged = false;
        }
        if (checkCondition){
        }
        return parent;
    }

    /**
     * Wait until elements is changed list of text
     *
     * @param list expected list of text
     * @return Parent instance
     */
    public ParentPanel waitForElementsTextChanged(final List<String> list) {
        return waitForElementsTextChanged(list, TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until elements is changed list of text
     *
     * @param list expected list of text
     * @param timeoutSec seconds to wait until elements count is changed
     * @return Parent instance
     */
    public ParentPanel waitForElementsTextChanged(final List<String> list, final int timeoutSec) {
        return waitForElementsTextChanged(list, timeoutSec, CHECKCONDITION);
    }

    /**
     * Wait until number of elements is changed
     *
     * @param count      - source number of element
     * @param timeoutSec seconds to wait until elements count is changed
     * @param checkCondition log assert for expected conditions.
     * @return Parent instance
     */
    public ParentPanel waitForNumberOfElementsChanged(final int count, final int timeoutSec, final boolean checkCondition) {
        boolean isChanged;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeoutSec)
                .ignoring(StaleElementReferenceException.class);
        try {
            getWebElementsCount();
            isChanged = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            return getDriver().findElements(bylocator).size() != count;
                        }
                    }
            );
        } catch (TimeoutException e) {
            isChanged = false;
        }
        if (checkCondition){
        }
        return parent;
    }

    /**
     * Wait until number of elements is changed
     *
     * @param count      - source number of element
     * @return Parent instance
     */
    public ParentPanel waitForNumberOfElementsChanged(final int count) {
        return waitForNumberOfElementsChanged(count, TIMEOUT, CHECKCONDITION);
    }

    /**
     * Wait until number of elements is changed
     *
     * @param count      - source number of element
     * @param timeoutSec seconds to wait until elements count is changed
     * @return Parent instance
     */
    public ParentPanel waitForNumberOfElementsChanged(final int count, final int timeoutSec) {
        return waitForNumberOfElementsChanged(count, timeoutSec, CHECKCONDITION);
    }

}

