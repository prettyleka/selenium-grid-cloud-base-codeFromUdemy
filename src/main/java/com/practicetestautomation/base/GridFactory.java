package com.practicetestautomation.base;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class GridFactory {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String browser;
    private Logger log;
    private String platform;

    public GridFactory(String browser, String platform, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
        this.platform=platform;
    }


    public WebDriver createDriver() {
        log.info("Connecting to the node with: " + browser);
        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (browser) {
            case "chrome":
                capabilities.setBrowserName("chrome");
                break;

            case "firefox":
                capabilities.setBrowserName("firefox");
                break;

            default:
                capabilities.setBrowserName("chrome");
                break;
        }
        URL url=null;
        try {
            url = new URL("http://localhost:4444/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        driver.set(new RemoteWebDriver(url, capabilities));
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
        return driver.get();
    }
}

