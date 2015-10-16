package taimi.backend.event;

import org.springframework.context.ApplicationEvent;

import taimi.backend.data.handler.DataType;

public class DataReceivedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private Object dataObject;
	
	private DataType dataType;
	
	public DataReceivedEvent(Object source) {
		super(source);
		dataType = DataType.NULL;
	}

	public DataReceivedEvent(Object source, Object dataObject) {
		super(source);
		this.setDataObject(dataObject);
		this.setDataType(DataType.resolveDataType(dataObject));
	}

	public Object getDataObject() {
		return dataObject;
	}

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}
	
	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
}
