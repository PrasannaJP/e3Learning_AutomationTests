package com.webuiframework.oua.uitests.utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TestBaseWebDriver {

    public static WebDriver driver;
    public static String browserType = "firefox";
    public static boolean grid = false;
    public static String gridHub = "http://localhost:4444/wd/hub";
    public static final String screenshotDirectory = "target/surefire-reports/html/screenshots";
    public static boolean simpleClassName = true;
    public static boolean takePassedScreenshot = false;
    public static boolean logFindElementLocator = true;
    public static boolean allure = false;
    public static boolean reportportal = false;

    /**
     * Set Browser for WebDriver.
     *
     * @param browser - browser to initialization.
     */
    public static void setBrowserType(String browser) {
        browserType = browser;
    }

    /**
     * Use Selenium Grid.
     *
     * @param useGrid - true for using selenium grid.
     */
    public static void useGrid(boolean useGrid) {
        grid = useGrid;
    }

    /**
     * Set Selenium Grid Hub.
     *
     * @param hub - selenium grid hub url.
     */
    public static void setGridHub(String hub) {
        gridHub = hub;
    }

    /**
     * Take screenshots at passed assertion.
     *
     * @param takeScreenshot - true for taking screenshots at passed assertion.
     */
    public static void takePassedScreenshot(boolean takeScreenshot) {
        takePassedScreenshot = takeScreenshot;
    }

    /**
     * Use simple or canonical class name.
     *
     * @param simple - true for using simple class name .
     */
    public static void useSimpleClassName(boolean simple) {
        simpleClassName = simple;
    }

    /**
     * WebDriver initialization.
     */
    public void initWebDriver() {
        if (grid) {
            initWebDriverGrid();
        } else {
            switch (browserType) {
                case "safari":
                    WebDriverWrapper.initSafariDriver();
                    break;
                case "chrome":
                    WebDriverWrapper.initChromeDriver();
                    break;
                case "firefox":
                    WebDriverWrapper.initFirefoxDriver();
                    break;
                case "ie":
                case "iexplorer":
                case "internetexplorer":
                    WebDriverWrapper.initInternetExplorerDriver();
                    break;
            }
            driver = WebDriverWrapper.getDriver();
        }
        initScreenshotDirectory();
    }

    /**
     * WebDriver Grid initialization.
     */
    public void initWebDriverGrid() {

        Capabilities capabilities = null;
        switch (browserType) {
            case "safari":
                capabilities = DesiredCapabilities.internetExplorer();
                break;
            case "chrome":
                capabilities = DesiredCapabilities.chrome();
                break;
            case "firefox":
                capabilities = DesiredCapabilities.firefox();
                break;
            case "ie":
            case "iexplorer":
            case "internetexplorer":
                capabilities = DesiredCapabilities.internetExplorer();
                break;
        }
        try {
            WebDriverWrapper.initRemoteWebDriver(gridHub, capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = WebDriverWrapper.getDriver();

    }

    /**
     * Directory for Screenshot initialization.
     */
    public void initScreenshotDirectory() {
        MakeDir.makeDir(screenshotDirectory);
        ScreenShotMaker.setPath(screenshotDirectory);
    }
}

   