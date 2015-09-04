package com.webuiframework.oua.uitests.utils;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public final class ScreenShotMaker {

    private ScreenShotMaker(){}

    private static String path = "\\";
    private static String dir = "\\";
    private static boolean hasTake = true;

    /**
     * Set ScreenShotMaker path
     *
     * @param sPath - to take screenshots
     */
    public static void setPath(String sPath) {
        ScreenShotMaker.dir = "." + sPath.substring(sPath.lastIndexOf("/")) + "/";
        ScreenShotMaker.path = sPath + "/";
    }

    /**
     * Set hasTake variable value
     *
     * @param value - to set hasTake variable
     */
    public static void hasTake(boolean value) {
        hasTake = value;
    }

    /**
     * Get hasTake variable value
     *
     * @return hasTake
     */
    public static boolean getHasScreenshot() {
        return hasTake;
    }

    private static boolean isDirectoryCorrect() {
        return !path.equals("\\");
    }

    /**
     * create screenshot on remote machine
     *
     * @param id - name output png file
     * @return full path
     */
    public static String takeScreenshotRemote(String id) {
        String sId = id;
        String base64Screenshot;
        if (hasTake) {
            if (isDirectoryCorrect()) {
                String name = String.format("%s.png", id + "_" + DateUtil.now(new SimpleDateFormat("HH_mm_ss-sss").toPattern()));
                try {
                    TakesScreenshot tsDriver;
                    tsDriver = (TakesScreenshot) WebDriverWrapper.getDriver();
                    base64Screenshot = tsDriver.getScreenshotAs(OutputType.BASE64);

                    byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
                    File file = new File(ScreenShotMaker.path + name);
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(decodedScreenshot);
                    }
                    if (TestBaseWebDriver.reportportal) {
                    }
                } catch (WebDriverException | IOException e) {
                    sId += String.format("  [%s when making screenshot(webdriver: %s)]", e.toString(), WebDriverWrapper.getDriver());
                }
                return dir + name + "|" + sId;
            }
            return String.format("Incorrect directory screenshot directory: %s", path);
        } else {
            return id;
        }
    }

    
}
