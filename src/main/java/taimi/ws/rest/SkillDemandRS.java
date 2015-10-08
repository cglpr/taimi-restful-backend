package taimi.ws.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import taimi.domain.SkillDemand;
import web.resource.json.JSONWebSearch;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Warning: Do not use -> s-l-o-w service
 * 
 * @deprecated
 * @author vpotry
 *
 */
@Deprecated
@Path("extskilldemand")
public class SkillDemandRS {
	
	// TODO: from configuration...
	private static final String BASE_URL = "https://jobs.github.com/positions.json";
	
	private enum  TechSkills {Java, Javascript, JQuery, Angularjs, Perl, Ruby, Python};
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List <SkillDemand> getExtSkills() {
    	List <SkillDemand> sdArr = new ArrayList<SkillDemand>();
    	
    	String url = null;
 
    	
    	for(TechSkills val : TechSkills.values()) {
			url = BASE_URL + "?description=" + val.toString();
			JSONArray jsonArr = null;
			
			try {
				jsonArr = JSONWebSearch.readJsonFromUrl(url);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(jsonArr != null) {
				SkillDemand sd = new SkillDemand();
				sd.setTechName(val.toString());
				sd.setDemandCnt(jsonArr.length());
				sdArr.add(sd);
			} 
		}
    	
    	return sdArr;
    }
    

}