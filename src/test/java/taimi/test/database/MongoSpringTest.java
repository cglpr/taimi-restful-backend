package taimi.test.database;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.mongodb.BasicDBObject;

import org.junit.Test;

import taimi.annotation.utils.AnnotationUtils;
import taimi.database.search.MongoDBSearch;
import taimi.database.spring.MongoSpringClient;
import taimi.domain.ExternalSource;
import taimi.domain.SkillDemand;
import taimi.domain.SourceURL;
import taimi.domain.Technology;

import taimi.visualization.DataTableMaker;

public class MongoSpringTest {
	@Test
	public void testGetDataTable() {
		List <ExternalSource> lst = MongoDBSearch.getAllSkillDemands();
		ColumnDescription[] columns = {new ColumnDescription("techname", ValueType.TEXT, "Technology"),  
									new ColumnDescription("count", ValueType.NUMBER, "Demand#")};
		DataTable dt = DataTableMaker.makeDataTable(lst, columns);
		
		System.out.println(dt);
		
	}
	
	@Test
	public void testGetCollection() {
		com.mongodb.DBCollection col = MongoSpringClient.getCollection("TestCollection");
		BasicDBObject inQuery = new BasicDBObject();
		inQuery.put("name", "test1");
		
		System.out.println(col.getCount(inQuery));
	}
	
	@Test
	public void testFind() {
		List <ExternalSource> lst = MongoDBSearch.getAllSkillDemands();
		
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
		List <Technology> techs = MongoSpringClient.findAll(Technology.class);
		
		for(Technology t : techs) {
			System.out.println(t.getName());
		}
	}
	
	@Test
	public void testGetURLS() {
		List <SourceURL> urls = MongoSpringClient.findAll(SourceURL.class);
		
		for(SourceURL u : urls) {
			System.out.println(u.getUrl() + u.getParameters());
		}
	}

	@Test
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
		List <ExternalSource> lst = MongoDBSearch.getAllSkillDemands();
		ColumnDescription[] columns = {new ColumnDescription("techname", ValueType.TEXT, "Technology"),  
									new ColumnDescription("count", ValueType.NUMBER, "Demand#")};
		DataTable dt = DataTableMaker.makeDataTable(lst, columns);
		
		System.out.println(dt);
	}
}
