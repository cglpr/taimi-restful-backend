package taimi.domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import taimi.annotation.mongodb.CascadeSave;

@Document(collection = "externalSources")
public class ExternalSource extends MongoDbObject {
	
	@Id
	private ObjectId id;
	
	@Indexed(unique = true)
	private String name;
	
	@DBRef
	@CascadeSave
	private List<SkillDemand> demands;

	private String url;	
	
	public ExternalSource() {
		this("", "", new ArrayList<SkillDemand>());
	}
	
	public ExternalSource(String name, String url) {
		this(name, url, new ArrayList<SkillDemand>());
	}
	
	public ExternalSource(String name, String url, List <SkillDemand> demands) {
		this.name = name;
		this.url = url;
		this.demands = demands;
		this.id =  new ObjectId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public String getCollectionName() throws Exception {
		Annotation annotation = ExternalSource.class.getAnnotation(Document.class);
	    Class<? extends Annotation> type = annotation.annotationType();
	   
	    Method method = type.getDeclaredMethod("collection");
	    String collectionName = (String) method.invoke(annotation, (Object[])null);

	    return collectionName;
	}
}
