package taimi.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import taimi.backend.annotation.utils.AnnotationUtils;
import taimi.backend.data.handler.JsonHandler;
import taimi.backend.domain.ExternalSource;
import taimi.backend.domain.SkillDemand;
import taimi.backend.domain.SourceURL;
import taimi.backend.domain.Technology;
import taimi.backend.event.DataEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Defined in scheduling-context.xml (Spring/cron configuration)
 * 
 * Fetches data from external sources & inserts into MongoDb.
 * 
 * TODO: Move all data processing to separate handler behind data notification
 * @see taimi.backend.listener.DataEventListener
 * 
 * @author vpotry
 *
 */
@Service("skillDemandWebSearchService")
public class SkillDemandWebSearchService implements AbstractService {
	
	private final Logger logger = Logger.getLogger(SkillDemandWebSearchService.class);
	
	@Autowired 
	MongoDBService mongoDBService;
	
	@Autowired
	DataEventPublisher dataEventPublisher;
	
	public void performService() {
		logger.info("Scheduled service thread " + Thread.currentThread().getName() + " started.");
		
		try {
			List <ExternalSource> data = getData();
			if(data != null) {
				storeData(data);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			logger.error("Exception occurred in JSON handling",e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception occurred in reading JSON from URL",e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting SkillDemand data",e);
		}
		
		logger.info(Thread.currentThread().getName() + " : All tasks done.");
	}
	
	private List <ExternalSource> getData() throws Exception {
		List <SourceURL>  urls = mongoDBService.findAll(SourceURL.class);
		List <Technology> techs =  mongoDBService.findAll(Technology.class);
		
		if(urls == null || urls.size() == 0) {
			logger.warn("There are no SourceURLs in MongoDb collection " + AnnotationUtils.getCollectionName(SourceURL.class));
			logger.warn("-> Skill demand data not fetched.");
			return null;
		} 
		
		if(techs == null || techs.size() == 0) {
			logger.warn("There are no Technologies in MongoDb collection " + AnnotationUtils.getCollectionName(Technology.class));
			logger.warn("-> Skill demand data not fetched.");
			return null;
		}
		
		List <ExternalSource> extSrcLst = new ArrayList <ExternalSource>();
		
		for(SourceURL url : urls) {
			ExternalSource extSrc = new ExternalSource(url.getUrl());
			List <SkillDemand> demands = new ArrayList<SkillDemand>();
			
			for(Technology tech : techs) {
				String searchURL = url.getUrl();
				String urlParams = url.getParameters();
				
				if(urlParams != null) {
					searchURL = searchURL + urlParams.replace("<technology>", tech.getName());
				}
	
				demands.add(getDemand(searchURL, tech.getName(), url.getCriteria()));	
			}
			extSrc.setDemands(demands);
			extSrcLst.add(extSrc);
		}
		
		return extSrcLst;
	}
	
	private void storeData(List <ExternalSource> extSrcLst) {
		try {
			logger.debug("Storing to collection " + 
					AnnotationUtils.getCollectionName(ExternalSource.class) + 
					" -> " + extSrcLst.size() + " documents.");
		} catch (Exception e) {
			logger.debug("Storing to collection (failed to read collection name from class:( )");
		}
		
		// *** This is hopefully only temporal solution! Read TODO: fix update!
		mongoDBService.dropCollection(SkillDemand.class);
		mongoDBService.dropCollection(ExternalSource.class);
		
		for(ExternalSource s : extSrcLst) {			
			//esDb  = mongoDBService.findOne(Criteria.where("url").is(s.getUrl()), ExternalSource.class);
			/*if(esDb == null) {
				// New one
				mongoDBService.saveObject(s);
			} else {
				// Change db values for fresh values... TODO: there must be a better way...
				//List <SkillDemand> sdFresh = s.getDemands();
				
				// -> Or worse :) -> ***
			} */
				
			mongoDBService.saveObject(s);
		}
		
		logger.debug("-> done");
	}
	
	private SkillDemand getDemand(String URL, String techName, final String rule) throws JSONException, IOException {
		JSONArray jsonArr = WebSearchService.readJsonFromUrl(URL);
		publishData(jsonArr);
		/** ^-- As Listener behind publish does nothing yet, we handle data here **/
		// Well, currently we don't want to save any other data than amounts...
		
		int cnt = JsonHandler.getCount(jsonArr, rule);
		
		return new SkillDemand(techName, cnt,  "");
	}
	
	/**
	 * Publishes data for Listener (that is supposed to handle it based on type)
	 * 
	 * @param jsonArr
	 */
	private void publishData(JSONArray jsonArr) {
		dataEventPublisher.publish(jsonArr);
	}
}
 