package taimi.test.database;

import java.util.ArrayList;
import java.util.List;

import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.mongodb.BasicDBObject;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import taimi.database.search.MongoDBSearch;
import taimi.database.spring.MongoSpringClient;
import taimi.domain.ExternalSource;
import taimi.domain.SkillDemand;
import taimi.visualization.DataTableMaker;

public class MongoSpringTest {

	@Test
	private void testGetDataTable() {
		List <ExternalSource> lst = MongoDBSearch.getAllSkillDemands();
		ColumnDescription[] columns = {new ColumnDescription("techname", ValueType.TEXT, "Technology"),  
									new ColumnDescription("count", ValueType.NUMBER, "Demand#")};
		DataTable dt = DataTableMaker.makeDataTable(lst, columns);
		
		System.out.println(dt);
		
	}
	
	@Test
	private void testGetCollection() {
		com.mongodb.DBCollection col = MongoSpringClient.getCollection("TestCollection");
		BasicDBObject inQuery = new BasicDBObject();
		inQuery.put("name", "test1");
		
		System.out.println(col.getCount(inQuery));
	}
	
	@Test
	private void testFindCriteria() {
		List <ExternalSource> lst = MongoDBSearch.getSkillDemandFromSourcename("Test1");
		
		for(ExternalSource src : lst) {
			System.out.println(src.getName() + ", " + src.getUrl());
			List <SkillDemand> demands = src.getDemands();
			for(SkillDemand sd : demands) {
				System.out.println(sd.getTechName() + ": " + sd.getDemandCnt());
			}
		}
	}
	
	@Test
	private void testFind() {
		List <ExternalSource> lst = MongoDBSearch.getAllSkillDemands();
		
		for(ExternalSource src : lst) {
			System.out.println(src.getName() + ", " + src.getUrl());
			List <SkillDemand> demands = src.getDemands();
			for(SkillDemand sd : demands) {
				System.out.println(sd.getTechName() + ": " + sd.getDemandCnt());
			}
		}
	}
	
	@Test
	private void testSave() {
		List <SkillDemand> sd = new ArrayList <SkillDemand>();
		sd.add(new SkillDemand("Java", 10, "Java demand"));
		sd.add(new SkillDemand("C", 15, "Perl demand"));
		ExternalSource ext = new ExternalSource("Test3", "http://testi3.tester.fi", sd);
		
		MongoSpringClient.saveObject(ext);
	}

	
}
