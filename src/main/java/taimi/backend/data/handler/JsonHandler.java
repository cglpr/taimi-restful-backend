package taimi.backend.data.handler;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.render.JsonRenderer;

public class JsonHandler {
	public static String toJsonString(DataTable dt) {
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
}
