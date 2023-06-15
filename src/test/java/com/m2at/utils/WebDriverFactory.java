package com.m2at.utils;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

	WebDriver driver;

	public void setWebDriverManager() {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
	}

	public WebDriver initialiseWebDriver(String browserType) {

		setWebDriverManager();
		
		if (driver == null) {
			switch (browserType) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "chromeheadless":
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");

				driver = new ChromeDriver(options);
				break;
			case "firefox":
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "firefoxLog");
				java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

				driver = new FirefoxDriver();

				break;
			case "firefoxheadless":
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "firefoxLog");
				java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

				FirefoxOptions foptions = new FirefoxOptions();
				foptions.setHeadless(true);
				
				driver = new FirefoxDriver(foptions);

				break;
			default:
				System.out.println("Driver not initialised");
				throw new RuntimeException("Unsupported browser");
			}
		}
		return driver;
	}
}
