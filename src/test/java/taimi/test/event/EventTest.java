package taimi.test.event;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import taimi.backend.configuration.SpringConfiguration;
import taimi.backend.event.DataEventPublisher;
import taimi.backend.service.WebSearchService;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

public class EventTest {
	
	@Test
	public void testPublish() {
		DataEventPublisher dataEventPublisher = getDataEventPublisher();
		dataEventPublisher.publish();  
		dataEventPublisher.publish("text...");
		dataEventPublisher.publish("<tag s=\"bla\">text</tag>");  
	}
	
	@Test
	public void testJSONArrayPublish() throws IOException, JSONException {
		  JSONArray json = WebSearchService.readJsonFromUrl("http://service.dice.com/api/rest/jobsearch/v1/simple.json?text=java"); //readJsonFromUrl("https://jobs.github.com/positions.json?description=java");
		  DataEventPublisher dataEventPublisher = getDataEventPublisher();
		  dataEventPublisher.publish(json);
	}
	
	private static DataEventPublisher getDataEventPublisher() {
		AnnotationConfigApplicationContext ctx = 
	             new AnnotationConfigApplicationContext(SpringConfiguration.class);
		
		return (DataEventPublisher) ctx.getBean("dataEventPublisher");
	}
}
 