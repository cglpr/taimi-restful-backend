package taimi.backend.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author vpotry
 *
 */
@Document(collection = "GlobalTechDemand")
public class SkillDemand implements MongoDbObject, Comparable<SkillDemand>  {
	
	@Id
	private ObjectId id;
	
	@Indexed
	private String techName;
	
	private String desc;
	
	private int demandCnt;
	
	
	public SkillDemand() {
		this(null, 0, null);
	}
	
	public SkillDemand(String hash, String tech, int demandCnt) {
		this(tech, demandCnt, "");
	}
	
	public SkillDemand(String tech, int demandCnt,  String desc) {
		this.techName = tech;
		this.demandCnt = demandCnt;
		this.desc = desc;
		
		this.id = new ObjectId();
	}
	
	public String getTechName() {
		return techName;
	}
	
	public void setTechName(String tech) {
		this.techName = tech;
	}
	
	public int getDemandCnt() {
		return demandCnt;
	}
	
	public void setDemandCnt(int demandCnt) {
		this.demandCnt = demandCnt;
	}	

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	@Override
	public int compareTo(SkillDemand sd) {
		return sd.getDemandCnt() - this.demandCnt;
	}
}
