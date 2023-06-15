package com.m2at.glue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.m2at.utils.LoadProperties;
import com.m2at.utils.WebDriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GooglePagetitleSteps {

	WebDriver driver;
	WebElement element;
	WebDriverWait wait;

	Properties property;

	private String testUrl;
	private String browserType;
	private String filePath;

	LoadProperties loadProperties;
	WebDriverFactory webDriverFactory;

	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}

	public String getTestUrl() {
		return testUrl;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public String getBrowserType() {
		return browserType;
	}

	/**
	 * Wait Until Page Loads completely
	 * 
	 * @param driver
	 */
	public void waitUntilPageLoadsCompletely(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	/**
	 * Get element based on locator
	 * 
	 * @param locator
	 * @return element
	 */
	public WebElement getWebElement(String locator) {
		try {
			element = driver.findElement(By.xpath(locator));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			Assert.fail("Cannot locate element: " + e.getMessage());
		}

		return element;
	}

	@Before
	public void beforeScenario() throws InterruptedException, IOException {
		// Setup properties
		filePath = "./src/test/resources/config/google.properties";
		loadProperties = new LoadProperties(filePath);
		property = loadProperties.loadProperty();

		// Instantiate browser driver based on browserType		
		webDriverFactory = new WebDriverFactory();
		driver = webDriverFactory.initialiseWebDriver(property.getProperty("browserType"));

		// Instantiate WebDriverWait
		wait = new WebDriverWait(driver, 10);

		// Navigate
		driver.manage().window().maximize();
		driver.navigate().to(property.getProperty("testUrl"));
	}

	@After
	public void afterScenario() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Given("I am on the homepage")
	public void i_am_on_the_homepage() {
		try {

			boolean searchTextBoxPresent = getWebElement(property.getProperty("searchBox")).isDisplayed();

			assertThat(searchTextBoxPresent, is(true));

			System.out.println("User is on the home Page");
		} catch (AssertionError e) {
			System.out.println("User is NOT on the home Page");
			Assert.fail("User is NOT on the home Page - " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception");
			Assert.fail("Exception - " + e.getMessage());
		} 
	}

	@When("I search for {string}")
	public void i_search_for(String searchItem) {
		getWebElement(property.getProperty("searchBox")).sendKeys(searchItem);
		getWebElement(property.getProperty("searchBox")).submit();
		waitUntilPageLoadsCompletely(driver);
	}

	@Then("the page title should include {string}")
	public void the_page_title_should_include(String expected) {
		try {			
			System.out.println("expected: " + expected);
			
			String actual = driver.getTitle();
			System.out.println("actual: " + actual);

			assertThat(actual, containsString(expected));

			System.out.println("Test passed");
		} catch (AssertionError e) {
			System.out.println("Assertion error - test failed");
			Assert.fail("Assertion error - test failed - " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception");
			Assert.fail("Exception - " + e.getMessage());
		} 
	}
}
