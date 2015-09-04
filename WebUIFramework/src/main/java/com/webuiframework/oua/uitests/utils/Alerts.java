package com.webuiframework.oua.uitests.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.webuiframework.oua.uitests.utils.WebDriverWrapper.TIMEOUT;

public class Alerts {

    private Alerts(){}

    /**
     * Get alert.
     *
     * @param timeoutSec to wait until alert is exists.
     * @return Alert
     */
    public static Alert getAlert(int timeoutSec) {
        WebDriverWait wait = new WebDriverWait(WebDriverWrapper.getDriver(), timeoutSec);
        wait.until(ExpectedConditions.alertIsPresent());
        return WebDriverWrapper.getDriver().switchTo().alert();
    }

    /**
     * Get alert.
     *
     * @return Alert
     */
    public static Alert getAlert() {
        return getAlert(TIMEOUT);
    }

    /**
     * Get alert text.
     *
     * @return Alert text.
     */
    public static String getAlertText() {
        return getAlert().getText();
    }

    /**
     * Accept alert.
     *
     */
    public static void acceptAlert() {
        Alert alert = getAlert();
        alert.getText();
        alert.accept();
    }

    /**
     * Dismiss alert.
     */
    public static void dismissAlert() {
        Alert alert = getAlert();
        alert.dismiss();
    }

    /**
     * Find alert from web page.
     *
     * @param timeoutSec to wait until alert is exists.
     * @return true if alert is presents at web page, otherwise false
     */
    public static boolean findAlert(int timeoutSec) {
        WebDriverWait wait = new WebDriverWait(WebDriverWrapper.getDriver(), timeoutSec);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Find alert from web page.
     *
     * @return true if alert is presents on the web page, otherwise false
     */
    public static boolean findAlert() {
        return findAlert(TIMEOUT);
    }

    /**
     * Check that alert appeared at web page.
     *
     * @param timeoutSec to wait until alert is exists.
     * @param checkCondition log assert for expected conditions.
     */
    public static void waitForAlert(int timeoutSec, boolean checkCondition) {
        long start = System.currentTimeMillis() / 1000;
        boolean exists = findAlert(timeoutSec);
        if (checkCondition) {
        }
    }

    /**
     * Check that alert appeared at web page.
     */
    public static void waitForAlert() {
        waitForAlert(TIMEOUT, false);
    }

    /**
     * Confirm alert by JS.
     */
    public static void jConfirmAlerts() {
        if (findAlert(0)){
            WebDriverWrapper.executeScript("window.confirm = function(msg) { return true; }");
        }
    }

    /**
     * Wait for alert exists and accept.
     *
     * @param timeoutSec to wait until alert is exists.
     */
    public static void findAndAcceptAlert(int timeoutSec){
        if (findAlert(timeoutSec)){
            acceptAlert();
        }
    }
    /**
     * Wait for alert exists and accept.
     */
    public static void findAndAcceptAlert(){
        findAndAcceptAlert(TIMEOUT);
    }
    /**
     * Wait for alert exists and dismiss.
     *
     * @param timeoutSec to wait until alert is exists.
     */
    public static void findAndDismissAlert(int timeoutSec){
        if (findAlert(timeoutSec)){
            dismissAlert();
        }
    }
    /**
     * Wait for alert exists and dismiss.
     */
    public static void findAndDismissAlert(){
        findAndDismissAlert(TIMEOUT);
    }


}
