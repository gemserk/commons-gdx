package com.gemserk.componentsengine.properties;

public class Properties {

	public static <T> PropertyLocator<T> property(String key) {
		return new PropertyLocator<T>(key);
	}

	public static <T> PropertyLocator<T> property(String prefix, String key) {
		return new PropertyLocator<T>(prefix + "." + key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(PropertiesHolder propertiesHolder, String key) {
		Property<T> property = (Property<T>) propertiesHolder.getProperty(key);
		return property != null ? property.get() : null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setValue(PropertiesHolder propertiesHolder, String key, Object value) {
		Property property = propertiesHolder.getProperty(key);
		if (property == null) {
			property = new SimpleProperty<Object>(value);
			propertiesHolder.addProperty(key, property);
		}
		property.set(value);
	}

}
