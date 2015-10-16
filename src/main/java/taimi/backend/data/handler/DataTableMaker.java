package taimi.backend.data.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import taimi.backend.domain.ExternalSource;
import taimi.backend.domain.SkillDemand;

import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;

/**
 * Creates google DataTable
 * 
 * @author vpotry
 *
 */
public class DataTableMaker {
	public static DataTable makeDataTable(List <ExternalSource> srcData, ColumnDescription... columns) {
		return makeDataTable(srcData, Arrays.asList(columns));
	}
	
	public static DataTable makeDataTable(List <ExternalSource> srcData, List <ColumnDescription> columns) {
		DataTable dt = new DataTable();
		
		dt.addColumns(columns);
		
		if(srcData != null && srcData.size() > 0) {
			Map <String, Integer> data = combine(srcData);
			addRows(dt, data);
		}	
		
		return dt;
	}
	
	private static Map<String, Integer> combine(List <ExternalSource> srcData) {
		Map <String, Integer> data = new HashMap<String, Integer>();
		
		for(ExternalSource es : srcData) {
			List <SkillDemand> lst = es.getDemands();
			if(lst != null) {
				for(SkillDemand sd : lst ) {
					if(data.containsKey(sd.getTechName())) {
						int cnt = data.get(sd.getTechName());
						data.put(sd.getTechName(), cnt + sd.getDemandCnt());
					} else {
						data.put(sd.getTechName(), sd.getDemandCnt());
					}
				}
			}
		}
		
		return data;
	}
	
	private static void addRows(DataTable dt, Map <String, Integer> data) {
		try {
			Set <String> keys = data.keySet();
			
			for(String key : keys) {
				dt.addRowFromValues(key, data.get(key));
			}
		} catch (TypeMismatchException ex) {
			ex.printStackTrace();
		}
	}

}
