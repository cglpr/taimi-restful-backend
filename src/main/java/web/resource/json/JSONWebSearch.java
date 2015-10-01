package web.resource.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Gets JSON from URLs
 * 
 * @author vpotry
 * 
 * @version 0.9
 * 
 */
public class JSONWebSearch {

	  private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      
	      Object json = new JSONTokener(jsonText).nextValue();
	      JSONArray jsonList = null;
	      
	      if(json instanceof JSONObject) {
	    	  jsonList = new JSONArray();
	    	  jsonList.put(new JSONObject(jsonText));
	      } else if(json instanceof JSONArray) {
	    	  jsonList = new JSONArray(jsonText);
	      }
	      
	      return jsonList;
	    } finally {
	      is.close();
	    }
	  }

	  public static void main(String[] argv) throws IOException, JSONException {
		  JSONArray json = readJsonFromUrl("https://jobs.github.com/positions.json?description=java");
	    System.out.println(json.toString());
	   
	  }
	}