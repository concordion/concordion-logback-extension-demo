package org.concordion.driver.ui.google;

import org.concordion.driver.selenium.Browser;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

/**
 * A WebDriver Page Object corresponding to the Google Search Page.
 */
public class GoogleSearchPage extends PageObject {
    @CacheLookup
    @FindBy(name = "q")
    private WebElement queryBox;

    @FindBy(className = "nonExistent")
    private WebElement nonExistentLink;

    /**
     * Opens the Google Search Page.
     */
    public static GoogleSearchPage open(Browser browser) {
        browser.getDriver().get("http://www.google.com");

        return new GoogleSearchPage(browser);
    }

    public GoogleSearchPage(Browser browser) {
        super(browser);
    }

    /**
     * Searches for the specified string and opens the results page, waiting for the page to fully load.
     */
    public GoogleResultsPage searchFor(String query) {
        queryBox.sendKeys(query);
        queryBox.sendKeys(Keys.ESCAPE);

        capturePage("Entered search text, and about to click search button");

        queryBox.sendKeys(Keys.RETURN);

        return new GoogleResultsPage(getBrowser());
    }
}