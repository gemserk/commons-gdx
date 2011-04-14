package com.gemserk.componentsengine.properties;

public class ReferenceProperty<T> implements Property<T> {

	private final Property<T> property;

	public ReferenceProperty(Property<T> property) {
		this.property = property;
	}
	
	@Override
	public T get() {
		return property.get();
	}

	@Override
	public void set(T value) {
		property.set(value);
	}

}
