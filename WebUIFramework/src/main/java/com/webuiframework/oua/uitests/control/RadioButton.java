package com.webuiframework.oua.uitests.control;

import org.openqa.selenium.WebElement;

import com.webuiframework.oua.uitests.utils.Timer;

public class RadioButton<ParentPanel> extends Element<ParentPanel> {

    //constructor

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - RadioButton name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Parent instance
     */
    public RadioButton(String name, String locator,String ByLocator,ParentPanel parentPanel) {
        super(name, locator,ByLocator, parentPanel);
    }

    /**
     * Is this RadioButton is checked.
     *
     * @return True if the element is currently checked, false otherwise.
     */
    public boolean isChecked() {
        return getWebElement().isSelected();
    }

    /**
     * Check if this RadioButton is not checked eat, do nothing otherwise.
     *
     * @return Parent instance
     */
    public ParentPanel check() {
        Timer.alwaysDoneAction(() -> {
            WebElement webEl = getWebElement();
            if (!webEl.isSelected()) {
                webEl.click();
            }
        });
        return super.parent;
    }

}

