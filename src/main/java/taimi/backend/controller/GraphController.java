package taimi.backend.controller;

import java.util.*;

import org.springframework.web.bind.annotation.RestController; 
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import taimi.backend.data.handler.DataTableMaker;
import taimi.backend.data.handler.JsonHandler;
import taimi.backend.domain.ExternalSource;
import taimi.backend.service.MongoDBService;

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
@RestController
@RequestMapping("/rest/graph")
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
}
