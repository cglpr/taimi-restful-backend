package taimi.backend.controller;

import java.util.*;

import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import taimi.backend.data.handler.DataTableMaker;
import taimi.backend.data.handler.JsonHandler;
import taimi.backend.domain.ExternalSource;
import taimi.backend.service.MongoDBService;

import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;


/**
 * Provides google visialization DataTable 
 * json-data as rest service.
 * 
 * @author vpotry
 *
 */
@RestController
@RequestMapping("/rest/skilldemand")
public class GraphController {
	
	@Autowired 
	private MongoDBService mongoDBService;
	
	@RequestMapping(value = "/googlechart", method = RequestMethod.GET)
	public String getGoogleChart() {
		List <ExternalSource> lst = mongoDBService.getAllSkillDemands();
		
		ColumnDescription[] columns = {new ColumnDescription("techname", ValueType.TEXT, "Technology"),  
									new ColumnDescription("count", ValueType.NUMBER, "Demand #")};
		DataTable dt = DataTableMaker.makeDataTable(lst, columns);
		
		return JsonHandler.toJsonString(dt);
	}
	
	@RequestMapping(value = "/nvd3", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List <ExternalSource> getNvd3Chart() {
		List <ExternalSource> lst = mongoDBService.getAllSkillDemands();

        return lst;
	}

}
