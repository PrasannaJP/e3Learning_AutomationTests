package com.webuiframework.oua.uitests.control;

public class Image<ParentPanel> extends Element<ParentPanel> {

    //constructor

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Image name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Parent instance
     */
    public Image(String name, String locator,String ByLocator, ParentPanel parentPanel) {
        super(name, locator,ByLocator, parentPanel);
    }

}
