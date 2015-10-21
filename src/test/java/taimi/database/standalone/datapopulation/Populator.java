package taimi.database.standalone.datapopulation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import taimi.backend.configuration.SpringConfiguration;
import taimi.backend.domain.ExternalSource;
import taimi.backend.domain.SkillDemand;
import taimi.backend.domain.SourceURL;
import taimi.backend.domain.Technology;
import taimi.backend.service.MongoDBService;

public class Populator {
	
	public static void main(String[] argv) {
		addTechnology("C++", "Java", "EJB", "Spring", "Javascript", "JQuery", "Angularjs", "Perl", "Python", "Ruby", "JBoss", "Tomcat", "Weblogic", "Websphere", "MUMPS");
		addSourceURL("http://service.dice.com/api/rest/jobsearch/v1/simple.json?text=java", "?description=<technology>", "");
		addSourceURL("http://service.dice.com/api/rest/jobsearch/v1/simple.json", "?text=<technology>", "GET_VALUE:count");
		//new Populator().addSourceURL("https://api.usa.gov/jobs/search.json", "query=<technology>");

	}
	
	private static void addTechnology(String ...techNames) {
		MongoOperations mongoOperation = getMongoOperations();
		
		for(int i=0; i<techNames.length; i++) {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("name").is(techNames[i]));
			
			Technology tech  = mongoOperation.findOne(query, Technology.class);
			if(tech == null) {
				tech = new Technology(techNames[i]);
			}
			mongoOperation.save(tech);
		}
	}
	
	private static void addSourceURL(String url, String parameters, String criteria) {
		SourceURL srcurl = new SourceURL(url, parameters, criteria);
		getMongoOperations().save(srcurl);
	}
	
	private static MongoOperations getMongoOperations() {
		AnnotationConfigApplicationContext ctx = 
	             new AnnotationConfigApplicationContext(SpringConfiguration.class);
		
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		ctx.close();
		
		return mongoOperation;
	}

}
