package com.practicetestautomation.base;

import java.util.logging.Level;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


public class BrowserDriverFactory {

	WebDriver driver;

	//private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String browser;
	private Logger log;

	public BrowserDriverFactory(String browser, Logger log) {
		this.browser = browser.toLowerCase();
		this.log = log;
	}


	public WebDriver createDriver() {
		log.info("Create local driver: " + browser);

		switch (browser) {
		case "chrome":
			// Make sure to upgrade chromedriver to work with your browser version: https://chromedriver.chromium.org/downloads
			//System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			//System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			ChromeOptions o = new ChromeOptions();
			o.setHeadless(true);
			WebDriverManager.chromedriver().setup();
			driver= new ChromeDriver(o);
			break;

		case "firefox":
			// Make sure to upgrade geckodriver to work with your browser version: https://github.com/mozilla/geckodriver/releases
			//System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
			//System.setProperty(FirefoxDriver.Capability.MARIONETTE, "true");
			//System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
			FirefoxOptions fo = new FirefoxOptions();
			fo.setHeadless(true);
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(fo);

			break;

		default:
			/*log.debug("Do not know how to start: " + browser + ", starting chrome.");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");*/
			ChromeOptions de = new ChromeOptions();
			de.setHeadless(true);
			WebDriverManager.chromedriver().setup();
			driver= new ChromeDriver(de);
			break;
		}
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
		return driver;
	}
}
