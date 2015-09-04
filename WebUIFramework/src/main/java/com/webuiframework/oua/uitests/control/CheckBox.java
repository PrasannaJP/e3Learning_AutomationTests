package com.webuiframework.oua.uitests.control;

import com.webuiframework.oua.uitests.utils.Timer;

import org.openqa.selenium.WebElement;

import static com.webuiframework.oua.uitests.utils.Timer.alwaysDoneAction;

public class CheckBox<ParentPanel> extends Element<ParentPanel> {

    //constructor

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Checkbox name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current checkbox
     */
    public CheckBox(String name, String locator,String ByLocator, ParentPanel parentPanel) {
        super(name, locator,ByLocator,parentPanel);
    }

    /**
     * @return True if the element is currently checked, false otherwise.
     */
    public boolean isChecked() {
        return (Boolean) getWebElement().isSelected();
    }

    /**
     * Check if the element is not checked eat, do nothing otherwise.
     *
     * @return Parent Panel instance
     */
    public ParentPanel check() {
        alwaysDoneAction(() -> {
            WebElement webEl = getWebElement();
            if (!webEl.isSelected())
                webEl.click();
        });
        return super.parent;
    }

    /**
     * Uncheck if the element is checked, do nothing otherwise.
     *
     * @return Parent Panel instance
     */
    public ParentPanel uncheck() {
        alwaysDoneAction(() -> {
            WebElement webEl = getWebElement();
            if (webEl.isSelected()) {
                webEl.click();
            }
        });
        return super.parent;
    }

}

