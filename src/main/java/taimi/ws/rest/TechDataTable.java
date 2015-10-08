package taimi.ws.rest;

import java.util.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import taimi.database.search.MongoDBSearch;
import taimi.domain.ExternalSource;
import taimi.visualization.DataTableMaker;

import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.render.JsonRenderer;

/**
 * Provides google visialization DataTable 
 * json-data as rest service.
 * 
 * @author vpotry
 *
 */
@Path("techdata")
public class TechDataTable {
	
	@GET
	@Path("/testdata")
	@Produces(MediaType.TEXT_PLAIN)
	public String getStaticDataTable() {
		return "{" +
				 "\"cols\": [" +
			        "{\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"}," +
			        "{\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}" +
			      "]," +
			  "\"rows\": [" +
			        "{\"c\":[{\"v\":\"Mushrooms\",\"f\":null},{\"v\":3,\"f\":null}]}," +
			        "{\"c\":[{\"v\":\"Onions\",\"f\":null},{\"v\":1,\"f\":null}]}," +
			        "{\"c\":[{\"v\":\"Olives\",\"f\":null},{\"v\":1,\"f\":null}]}," +
			        "{\"c\":[{\"v\":\"Zucchini\",\"f\":null},{\"v\":1,\"f\":null}]}," +
			        "{\"c\":[{\"v\":\"Pepperoni\",\"f\":null},{\"v\":2,\"f\":null}]}" +
			      "]" +
				"}";
	}
	
	@GET
	@Path("/demand")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTechDataTable() {
		return toJsonString(getDataTable());
	}
	
	@GET
	@Path("/testdemand")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTechTestDataTable() {
		return toJsonString(getTestDataTable());
	}
	
	private String toJsonString(DataTable dt) {
		JsonNode root = null;
        String json = JsonRenderer.renderDataTable(dt, true, false, false).toString();

        try{
            JsonParser parser = new JsonFactory().createJsonParser(json)
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
                 root = new ObjectMapper().readTree(parser);
            }catch(Exception ex){
               ex.printStackTrace();
            }
        
        return root.toString();
	}
	
	/**
	 * Get datatable data from storage
	 * 
	 * @return DataTable
	 */
	private DataTable getDataTable() {
		List <ExternalSource> lst = MongoDBSearch.getAllSkillDemands();
		
		ColumnDescription[] columns = {new ColumnDescription("techname", ValueType.TEXT, "Technology"),  
									new ColumnDescription("count", ValueType.NUMBER, "Demand #")};
		DataTable dt = DataTableMaker.makeDataTable(lst, columns);
		
		return dt;
	}

	private DataTable getTestDataTable() {
		DataTable dt = new DataTable();
		
		List<ColumnDescription> columns = new ArrayList<ColumnDescription>();  
		columns.add(new ColumnDescription("techname", ValueType.TEXT, "Technology"));  
		columns.add(new ColumnDescription("count", ValueType.NUMBER, "Demand#"));

        dt.addColumns(columns);  
        
		try {
			dt.addRowFromValues("Java", 50);
			dt.addRowFromValues("Perl", 25);
			dt.addRowFromValues("JQuery", 30);
			dt.addRowFromValues("JBoss", 15);
		} catch (TypeMismatchException ex) {
			ex.printStackTrace();
		}
		
		return dt;
	}
}
