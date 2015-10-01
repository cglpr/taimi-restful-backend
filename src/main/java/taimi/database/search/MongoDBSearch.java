package taimi.database.search;

import java.util.*;

import org.bson.Document;

import taimi.database.MongoDbClient;
import taimi.domain.skill.SkillDemand;
import taimi.domain.skill.TechSkills;

import com.mongodb.client.MongoCollection;

public class MongoDBSearch {
	public List<SkillDemand> getForAllTechnologies() {
		MongoDbClient mongodb = new MongoDbClient();
		mongodb.connect();
		
		MongoCollection<Document> col = mongodb.getCollectionForName("extTechDemand");
	
		List<SkillDemand> demandLst = new ArrayList<SkillDemand>();
		for(TechSkills val : TechSkills.values()) {
			int cnt = (int) col.count(new Document("techname", val.toString()));
			demandLst.add(new SkillDemand(val.toString(), cnt));
			
		}
		
		mongodb.closeConnections();
		Collections.sort(demandLst);
		return demandLst;
	}
}
