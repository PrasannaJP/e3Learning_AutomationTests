package com.webuiframework.oua.uitests.control;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.webuiframework.oua.uitests.utils.LinqUtils.firstIndex;
import static com.webuiframework.oua.uitests.utils.Timer.alwaysDoneAction;
import static com.webuiframework.oua.uitests.utils.Timer.getResultAction;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

import java.util.ArrayList;
import java.util.List;

import com.webuiframework.oua.uitests.*;
import com.webuiframework.oua.uitests.utils.LinqUtils;
import com.webuiframework.oua.uitests.utils.WebDriverWrapper;

public class ComboBox<ParentPanel> extends Input<ParentPanel> {

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Button Name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current button
     */
    public ComboBox(String name, String locator,String ByLocator, ParentPanel parentPanel) {
        super(name, locator,ByLocator, parentPanel);
    }

    private Select select() { return new Select(getWebElement()); }

    /**
     * Select by Index.
     *
     * @param index - option index
     * @return Parent Panel instance
     */
    public ParentPanel select(int index) {
        alwaysDoneAction(() -> select().selectByIndex(index));
        return this.parent;
    }

    /**
     * Select by option text.
     *
     * @param value - option text
     * @return Parent Panel instance
     */
    public ParentPanel select(String value) {
        alwaysDoneAction(() -> select().selectByValue(value));
        return this.parent;
    }

    /**
     * Select by the visible option text(contains)
     *
     * @param item - visible option text(contains)
     * @return Parent Panel instance
     */
    public ParentPanel selectByTextContains(String item) {
        Select select = select();
        int firstIndex = getResultAction(() -> firstIndex(
                select.getOptions(),
                option -> option.getText().contains(item)));
        if (firstIndex > -1) {
            select.selectByIndex(firstIndex);
            return super.parent; }
        throw new NoSuchElementException(format("Cannot find item contains this text '%s'", item));
    }

    /**
     * Get first selected option text.
     *
     * @return First Selected option.
     */
    public String getFirstSelectedItem() {
        return getResultAction(() -> select().getFirstSelectedOption().getText());
    }

    /**
     * Get all selected options text.
     *
     * @return All Selected options.
     */
    public List<String> getSelectedItem() {
        return getResultAction(() -> (List<String>)LinqUtils.select(
                select().getAllSelectedOptions(),
                WebElement::getText));
    }

    /**
     * Get all option text.
     *
     * @return List of all options.
     */
    public List<String> getItems() {
        return getResultAction(() -> (List<String>)LinqUtils.select(
                select().getOptions(),
                WebElement::getText));
    }

    /**
     * Wait until option is selected by value.
     *
     * @param value - option text
     * @return Parent Panel instance
     */
    public ParentPanel waitForItemAndSelect(final String value) {
        boolean isSelected;
        long start = currentTimeMillis() / 1000;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(WebDriverWrapper.getDriver(), WebDriverWrapper.TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        try {
            isSelected = wait.until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            try {
                                Select select = new Select(getWebElement());
                                select.selectByValue(value);
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                    }
            );
        }catch (TimeoutException e){
            isSelected = false;
        }
        return parent;
    }

}

