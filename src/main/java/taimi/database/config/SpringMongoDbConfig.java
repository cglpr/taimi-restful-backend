package taimi.database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import taimi.annotation.mongodb.CascadingMongoEventListener;

import com.mongodb.MongoClient;

@Configuration
public class SpringMongoDbConfig {

	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), "ProjectSkillDb");
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
		
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
				
		return mongoTemplate;
	}
	
	@Bean
    public CascadingMongoEventListener cascadingMongoEventListener() {
        return new CascadingMongoEventListener();
    }
	
	
}