package org.concordion.driver.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.concordion.driver.Config;
import org.concordion.slf4j.ext.ReportLoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Manages the browser session.
 */
public class Browser {
    static {
        WebDriverManager.chromedriver().setup();
    }

    private WebDriver driver;

    public Browser() {
        ChromeOptions options = new ChromeOptions();

        if (("true").equals(System.getenv("HEADLESS_CHROME"))) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        if (Config.isProxyRequired()) {
			String browserProxy = Config.getProxyHost() + ":" + Config.getProxyPort();

			final org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
			proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.MANUAL);
			proxy.setHttpProxy(browserProxy);
			proxy.setFtpProxy(browserProxy);
			proxy.setSslProxy(browserProxy);
			// proxy.setNoProxy(browserNoProxyList);

            options.setProxy(proxy);
            options.setAcceptInsecureCerts(true);
		}

        driver = new ChromeDriver(options);

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
