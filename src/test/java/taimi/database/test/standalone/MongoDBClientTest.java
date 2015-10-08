package taimi.database.test.standalone;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import taimi.database.MongoDbClient;
import taimi.domain.skill.TechSkills;

public class MongoDBClientTest {
	public static void main(String[] argv) throws Exception {
		MongoDbClient mongodb = new MongoDbClient();
		mongodb.connect();
		
		MongoCollection<Document> col = mongodb.getCollectionForName("extTechDemand");
		
		
		
		for(TechSkills val : TechSkills.values()) {
			long cnt = col.count(new Document("techname", val.toString()));
			System.out.println(val + ": " + cnt);
		}
		
		mongodb.closeConnections();
	}
}
