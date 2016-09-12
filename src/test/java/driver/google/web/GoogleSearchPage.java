package driver.google.web;

import org.concordion.selenium.Browser;
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

    @CacheLookup
    @FindBy(name = "btnG")
    private WebElement submitButton;

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

        submitButton.click();

		return new GoogleResultsPage(getBrowser());
    }
}