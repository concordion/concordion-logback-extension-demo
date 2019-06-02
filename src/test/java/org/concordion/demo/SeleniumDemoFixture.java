package org.concordion.demo;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.concordion.driver.http.HttpDriver;
import org.concordion.driver.ui.google.GoogleResultsPage;
import org.concordion.driver.ui.google.GoogleSearchPage;
import org.concordion.slf4j.ext.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


/**
 * A fixture class for the StoryboardDemo.md specification.
 * <p>
 * This adds the Storyboard Extension to Concordion to add a storyboard to each example.
 * <p>
 * Two examples are included, a browser UI example using WebDriver, and a basic web service performing a HTTP GET.
 * <p>
 * Run this class as a JUnit test to produce the Concordion results.
 */
public class SeleniumDemoFixture extends ConcordionFixture {

    private GoogleSearchPage searchPage;
    private GoogleResultsPage resultsPage;

    /**
     * Searches for the specified topic, and waits for the results page to load.
     */
    public void searchFor(final String topic) {
		searchPage = GoogleSearchPage.open(getBrowser());
        resultsPage = searchPage.searchFor(topic);
    }

    /**
     * Returns the result from Google calculation.
     */
    public String getCalculatorResult() {
        return resultsPage.getCalculatorResult();
    }

    public boolean makeRestCall(String url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        getLogger().with()
                .message("Calling cat api")
    		.data(url)
    		.debug();
    	
		String responseMessage = new HttpDriver().get(url);
        
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(responseMessage);
		String prettyJsonString = gson.toJson(je);
		
        getLogger().with()
        	.message("Response")
        	.attachment(prettyJsonString, "response.json", MediaType.JSON)
        	.debug();
        
        return !responseMessage.isEmpty();
    }
    
    public String searchForTopic(String topic) {
    	//TODO This is not a step - possibly need a feature method? 
		getLogger().step("Example: " + topic);
		return GoogleSearchPage.open(getBrowser()).searchFor(topic).getCalculatorResult();
    }
}
