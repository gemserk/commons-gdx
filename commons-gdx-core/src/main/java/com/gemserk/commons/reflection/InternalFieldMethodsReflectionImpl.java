package com.gemserk.commons.reflection;

import java.lang.reflect.Method;

public class InternalFieldMethodsReflectionImpl implements InternalField {

	protected Method getterMethod;
	protected Method setterMethod;
	protected String fieldName;

	public InternalFieldMethodsReflectionImpl(String fieldName, Method getterMethod, Method setterMethod) {
		this.fieldName = fieldName;
		this.getterMethod = getterMethod;
		this.setterMethod = setterMethod;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public Object getValue(Object obj) {
		try {
			return getterMethod.invoke(obj, new Object[] {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setValue(Object obj, Object value) {
		try {
			setterMethod.invoke(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}