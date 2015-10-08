package taimi.database.search;

import java.util.*;

import org.springframework.data.mongodb.core.query.Criteria;
import taimi.database.spring.MongoSpringClient;
import taimi.domain.ExternalSource;

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
	public static List<ExternalSource> getSkillDemandFromSourcename(String srcName) {
		return MongoSpringClient.find(Criteria.where("name").is(srcName), ExternalSource.class);
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<ExternalSource> getAllSkillDemands() {
		return MongoSpringClient.findAll(ExternalSource.class);
	}

}
