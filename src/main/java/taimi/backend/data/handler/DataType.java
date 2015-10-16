package taimi.backend.data.handler;

import org.json.JSONArray;
import org.json.JSONObject;

public enum DataType {
	
	JSON, JSONArray, XML, PLAIN_TEXT, OTHER, NULL;
	
	public static DataType resolveDataType(Object o) {
		DataType t = NULL;
		
		if(o != null) {
			if(o instanceof JSONArray) {
				t = JSONArray;
			} else if(o instanceof JSONObject) {
				t = JSON;
			} else {
				String str = o.toString();
				System.out.println(str);
				// Tha check could be bettah :)
				if(str.matches("<\\w.*>\\w.*</\\w.*>")) {
					t = XML;
				} else {
					t = PLAIN_TEXT;
				}
				
			}
		}
		return t;
	}
}
