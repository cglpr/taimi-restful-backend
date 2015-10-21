package taimi.backend.service;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.mongodb.WriteResult;
import taimi.backend.domain.ExternalSource;
import taimi.backend.domain.MongoDbObject;

/**
 * Service for MongoDB operations
 * 
 * @author vpotry
 *
 */
@Service("mongoDBService")
public class MongoDBService {
	
	private static final Logger logger = LogManager.getLogger(MongoDBService.class);
	
	@Autowired 
	MongoOperations mongoOperation;
	
	public <T> WriteResult updateObject(Query query, Update update, Class<T> klazz) {
		return mongoOperation.updateFirst(query, update, klazz);
	}
	
	public boolean saveObject(MongoDbObject obj) {
		boolean ok = false;
		
		try {
			mongoOperation.save(obj);
			ok = true;
		} catch(org.springframework.dao.DuplicateKeyException dke) {
			// Ignore
			dke.printStackTrace();
			logger.debug("Duplicate key: " + dke.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Failed to save object: ", ex );
		}
		
		return ok;
	}
	
	public boolean saveObject(Collection <MongoDbObject> mObj) {
		boolean ok = true;
		try {
			for(MongoDbObject obj : mObj) {
				mongoOperation.save(obj);
			}
		} catch(org.springframework.dao.DuplicateKeyException dke) {
			// Ignore
			logger.debug("Duplicate key: " + dke.getMessage());
		} catch(Exception ex) {
			logger.error("Failed to save object: ", ex );
			ok = false;
		}
		return ok;
	}
	
	public <T> List<T> findAll(Class<T> klazz) {
		return mongoOperation.findAll(klazz);
	}
	
	public <T> List<T> find(Criteria criteria, Class<T> klazz) {    
		Query query = new Query(criteria);
		return mongoOperation.find(query, klazz);
	}
	
	public <T> T findOne(Criteria criteria, Class<T> klazz) {    
		Query query = new Query(criteria);
		return mongoOperation.findOne(query, klazz);
	}
	
	public com.mongodb.DBCollection getCollection(String collectionName) {
		return mongoOperation.getCollection(collectionName);
	}
	
	/**
	 * Be careful out there :)
	 * 
	 * @param klazz
	 */
	public <T> void dropCollection(Class<T> klazz) {
		mongoOperation.dropCollection(klazz);
	}
	
	
	/**
	 * 
	 * @param srcName
	 * @return
	 */
	public List<ExternalSource> getSkillDemandForURL(String url) {
		return find(Criteria.where("url").is(url), ExternalSource.class);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ExternalSource> getAllSkillDemands() {
		 return findAll(ExternalSource.class);
	}
}
