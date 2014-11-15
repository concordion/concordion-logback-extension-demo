package org.concordion.ext.driver.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Manages the browser session.
 */
public class Browser {
	private WebDriver driver;

	public Browser() {
		driver = new FirefoxDriver();
	}

	public void quit() {
		driver.quit();
	}

	public void addLogger() {
		EventFiringWebDriver efwd = new EventFiringWebDriver(driver);
		efwd.register(new SeleniumEventLogger());
		driver = efwd;
	}

	public WebDriver getDriver() {
		return driver;
	}

}
