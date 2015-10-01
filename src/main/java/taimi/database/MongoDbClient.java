package taimi.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.bson.Document;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Initial MongoDb client.
 * 
 * @author vpotry
 * 
 * @Since MongoClient 3.0
 */
public class MongoDbClient {

	private MongoDatabase mdb;
	private MongoClient mongoClient;
	
	public MongoDbClient() {		
	}
	
	public void closeConnections() {
		mongoClient.close();
	}
	
	public void connect() {
		 try{
			 ServerAddress server = new ServerAddress("localhost", 27017);
			 List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			 credentials.add(MongoCredential.createCredential("clientApp", "ProjectSkillDb", "clientApp".toCharArray()));
			 
			 mongoClient = new MongoClient(server, credentials);
			 
	         mdb = mongoClient.getDatabase("ProjectSkillDb");
	         
	         System.out.println("Connected to " + mdb.getName());
	      } catch(Exception e){
		     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		  }
	}
	
	public MongoCollection<Document> getCollectionForName(String collection) {
		return mdb.getCollection(collection);
	}
	
	public void insertJSON(JSONArray jsonArr, String tech) {
		
		MongoCollection<Document> collection = mdb.getCollection("extTechDemand");
		
		for(int i=0; i<jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			
			jsonObj.put("techname", tech);
			
			Document doc = org.bson.Document.parse(jsonObj.toString());
			
			try {
				System.out.println("Inserting to collection 'extTechDemand'...");
				collection.insertOne(doc);
			} catch(Exception ex) {}
		}
	}

}
