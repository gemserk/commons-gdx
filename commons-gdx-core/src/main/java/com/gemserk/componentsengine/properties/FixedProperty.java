package com.gemserk.componentsengine.properties;

/**
 * Implements Property and depends on a specific holder set on creation time. Used to implement anonymous inner classes with custom behavior.
 * note: added to replace InnerProperty.
 */
public class FixedProperty implements Property<Object> {

	private final PropertiesHolder holder;

	public FixedProperty(PropertiesHolder propertiesHolder) {
		this.holder = propertiesHolder;
	}
	
	protected PropertiesHolder getHolder() {
		return holder;
	}
	
	public Object get() {
		return null;
	}

	public void set(Object value) {

	}

	@Override
	public String toString() {
		Object object = get();
		if (object != null)
			return "FIXEDPROP: " + object.toString();
		return "FIXEDPROP: null value";
	}

}