package taimi.backend.configuration;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import taimi.backend.annotation.mongodb.CascadingMongoEventListener;
import taimi.backend.event.DataEventPublisher;
import taimi.backend.listener.DataEventListener;
import taimi.backend.service.MongoDBService;

import com.mongodb.MongoClient;

@Configuration
@ComponentScan({"taimi.backend.configuration","taimi.backend.controller","taimi.backend.service"})
@PropertySource("classpath:application.properties")
public class SpringConfiguration {

	@Value("${db.databaseName}")
	private String mongoDbName;
	
	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), mongoDbName);
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
	
	@Bean
	public DataEventPublisher dataEventPublisher() {
		return new DataEventPublisher();
	}
	
	@Bean
	public DataEventListener dataEventListener() {
		return new DataEventListener();
	}
	
	@Bean
	public MongoDBService mongoDBService() {
		return new MongoDBService();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer getPropertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@PostConstruct
    public void postInit() {
        System.out.println("Bean config initialized.");
    }
	
}