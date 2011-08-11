package com.gemserk.commons.artemis.events;

// Event should be named Message to try to match all the ComponentsEngine paradigm. 

public class Event {

	private String id;
	private Object source;

	public void setSource(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return source;
	}
	
	// public <T> T getSource() { .. }
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

}