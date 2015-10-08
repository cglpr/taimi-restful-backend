package taimi.database.search;

import java.util.*;

import org.springframework.data.mongodb.core.query.Criteria;

import taimi.database.spring.MongoSpringClient;
import taimi.domain.ExternalSource;
import taimi.domain.SkillDemand;

/**
 * 
 * @author vpotry
 *
 */
public class MongoDBSearch {
	
	/**
	 * 
	 * @param srcName
	 * @return
	 */
	public static List<ExternalSource> getSkillDemandForURL(String url) {
		return MongoSpringClient.find(Criteria.where("url").is(url), ExternalSource.class);
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<ExternalSource> getAllSkillDemands() {
		 return MongoSpringClient.findAll(ExternalSource.class);
	}
	
	

}
