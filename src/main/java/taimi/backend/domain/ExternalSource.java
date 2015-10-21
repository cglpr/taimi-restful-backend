package taimi.backend.domain;


import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import taimi.backend.annotation.mongodb.CascadeSave;

@Document(collection = "externalSources")
public class ExternalSource implements MongoDbObject {
	
	@Id
	private ObjectId id;
	
	@DBRef
	@CascadeSave
	private List<SkillDemand> demands;

	@Indexed(unique = true)
	private String url;	
	
	public ExternalSource() {
		this(null, new ArrayList<SkillDemand>());
	}
	
	public ExternalSource(String url) {
		this(url, new ArrayList<SkillDemand>());
	}

	public ExternalSource(String url, List <SkillDemand> demands) {
		this.url = url;
		this.demands = demands;
		this.id =  new ObjectId();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<SkillDemand> getDemands() {
		return demands;
	}

	public void setDemands(List<SkillDemand> demands) {
		this.demands = demands;
	}
	
	public void addDemand(SkillDemand demand) {
		if(this.demands == null) {
			this.demands = new ArrayList<SkillDemand>();
		}
		this.demands.add(demand);
	}
	
}
