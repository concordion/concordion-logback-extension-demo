package org.concordion.driver.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumEventLogger extends AbstractWebDriverEventListener {

    final Logger logger = LoggerFactory.getLogger("selenium.events");
    private String oldValue;

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        oldValue = webElement.getAttribute("value");
    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        String elementName = getElementName(webElement);
        try {
            String newValue = webElement.getAttribute("value");
            if (!newValue.equals(oldValue)) {
                if (newValue.length() == 0) {
                    logger.debug("[{}] - cleared value", elementName);
                } else {
                    logger.debug("[{}] - changed value to '{}'", elementName, newValue);
                }
            }
        } catch (Exception e) {
            logger.debug("[{}] - changed value", elementName);
        }
    }

    @Override
    public void onException(Throwable arg0, WebDriver arg1) {
        logger.debug(arg0.getClass().getName(), arg0);
    }

    private String getElementName(WebElement arg0) {
        String foundBy = arg0.toString();
        if (foundBy != null) {
            int arrowIndex = foundBy.indexOf("->");
            if (arrowIndex >= 0) {
                return "By." + foundBy.substring(arrowIndex + 3, foundBy.length() - 1);
            }
        }
        return "unknown";
    }
}