package taimi.database.test.standalone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.mongodb.BasicDBObject;

import taimi.database.config.SpringMongoDbConfig;
import taimi.database.search.MongoDBSearch;
import taimi.database.spring.MongoSpringClient;
import taimi.domain.ExternalSource;
import taimi.domain.MongoDbObject;
import taimi.domain.SkillDemand;
import taimi.visualization.DataTableMaker;

public class MongoSpringTest {
	public static void main(String[] args) throws Exception {
		//testSave();
		//testFindCriteria();
		//testFind();
		testGetDataTable();
	}
	
	private static void testGetDataTable() {
		List <ExternalSource> lst = MongoDBSearch.getAllSkillDemands();
		ColumnDescription[] columns = {new ColumnDescription("techname", ValueType.TEXT, "Technology"),  
									new ColumnDescription("count", ValueType.NUMBER, "Demand#")};
		DataTable dt = DataTableMaker.makeDataTable(lst, columns);
		
		System.out.println(dt);
		
	}
	
	private static void testGetCollection() {
		com.mongodb.DBCollection col = MongoSpringClient.getCollection("externalSources");
		BasicDBObject inQuery = new BasicDBObject();
		inQuery.put("name", "test1");
		
		System.out.println(col.getCount(inQuery));
	}
	
	private static void testFindCriteria() {
		List <ExternalSource> lst = MongoDBSearch.getSkillDemandFromSourcename("Test1");
		
		for(ExternalSource src : lst) {
			System.out.println(src.getName() + ", " + src.getUrl());
			List <SkillDemand> demands = src.getDemands();
			for(SkillDemand sd : demands) {
				System.out.println(sd.getTechName() + ": " + sd.getDemandCnt());
			}
		}
	}
	
	private static void testFind() {
		List <ExternalSource> lst = MongoDBSearch.getAllSkillDemands();
		
		for(ExternalSource src : lst) {
			System.out.println(src.getName() + ", " + src.getUrl());
			List <SkillDemand> demands = src.getDemands();
			for(SkillDemand sd : demands) {
				System.out.println(sd.getTechName() + ": " + sd.getDemandCnt());
			}
		}
	}
	
	private static void testSave() {
		List <SkillDemand> sd = new ArrayList <SkillDemand>();
		sd.add(new SkillDemand("Java", 10, "Java demand"));
		sd.add(new SkillDemand("C", 15, "Perl demand"));
		ExternalSource ext = new ExternalSource("Test3", "http://testi3.tester.fi", sd);
		
		MongoSpringClient.saveObject(ext);
	}

	
}
