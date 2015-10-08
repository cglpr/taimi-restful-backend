package taimi.task.scheduled.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import taimi.database.MongoDbClient;
import taimi.domain.skill.TechSkills;
import web.resource.json.JSONWebSearch;

/**
 * Defined in scheduling-context.xml (Spring/cron configuration)
 * 
 * Fetches data from external sources & inserts into MongoDb.
 * 
 * @author vpotry
 *
 */
public class SkillDemandWebSearchServiceImpl implements AbstractService {
	protected static Logger logger = Logger.getLogger(SkillDemandWebSearchServiceImpl.class);

	private final MongoDbClient mongodb = new MongoDbClient();
	
	public void performService() {
		String threadName = Thread.currentThread().getName();
		
		try {
			logger.info("Scheduled service thread " + threadName + " started.");
			getTechs();
		} catch (JSONException e) {
			logger.error("Exception occurred in JSON handling",e);
		} catch (IOException e) {
			logger.error("Exception occurred in reading JSON from URL",e);
		}
		logger.info("Scheduled service thread " + threadName + " finished.");
	}
	
	private void getTechs() throws JSONException, IOException {
		String baseUrl = "https://jobs.github.com/positions.json?description=";
		String url = null;
		
		List <JSONArray> jsonAArr = new ArrayList <JSONArray>();
		
		for(TechSkills val : TechSkills.values()) {
			url = baseUrl + val.toString();
			JSONArray jsonArr = JSONWebSearch.readJsonFromUrl(url);
			if(jsonArr != null) {
				jsonAArr.add(jsonArr);
				mongodb.insertJSON(jsonArr, val.toString());
			}
		}
	}
	
}
