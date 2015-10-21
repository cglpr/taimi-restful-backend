package taimi.backend.controller;

import java.util.*;

import org.apache.log4j.Logger;
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
	
	private final Logger logger = Logger.getLogger(GraphController.class);
	
	@Autowired 
	private MongoDBService mongoDBService;
	
	@RequestMapping(value = "/googlechart", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String getGoogleChart() {
		logger.debug("google chart data requested");
		List <ExternalSource> lst = mongoDBService.getAllSkillDemands();
		logger.debug("Data size: " + ((lst != null)?lst.size():"[null]"));
		
		ColumnDescription[] columns = {new ColumnDescription("techname", ValueType.TEXT, "Technology"),  
									new ColumnDescription("count", ValueType.NUMBER, "Demand #")};
		DataTable dt = DataTableMaker.makeDataTable(lst, columns);
		
		return JsonHandler.toJsonString(dt);
	}
	
	@RequestMapping(value = "/nvd3", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List <ExternalSource> getNv3dChartData() {
		logger.debug("Angular/nvd3 data requested");
		List <ExternalSource> lst = mongoDBService.getAllSkillDemands();
		// TODO: Format for nv3d specific data
		logger.debug("Data size: " + ((lst != null)?lst.size():"[null]"));
        return lst;
	}
	
	@RequestMapping(value = "/general", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List <ExternalSource> getSkillDemandData() {
		logger.debug("(Unformatted) skilldemand data requested");
		List <ExternalSource> lst = mongoDBService.getAllSkillDemands();
		logger.debug("Data size: " + ((lst != null)?lst.size():"[null]"));
        return lst;
	}
	
	

}
