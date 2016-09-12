package org.concordion.selenium;

import org.concordion.slf4j.ext.ReportLoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import driver.Config;

/**
 * Manages the browser session.
 */
public class Browser {
    private WebDriver driver;

    public Browser() {
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();

		if (Config.isProxyRequired()) {
			String browserProxy = Config.getProxyHost() + ":" + Config.getProxyPort();

			final org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
			proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.MANUAL);
			proxy.setHttpProxy(browserProxy);
			proxy.setFtpProxy(browserProxy);
			proxy.setSslProxy(browserProxy);
			// proxy.setNoProxy(browserNoProxyList);

			capabilities.setCapability(CapabilityType.PROXY, proxy);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		}

		driver = new FirefoxDriver(capabilities);

		EventFiringWebDriver efwd = new EventFiringWebDriver(driver);
		efwd.register(new SeleniumEventLogger());
		driver = efwd;

		ReportLoggerFactory.setScreenshotTaker(new SeleniumScreenshotTaker(this));
    }

    public void close() {
		ReportLoggerFactory.removeScreenshotTaker();

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public WebDriver getDriver() {
		return driver;
    }
}
