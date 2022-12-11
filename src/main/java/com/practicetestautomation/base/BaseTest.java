package com.practicetestautomation.base;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

@Listeners({SauceLabsTestListener.class})
public class BaseTest {

	protected WebDriver driver;
	protected Logger log;

	@Parameters({ "browser", "environment", "platform" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, @Optional("chrome") String browser, @Optional("local") String environment, @Optional("") String platform, ITestContext ctx) throws MalformedURLException {
		log = LogManager.getLogger(ctx.getCurrentXmlTest().getSuite().getName());
		setProperties();

		ctx.setAttribute("sauce", false);

		switch (environment) {
			case "local":
				driver = new BrowserDriverFactory(browser, log).createDriver();
				break;
			case "grid":
				driver = new GridFactory(browser, platform, log).createDriver();
				break;
			case "sauce":
				ctx.setAttribute("sauce", true);
				String souceTestName=ctx.getName()+" | "+method.getName()+" | "
						+browser+" | "+platform;
				SauceLabsFactory factory = new SauceLabsFactory(browser, platform, souceTestName, log);
				driver = factory.createDriver();
				ctx.setAttribute("sessionID", factory.getSessionId());
				break;
			default:
				driver = new BrowserDriverFactory(browser, log).createDriver();
				break;

		}
		driver.manage().window().maximize();
	}

	private void setProperties() {
		Properties props = System.getProperties();
		try {
			props.load(new FileInputStream(new File("src/main/resources/test.properties")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}


	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		log.info("Close driver");
		driver.quit();
	}

}
