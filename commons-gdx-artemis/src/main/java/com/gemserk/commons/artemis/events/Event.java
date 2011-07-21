package com.gemserk.commons.artemis.events;

public class Event {

	private String id;
	private Object source;

	public void setSource(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return source;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

}