package driver.google.web;

import org.concordion.selenium.Browser;
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
		logger.step("Opening {}", this.getClass().getSimpleName());
	}

	public void capturePage(String description) {
		logger.with()
				.message(description)
				.screenshot()
				.debug();
		
	}

	protected Browser getBrowser() {
		return browser;
	}
}
