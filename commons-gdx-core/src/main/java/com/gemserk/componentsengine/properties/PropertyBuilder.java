package com.gemserk.componentsengine.properties;

public class PropertyBuilder {
	
	public static <T> Property<T> property(T t) {
		return new SimpleProperty<T>(t);
	}

}
