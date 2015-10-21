package taimi.backend.data.handler;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

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
	
	// TODO: _Better_ criteria handling :)
	public static int getCount(JSONArray jsonArr, String rule) {
		int cnt = 0;
		
		if(jsonArr == null) {
			return cnt;
		}
		
		if(rule == null || "".equals(rule)) {
			return jsonArr.length();
		}
		
		if(rule.startsWith("GET_VALUE:")) {
			try {
				String name = rule.substring(rule.indexOf(":")+1);
				cnt = jsonArr.getJSONObject(0).getInt(name);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return cnt;
	}
}
