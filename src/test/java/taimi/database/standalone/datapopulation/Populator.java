package taimi.database.standalone.datapopulation;

import org.springframework.beans.factory.annotation.Autowired;
import taimi.backend.domain.SourceURL;
import taimi.backend.domain.Technology;
import taimi.backend.service.MongoDBService;

public class Populator {
	@Autowired 
	MongoDBService mongoDBService;
	
	public static void main(String[] argv) {
		//addTechnology("Java", "EJB", "Spring" "Javascript", "JQuery", "Angularjs", "Perl", "Python", "Ruby", "JBoss", "Tomcat", "Weblogic", "Websphere", "MUMPS");
		//addSourceURL("https://jobs.github.com/positions.json", "?description=<technology>");
		new Populator().addSourceURL("https://api.usa.gov/jobs/search.json", "query=<technology>");
	}
	
	private void addTechnology(String ...techNames) {
		
		for(int i=0; i<techNames.length; i++) {
			Technology tech = new Technology(techNames[i]);
			mongoDBService.saveObject(tech);
		}
	}
	
	private void addSourceURL(String url, String parameters) {
		SourceURL srcurl = new SourceURL(url, parameters);
		mongoDBService.saveObject(srcurl);
	}

}
