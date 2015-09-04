package com.webuiframework.oua.uitests.control;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Input<ParentPanel> extends Element<ParentPanel> {

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Input name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Parent instance
     */
    public Input(String name, String locator,String ByLocator, ParentPanel parentPanel) {
        super(name, locator,ByLocator, parentPanel);
    }

    /**
     * Gets the value of an input field
     *
     * @return the value of an input field
     */
    public String getValue() {
        return super.getAttribute("value");
    }


    /**
     * Type text to the Input field
     *
     * @param text - text for Input field
     * @return Parent instance
     */
    public ParentPanel setText(String text) {
        WebElement webEl = getWebElement();
        webEl.click();
        webEl.clear();
        webEl.click();
        super.sendKeys(text);
        return super.parent;
    }

    /**
     * Type text to the Input field with secure log.
     *
     * @param text - text for Input field
     * @return Parent instance
     */
    public ParentPanel setTextSecure(String text) {
        WebElement webEl = getWebElement();
        webEl.click();
        webEl.clear();
        webEl.click();
        super.sendKeysSecure(text);
        return super.parent;
    }

    /**
     * Clear the value from the Input field
     *
     * @return Parent instance
     */
    public ParentPanel clear() {
        return super.parent;
    }

    /**
     * Use this method to simulate typing into an element, which may set its value.
     *
     * @param keysToSend - CharSequence to send
     * @return Parent instance
     */
    public ParentPanel sendKeys(CharSequence... keysToSend) {
        return super.sendKeys(keysToSend);
    }

    /**
     * Use this method to simulate enter key press into an element.
     *
     * @return Parent instance
     */
    public ParentPanel pressEnter() {
        return super.sendKeys(Keys.ENTER);
    }

    /**
     * Use this method to simulate send keys to the element.
     *
     * @param sendKeys - e.g. sendKeys(Keys.ARROW_DOWN)
     * @return Parent instance
     */
    public ParentPanel sendKeys(Keys sendKeys) {
        return super.sendKeys(sendKeys);
    }

}

