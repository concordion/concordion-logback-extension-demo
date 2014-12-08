package org.concordion.ext.demo;

import org.concordion.api.FailFast;
import org.concordion.api.extension.Extension;
import org.concordion.api.extension.Extensions;
import org.concordion.ext.LogbackLogMessenger;
import org.concordion.ext.LoggingFormatterExtension;
import org.concordion.ext.LoggingTooltipExtension;
import org.concordion.ext.loggingFormatter.LogbackHelper;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

/**
 * A base class for Google search tests that opens up the Google site at the Google search page, and closes the browser once the test is complete.
 */
@RunWith(ConcordionRunner.class)
@Extensions({ LoggingFormatterExtension.class })
@FailFast
public abstract class AcceptanceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final Logger tooltipLogger = LoggerFactory.getLogger("TOOLTIP_" + this.getClass().getName());

	@Extension
	// Set third parameter (isAdditive) to false to prevent tooltip messages from appearing in logs.  Requires a uniquely named logger as per this example otherwise
	// you will affect all logging.
	public final LoggingTooltipExtension tooltipExtension = new LoggingTooltipExtension(new LogbackLogMessenger(tooltipLogger.getName(), Level.ALL, true));
	
	static {
		LogbackHelper.logInternalStatus();
	}

	public AcceptanceTest() {
		
	}

	public Logger getLogger() {
		return logger;
	}

	public void addConcordionTooltip(final String message) {
		// Logging at debug level means the message won't make it to the console, but will make it to the logs (based on included logback configuration files)
		tooltipLogger.debug(message);
	}
	
	@Before
	public void startUpTest() {
		LogbackHelper.startTestLogging(this);
		logger.info("Initialising the acceptance test class {} on thread {}", this.getClass().getSimpleName(), Thread.currentThread().getName());
	}

	@After
	public void tearDownTest() {
		logger.info("Tearing down the acceptance test class on thread {}", Thread.currentThread().getName());
		LogbackHelper.stopTestLogging();
	}
}
