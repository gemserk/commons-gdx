package com.gemserk.componentsengine.properties;

import java.util.Map;

/**
 * Provides a basic implementation of the PropertiesHolder interface.
 */
public class PropertiesHolderImpl implements PropertiesHolder {

	private Map<String, Property<Object>> properties;
	
	public PropertiesHolderImpl(Map<String, Property<Object>> properties) {
		this.properties = properties;
	}

	@Override
	public void addProperty(String key, Property<Object> value) {
		this.properties.put(key.intern(), value);
	}

	@Override
	public Property<Object> getProperty(String key) {
		return this.properties.get(key);
	}
	
	@Override
	public Map<String, Property<Object>> getProperties() {
		return properties;
	}
	
	@Override
	public String toString() {
		return properties.toString();
	}
	
}