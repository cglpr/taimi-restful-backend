package taimi.database.spring;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import taimi.database.config.SpringMongoDbConfig;
import taimi.domain.MongoDbObject;

public class MongoSpringClient {
	
	private static final Logger logger = Logger.getLogger(MongoSpringClient.class);
	
	private  static MongoOperations getMongoOperations() {
		ApplicationContext ctx = 
	             new AnnotationConfigApplicationContext(SpringMongoDbConfig.class);
		
		 for (  String bean : ctx.getBeanDefinitionNames()) {
			    System.out.println(bean);
			  }
		
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		return mongoOperation;
	}
	
	public static boolean saveObject(MongoDbObject obj) {
		boolean ok = false;
		
		try {
			getMongoOperations().save(obj);
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
	
	public static boolean saveObject(Collection <MongoDbObject> mObj) {
		boolean ok = true;
		MongoOperations mongoOperation = getMongoOperations();
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
	
	public static <T> List<T> findAll(Class<T> klazz) {
	    MongoOperations mongoOperation = getMongoOperations();
		return mongoOperation.findAll(klazz);
	}
	
	public static <T> List<T> find(Criteria criteria, Class<T> klazz) {
	    
		Query query = new Query(criteria);
		MongoOperations mongoOperation = getMongoOperations();
	    
		return mongoOperation.find(query, klazz);
	}
	
	public static com.mongodb.DBCollection getCollection(String collectionName) {
		MongoOperations mongoOperation = getMongoOperations();
		
		return mongoOperation.getCollection(collectionName);
	}
}