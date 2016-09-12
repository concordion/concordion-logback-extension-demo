package org.concordion.demo;

import org.concordion.api.AfterExample;
import org.concordion.api.AfterSpecification;
import org.concordion.api.BeforeExample;
import org.concordion.api.BeforeSpecification;
import org.concordion.api.ConcordionScoped;
import org.concordion.api.ExampleName;
import org.concordion.api.FailFast;
import org.concordion.api.Scope;
import org.concordion.api.ScopedObjectHolder;
import org.concordion.api.extension.Extension;
import org.concordion.api.extension.Extensions;
import org.concordion.driver.selenium.Browser;
import org.concordion.driver.selenium.SeleniumScreenshotTaker;
import org.concordion.ext.LogbackLogMessenger;
import org.concordion.ext.LoggingFormatterExtension;
import org.concordion.ext.LoggingTooltipExtension;
import org.concordion.integration.junit4.ConcordionRunner;
import org.concordion.logback.LogbackAdaptor;
import org.concordion.slf4j.ext.FluentLogger;
import org.concordion.slf4j.ext.ReportLogger;
import org.concordion.slf4j.ext.ReportLoggerFactory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

/**
 * A base class for Google search tests that opens up the Google site at the Google search page, and closes the browser once the test is complete.
 */
@RunWith(ConcordionRunner.class)
@Extensions( LoggingTooltipExtension.class )
@FailFast
public abstract class ConcordionFixture {

	private final ReportLogger logger = ReportLoggerFactory.getReportLogger(this.getClass().getName());
	private final Logger tooltipLogger = LoggerFactory.getLogger("TOOLTIP_" + this.getClass().getName());

	@Extension
	LoggingFormatterExtension logExtension = new LoggingFormatterExtension();
	
	@Extension
	// Some notes on LogbackLogMessenger parameters:
	// loggerName: is set to a unique value - this means the tooltip extension will only pick up log messages specifically targeted for the tooltip
	// isAdditive: set to true so that log messages are also picked up by other appenders (see addConcordionTooltip() for more info)
	// tooltipPatter: default format is "[timestamp] log message", I've overridden that in this example
	private final LoggingTooltipExtension tooltipExtension = new LoggingTooltipExtension(new LogbackLogMessenger(tooltipLogger.getName(), Level.ALL, true, "%msg%n"));
	
	@ConcordionScoped(Scope.SPECIFICATION)
    private ScopedObjectHolder<Browser> browserHolder = new ScopedObjectHolder<Browser>() {
        @Override
        public Browser create() {
            Browser browser = new Browser();
            return browser;
        }

        @Override
        protected void destroy(Browser browser) {
            FluentLogger.removeScreenshotTaker();
            browser.close();
        };
    };
    
	static {
		LogbackAdaptor.logInternalStatus();
	}

	public ConcordionFixture() {
		
	}

	public ReportLogger getLogger() {
		return logger;
	}

	public void addConcordionTooltip(final String message) {
		// Logging at debug level means the message won't make it to the console, but will make it to the logs (based on included logback configuration files)
		tooltipLogger.debug(message);
	}
	
	protected Browser getBrowser() {
        Browser browser = browserHolder.get();
        
        if (!FluentLogger.hasScreenshotTaker()) {
        	FluentLogger.addScreenshotTaker(new SeleniumScreenshotTaker(browser));
        }
        
        return browser;
    }
	
	@BeforeSpecification
	public void startUpTest() {
		logger.info("Initialising the acceptance test class {} on thread {}", this.getClass().getSimpleName(), Thread.currentThread().getName());
	}

	@BeforeExample
    private final void beforeExample(@ExampleName String exampleName) {
		logger.info("Starting example {}", exampleName);	
    }
	
	@AfterExample
    private final void afterExample() {
    	// Remove screenshot taker so that next example will not get the example 
    	// completed screenshot unless it has used the browser 
    	FluentLogger.removeScreenshotTaker();
    }
	
	@AfterSpecification
	public void tearDownTest() {
		logger.info("Tearing down the acceptance test class on thread {}", Thread.currentThread().getName());
	}
}
