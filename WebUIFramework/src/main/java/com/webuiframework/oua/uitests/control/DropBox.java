package com.webuiframework.oua.uitests.control;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.webuiframework.oua.uitests.utils.LinqUtils;

import static com.webuiframework.oua.uitests.utils.Timer.alwaysDoneAction;
import static com.webuiframework.oua.uitests.utils.Timer.getResultAction;

import java.util.List;

public class DropBox<ParentPanel> extends Element<ParentPanel> {

    //constructor

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - DropBox name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current dropbox
     */
    public DropBox(String name, String locator,String ByLocator, ParentPanel parentPanel) {
        super(name, locator,ByLocator, parentPanel);
    }

    private Select select() { return new Select(getWebElement()); }

    /**
     * Select by the visible option text
     *
     * @param sItem - visible option text
     * @return Parent Panel instance
     */
    public ParentPanel selectByText(String sItem) {
        alwaysDoneAction(() -> select().selectByVisibleText(sItem));
        return super.parent;
    }

    /**
     * Select all options that have a value matching the argument. That is, when given "foo" this
     * would select an option like:
     *
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     *
     * @param value The value to match against
     * @return Parent Panel instance
     */
    public ParentPanel selectByValue(String value) {
        alwaysDoneAction(() -> select().selectByValue(value));
        return super.parent;
    }

    /**
     * Select the option at the given index
     *
     * @param index - index The option at this index will be selected
     * @return Parent Panel instance
     */
    public ParentPanel selectByIndex(int index) {
        alwaysDoneAction(() -> select().selectByIndex(index));
        return super.parent;
    }

    /**
     * Select by the visible option text(contains)
     *
     * @param sItem - visible option text(contains)
     * @return Parent Panel instance
     */
    public ParentPanel selectByTextContains(String sItem) {
        Select select = select();
        int firstIndex = getResultAction(() -> LinqUtils.firstIndex(
                select.getOptions(),
                option -> option.getText().contains(sItem)));
        if (firstIndex > -1) {
            select.selectByIndex(firstIndex);
            return super.parent;
        }
        throw new NoSuchElementException(String.format("Cannot find item contains this text '%s'", sItem));
    }

    /**
     * Gets count of options in DropBox
     *
     * @return count of options in DropBox
     */
    public int getOptionsCount() {
        return select().getOptions().size();
    }

    /**
     * Gets all options
     *
     * @return All options belonging to this select tag
     */
    public String[] getAllOptions() {
        return getResultAction(() -> (String[])LinqUtils.select(
                select().getOptions(),
                WebElement::getText).toArray());
    }

    /**
     * Gets first selected option
     *
     * @return The first selected option in this select tag(or the currently selected option in a
     * normal select)
     */
    public String getFirstSelectedOption() {
        return select().getFirstSelectedOption().getText();
    }

    /**
     * Gets All selected options
     *
     * @return All selected options belonging to this select tag
     */
    public String[] getAllSelectedOptions() {
        return getResultAction(() -> (String[])LinqUtils.select(
                    select().getAllSelectedOptions(),
                    WebElement::getText).toArray());
    }


    /**
     * Undo selection by option text of Select
     *
     * (That is Deselect all options that display text matching the argument)
     *
     * @param sItem - visible option text
     * @return Parent Panel instance
     */
    public ParentPanel deSelectByText(String sItem) {
        alwaysDoneAction(() -> select().deselectByVisibleText(sItem));
        return super.parent;
    }

    /**
     * Undo the selection for all options. This is only valid when the SELECT supports multiple selections.
     *
     * Check if the Select can be multiple selected
     * boolean isMultiple = select.isMultiple();
     *
     * @return Parent Panel instance
     */
    public ParentPanel deselectAll() {
        alwaysDoneAction(() -> select().deselectAll());
        return super.parent;
    }

    /**
     * Check if the Select can be multiple selected
     *
     * @return Whether this select element support selecting multiple options at the same time? This
     * is done by checking the value of the "multiple" attribute.
     */
    public boolean isMultiple() {
        return select().isMultiple();
    }

    /**
     * Check if value present into Dropbox
     *
     * @param value - checked value
     * @return true if value exists
     */
    public boolean isOptionExist(String value) {
        return LinqUtils.first(
                getAllOptions(),
                option -> option.equals(value)) != null;
    }

}

