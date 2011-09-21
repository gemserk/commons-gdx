package com.gemserk.componentsengine.properties;

public class SimpleProperty<T extends Object> implements Property<T> {

	protected T value;

	public SimpleProperty() {
		
	}

	public SimpleProperty(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "PROP: " + value;
	}
	
}
