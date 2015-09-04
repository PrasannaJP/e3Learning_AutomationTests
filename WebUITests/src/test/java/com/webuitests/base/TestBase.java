package com.webuitests.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.webuiframework.oua.uitests.utils.TestBaseWebDriver;



public class TestBase extends TestBaseWebDriver {
	public Properties testproperties;
	String Propertiesfilename = "config.properties";
	public InputStream inputstream;
	public static String BaseURL = "";
	
	public void Initalise(){
		
		testproperties = new Properties();
		InputStream inputstream = getClass().getClassLoader().getResourceAsStream(Propertiesfilename);
		if(inputstream != null){
			try {
				testproperties.load(inputstream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.setProperty("webdriver.chrome.driver", testproperties.getProperty("webuitests.chromedriver"));
        System.setProperty("webdriver.ie.driver", testproperties.getProperty("webuitests.iedriver"));
        setBrowserType(testproperties.getProperty("webuitests.browser"));
        System.out.println(testproperties.getProperty("webuitests.browser"));
		initWebDriver();
		takePassedScreenshot(Boolean.parseBoolean(testproperties.getProperty("webuitests.takePassedScreenshot")));
		BaseURL = testproperties.getProperty("webuitests.URL");
		
	}
	

}
