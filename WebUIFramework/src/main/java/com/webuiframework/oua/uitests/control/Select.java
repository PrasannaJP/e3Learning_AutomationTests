package com.webuiframework.oua.uitests.control;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.webuiframework.oua.uitests.utils.LinqUtils;
import com.webuiframework.oua.uitests.utils.WebDriverWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.webuiframework.oua.uitests.utils.LinqUtils.foreach;
import static com.webuiframework.oua.uitests.utils.Timer.alwaysDoneAction;

public class Select<ParentPanel> extends Element<ParentPanel> {

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Button Name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current button
     */
    public Select(String name, String locator,String ByLocator, ParentPanel parentPanel) {
        super(name, locator,ByLocator, parentPanel);
    }

    private org.openqa.selenium.support.ui.Select select() {
        return new org.openqa.selenium.support.ui.Select(getWebElement()); }
    /**
     * Deselect all items.
     *
     * @return Parent instance
     */
    public ParentPanel deselect() {
        alwaysDoneAction(() -> select().deselectAll());
        return this.parent;
    }

    /**
     * Select item by index.
     *
     * @param index of item
     * @return Parent instance
     */
    public ParentPanel select(int index) {
        alwaysDoneAction(() -> {
            org.openqa.selenium.support.ui.Select select = select();
            select.deselectAll();
            select.selectByIndex(index);
        });
        return this.parent;
    }

    /**
     * Select item by value.
     *
     * @param value of item
     * @return Parent instance
     */
    public ParentPanel select(String value) {
        alwaysDoneAction(() -> {
            org.openqa.selenium.support.ui.Select select = select();
            select.deselectAll();
            select.selectByValue(value);
        });
        return this.parent;
    }

    /**
     * Select items by value.
     *
     * @param values of items
     * @return Parent instance
     */
    public ParentPanel select(String[] values) {
        org.openqa.selenium.support.ui.Select select = select();
        alwaysDoneAction(() -> {
            select.deselectAll();
            for (String value : values) {
                select.selectByValue(value);
            }
        });
        return this.parent;
    }

    /**
     * Select items by indexes.
     *
     * @param ids - indexes of items
     * @return Parent instance
     */
    public ParentPanel select(int[] ids) {
        org.openqa.selenium.support.ui.Select select = select();
        alwaysDoneAction(() -> {
            select.deselectAll();
            for (int id : ids) {
                select.selectByIndex(id);
            }
        });
        return this.parent;
    }

    /**
     * Get list of selected items.
     *
     * @return Parent instance
     */
    public List<String> getSelectedItems() {
        return (List<String>) LinqUtils.select(
                select().getAllSelectedOptions(),
                WebElement::getText);
    }

    /**
     * Get list of items.
     *
     * @return Parent instance
     */
    public List<String> getItems() {
        return (List<String>) LinqUtils.select(
                select().getOptions(),
                WebElement::getText);
    }

    /**
     * Wait until item is selected by value.
     *
     * @param value - item text
     * @return Parent Panel instance
     */
    public ParentPanel waitForItemAndSelect(final String value) {
        boolean isSelected;
        long start = System.currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(WebDriverWrapper.getDriver(), WebDriverWrapper.TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        try {
            isSelected = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            try {
                                org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
                                select.selectByValue(value);
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                    }
            );
        }catch (TimeoutException e) {
            isSelected = false;
        }
        return parent;
    }

}

