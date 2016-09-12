package org.concordion.driver.ui.google;

import org.concordion.driver.selenium.Browser;
import org.concordion.slf4j.ext.ReportLogger;
import org.concordion.slf4j.ext.ReportLoggerFactory;
import org.openqa.selenium.support.PageFactory;

public class PageObject {
	private final ReportLogger logger = ReportLoggerFactory.getReportLogger(this.getClass().getName());
	private final Browser browser;

	public PageObject(Browser browser) {
		this.browser = browser;

		PageFactory.initElements(browser.getDriver(), this);

		pageOpening();
	}

	protected void pageOpening() {
		//TODO Cannot make this location aware!
		logger.step("Opening {}", this.getClass().getSimpleName());		
	}

	public void capturePage(String description) {
		logger.with()
				.message(description)
				.screenshot()
				.locationAwareParent(PageObject.class)
				.debug();
		
	}

	protected Browser getBrowser() {
		return browser;
	}
}
