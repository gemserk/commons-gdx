package com.gemserk.commons.artemis.components;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Component;

public class PropertiesComponent extends Component {
	
	// TODO: should use a Parameters class? should Parameters API be named something like Properties?

	public final Map<String, Object> properties;

	public PropertiesComponent(Map<String, Object> properties) {
		this.properties = properties;
	}

	public PropertiesComponent() {
		this(new HashMap<String, Object>());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String id) {
		return (T) properties.get(id);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String id, T defaultValue) {
		if (!properties.containsKey(id))
			return defaultValue;
		return (T) properties.get(id);
	}

}
