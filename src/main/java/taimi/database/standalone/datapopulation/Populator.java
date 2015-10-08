package taimi.database.standalone.datapopulation;

import taimi.database.spring.MongoSpringClient;
import taimi.domain.SourceURL;
import taimi.domain.Technology;

public class Populator {
	public static void main(String[] argv) {
		//addTechnology("Java", "EJB", "Spring" "Javascript", "JQuery", "Angularjs", "Perl", "Python", "Ruby", "JBoss", "Tomcat", "Weblogic", "Websphere", "MUMPS");
		//addSourceURL("jobs.github.com/positions.json", "?description=<technology>");
	}
	
	private static void addTechnology(String ...techNames) {
		
		for(int i=0; i<techNames.length; i++) {
			Technology tech = new Technology(techNames[i]);
			MongoSpringClient.saveObject(tech);
		}
	}
	
	private static void addSourceURL(String url, String parameters) {
		SourceURL srcurl = new SourceURL(url, parameters);
		MongoSpringClient.saveObject(srcurl);
	}

}
