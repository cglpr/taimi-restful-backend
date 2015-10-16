package taimi.backend.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import taimi.backend.event.DataReceivedEvent;

@Component
public class DataEventListener {
	
	@EventListener(condition = "{#event.dataType == T(taimi.backend.data.handler.DataType).JSON}")
	public void handleJsonDataReceivedEvent(DataReceivedEvent event) {
		
		System.out.println("JSON data received.");
	}
	
	@EventListener(condition = "{#event.dataType == T(taimi.backend.data.handler.DataType).JSONArray}")
	public void handleJsonArrDataReceivedEvent(DataReceivedEvent event) {
		
		System.out.println("JSONArray data received.");
	}
	
	@EventListener(condition = "{#event.dataType == T(taimi.backend.data.handler.DataType).XML}")
	public void handleXmlDataReceivedEvent(DataReceivedEvent event) {
		
		System.out.println("XML data received.");
	}
	
	@EventListener(condition = "{#event.dataType == T(taimi.backend.data.handler.DataType).PLAIN_TEXT}")
	public void handleTextDataReceivedEvent(DataReceivedEvent event) {
		
		System.out.println("PLAIN_TEXT data received.");
	}
	
	@EventListener(condition = "{#event.dataType == T(taimi.backend.data.handler.DataType).NULL}")
	public void handleNullDataReceivedEvent(DataReceivedEvent event) {
		System.out.println("NULL data received.");
	}
}
