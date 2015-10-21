package taimi.backend.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "techs")
public class Technology implements MongoDbObject {
	
	@Id
	private ObjectId id;
	
	@Indexed(unique = true)
	private String name;
	
	public Technology() {
		this(null);
	}
	
	public Technology(String name) {
		this.id = new ObjectId();
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ObjectId getId() {
		return this.id;
	}
	
	public void setObjectId(ObjectId id) {
		this.id = id;
	}
	
}