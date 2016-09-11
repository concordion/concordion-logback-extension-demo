package org.concordion.demo;

import java.io.IOException;

import org.concordion.slf4j.ext.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import driver.google.web.GoogleResultsPage;
import driver.google.web.GoogleSearchPage;
import driver.http.HttpDriver;


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
        searchPage = new GoogleSearchPage(getBrowser());
        resultsPage = searchPage.searchFor(topic);
    }

    /**
     * Returns the result from Google calculation.
     */
    public String getCalculatorResult() {
        return resultsPage.getCalculatorResult();
    }

    public boolean makeRestCall() throws IOException {
    	//xmlurl = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22nome%2C%20ak%22)&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    	String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22nome%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    	//https://developer.yahoo.com/weather/
    	
    	getLogger().with()
    		.message("Calling Yahoo weather api")
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
    	return new GoogleSearchPage(getBrowser()).searchFor(topic).getCalculatorResult();
    }
}
