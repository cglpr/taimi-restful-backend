package taimi.test.database;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.mongodb.BasicDBObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import taimi.backend.annotation.utils.AnnotationUtils;
import taimi.backend.configuration.SpringConfiguration;
import taimi.backend.data.handler.DataTableMaker;
import taimi.backend.domain.ExternalSource;
import taimi.backend.domain.SkillDemand;
import taimi.backend.domain.SourceURL;
import taimi.backend.domain.Technology;
import taimi.backend.service.MongoDBService;

public class MongoSpringTest {
	
	/* Autowired
	MongoDBService mongoDBService;
	*/
	
	private static MongoOperations getMongoOperations() {
		AnnotationConfigApplicationContext ctx = 
	             new AnnotationConfigApplicationContext(SpringConfiguration.class);
		
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		ctx.close();
		
		return mongoOperation;
	}
	
	@Test
	public void testGetDataTable() {
		List <ExternalSource> lst = getMongoOperations().findAll(ExternalSource.class);
		ColumnDescription[] columns = {new ColumnDescription("techname", ValueType.TEXT, "Technology"),  
									new ColumnDescription("count", ValueType.NUMBER, "Demand#")};
		DataTable dt = DataTableMaker.makeDataTable(lst, columns);
		
		System.out.println(dt);
		
	}
	
	@Test
	public void testGetCollection() {
		com.mongodb.DBCollection col = getMongoOperations().getCollection("TestCollection");
		BasicDBObject inQuery = new BasicDBObject();
		inQuery.put("name", "test1");
		
		System.out.println(col.getCount(inQuery));
	}
	
	@Test
	public void testFind() {
		List <ExternalSource> lst =  getMongoOperations().findAll(ExternalSource.class);
		
		for(ExternalSource src : lst) {
			System.out.println(src.getUrl());
			List <SkillDemand> demands = src.getDemands();
			for(SkillDemand sd : demands) {
				System.out.println(sd.getTechName() + ": " + sd.getDemandCnt());
			}
		}
	}
	
	
	@Test
	public void testGetTechs() {
		List <Technology> techs = getMongoOperations().findAll(Technology.class);
		
		for(Technology t : techs) {
			System.out.println(t.getName());
		}
	}
	
	@Test
	public void testGetURLS() {
		List <SourceURL> urls = getMongoOperations().findAll(SourceURL.class);
		
		for(SourceURL u : urls) {
			System.out.println(u.getUrl() + u.getParameters());
		}
	}


	public void testCollectionNames() {
		try {
			assertEquals("SourceUrls", AnnotationUtils.getCollectionName(SourceURL.class));
			assertEquals("Technologies", AnnotationUtils.getCollectionName(Technology.class));
			assertEquals("GlobalTechDemand", AnnotationUtils.getCollectionName(SkillDemand.class));
			assertEquals("ExternalSources", AnnotationUtils.getCollectionName(ExternalSource.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] argv) {
		new MongoSpringTest().testGetDataTable();
	}
}
