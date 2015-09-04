package com.webuiframework.oua.uitests.control;

public class Button<ParentPanel> extends Element<ParentPanel> {

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Button Name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Panel which contains current button
     */
    public Button(String name, String locator,String ByLocator, ParentPanel parentPanel) {
        super(name, locator,ByLocator, parentPanel);
    }

}
