package com.gemserk.commons.reflection;

import java.lang.reflect.Field;

public class InternalFieldDirectImpl implements InternalField {

	protected Field field;
	protected String fieldName;

	public InternalFieldDirectImpl(Field field) {
		this(field.getName(), field);
	}

	public InternalFieldDirectImpl(String fieldName, Field field) {
		this.field = field;
		this.fieldName = fieldName;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public Object getValue(Object obj) {
		try {
			return field.get(obj);
		} catch (Exception e) {
			throw new RuntimeException("Field must be public", e);
		}
	}

	@Override
	public void setValue(Object obj, Object value) {
		try {
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException("Field must be public", e);
		}
	}

}