package com.gemserk.commons.artemis.components;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.EntityManager;

public class ComponentMapperInitHelper {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void config(Object target, EntityManager entityManager) {
		try {
			for (Field field : target.getClass().getDeclaredFields()) {
				String fieldName = field.getName();
				Class<?> fieldType = field.getType();
				if (ComponentMapper.class.isAssignableFrom(fieldType)) {
					System.out.println("ComponentMapper detected: " + fieldName);
					ParameterizedType genericType = (ParameterizedType) field.getGenericType();
					Class componentType = (Class) genericType.getActualTypeArguments()[0];

					ComponentMapper<Component> componentMapper = new ComponentMapper<Component>(componentType, entityManager);
					field.setAccessible(true);
					field.set(target, componentMapper);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while setting component mappers", e);
		}
	}

}
