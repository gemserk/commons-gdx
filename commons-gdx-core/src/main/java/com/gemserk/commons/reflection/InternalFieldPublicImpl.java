package com.gemserk.commons.reflection;

import java.lang.reflect.Field;

public class InternalFieldPublicImpl implements InternalField {

	protected Field field;
	protected String fieldName;

	public InternalFieldPublicImpl(Field field) {
		this(field.getName(), field);
	}

	public InternalFieldPublicImpl(String fieldName, Field field) {
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