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
@Document(collection = "sourceUrls")
public class SourceURL implements MongoDbObject {
	
	@Id
	private ObjectId id;
	@Indexed(unique = true)
	private String url;
	private String parameters;
	
	private String criteria;
	
	public SourceURL() {
		this(null, null);
	}
	
	public SourceURL(String url, String parameters) {
		this(url, parameters, null);
	}
	
	public SourceURL(String url, String parameters, String criteria) {
		this.id = new ObjectId();
		this.url = url;
		this.setParameters(parameters);
		this.setCriteria(criteria);
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
}