package com.webuitests.cuketests;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.TakesScreenshot;

import com.webuiframework.oua.uitests.utils.ScreenShotMaker;
import com.webuiframework.oua.uitests.utils.WebDriverWrapper;
import com.webuitests.base.TestBase;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GoogleSearch extends TestBase {
	
	@Before
	public void setUp(Scenario scenario){
		Initalise();
	}
	
	@After
	public void teardown(Scenario scenario){
		try{
			if(scenario.isFailed()){
				ScreenShotMaker.takeScreenshotRemote(scenario.getName());
			}
		}
		finally{
			WebDriverWrapper.quit();
		}
	}
	
	@Given("^Open default website$")
	public void open_default_website() throws Throwable {
        WebDriverWrapper.open(BaseURL);
	}

	@Then("^I see default page$")
	public void Open_DefaultPage() throws Throwable {
		Assert.assertTrue(GoogleSearchPage.get().logo.isDisplayed());
	    //Assert.assertTrue("check search button is visible",GoogleSearchPage.get().searchBtn.isDisplayed());
	}
	
	@When("^I set text '([^\\\"]*)' at default page$")
    public void i_set_text_at_default_page(String value) {
		GoogleSearchPage.get().textField.setText(value);
    }
	
	@Then("^I did not see search button$")
    public void i_did_not_see_search_button() {
		Assert.assertFalse("check search button is not visible",GoogleSearchPage.get().searchBtn.isDisplayed());
    }
	
	@When("^I click '([^\\\"]*)' button$")
    public void i_click_search_button(String value) {
        switch (value) {
            case "Search":
            	GoogleSearchPage.get().searchBtn2.click();
                break;
            case "Search2":
            	GoogleSearchPage.get().searchBtn2.click();
                break;
        }
    }
	
	@Then("^I see first link text '([^\\\"]*)' at results page$")
    public void i_see_first_link_text_at_results_page(String value) {
		Assert.assertEquals("check first link text",GoogleSearchPage.get().resultsLinks.getText(0), value);
    }
	
	@When("^I click at '([^\\\"]*)' results link$")
    public void i_click_at_results_link(int number) {
		GoogleSearchPage.get().resultsLinks.clickBy(number-1);
    }

    @Then("^I see first link title '([^\\\"]*)' at new tab$")
    public void i_see_first_link_title_at_new_tab(String value) {
    	WebDriverWrapper.waitForAjaxJQueryProcess();
        org.junit.Assert.assertEquals("check first link title",WebDriverWrapper.getDriver().getTitle(), value);
    }
}
