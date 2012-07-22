package com.gemserk.commons.reflection;

public interface InternalField {
	
	String getFieldName();
	
	Object getValue(Object instance);

	void setValue(Object instance, Object value);

}
