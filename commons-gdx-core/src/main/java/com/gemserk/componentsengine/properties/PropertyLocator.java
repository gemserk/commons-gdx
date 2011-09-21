package com.gemserk.componentsengine.properties;

/**
 * Provides a way to retrieve a Property from a PropertiesHolder
 */
@SuppressWarnings("unchecked")
public class PropertyLocator<T> {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.intern();
	}

	public PropertyLocator(String name) {
		this.name = name.intern();
	}

	public Property<T> get(PropertiesHolder propertiesHolder) {
		return (Property<T>) propertiesHolder.getProperty(name);
	}

	public T getValue(PropertiesHolder propertiesHolder) {
		Property<T> prop = get(propertiesHolder);

		return prop != null ? prop.get() : null;
	}

	public T getValue(PropertiesHolder propertiesHolder, T defaultValue) {
		T t = getValue(propertiesHolder);

		return t != null ? t : defaultValue;
	}

	public void setValue(PropertiesHolder propertiesHolder, T value) {
		Property<Object> property = (Property<Object>) get(propertiesHolder);
		if (property == null) {
			property = new SimpleProperty<Object>(value);
			propertiesHolder.addProperty(name, property);
		}
		property.set(value);
	}

}
