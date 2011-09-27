package com.gemserk.commons.reflection;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.animation4j.reflection.ReflectionUtils;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public class ObjectConfiguratorTest {

	static class ObjectConfigurator {

		protected static final Logger logger = LoggerFactory.getLogger(ObjectConfigurator.class);

		private RandomAccessMap<String, Object> configurationMap;

		public ObjectConfigurator() {
			configurationMap = new RandomAccessMap<String, Object>();
		}

		public void configure(Object object) {

			Class<?> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				Object value = configurationMap.get(fieldName);

				if (value == null) {
					logger.debug("Couldn't find value for field " + fieldName + " from " + clazz);
					continue;
				}

				setField(object, fieldName, value);
			}

		}

		public void setField(Object object, String fieldName, Object value) {
			Class<?> clazz = object.getClass();
			Method setter = ReflectionUtils.getSetter(clazz, fieldName);

			String setterName = ReflectionUtils.getSetterName(fieldName);

			if (setter == null) {
				logger.debug("Failed to set value, " + setterName + "() method not found in " + object.getClass());
				return;
			}

			try {
				setter.invoke(object, value);
			} catch (Exception e) {
				throw new RuntimeException("failed to set value on " + setterName + "() method from " + object.getClass(), e);
			}
		}

		public void add(String name, Object o) {
			configurationMap.put(name, o);
		}

	}

	static class MyTemplate {

		private Object object;
		private Object anotherObject;

		public void setObject(Object object) {
			this.object = object;
		}

		public void setAnotherObject(Object anotherObject) {
			this.anotherObject = anotherObject;
		}

	}

	static class MyTemplate2 {

		private Object object;

		public void setObject(Object object) {
			this.object = object;
		}

	}

	static class MyTemplate3 {

		Object object;

	}

	@Test
	public void shouldNotConfigureInternalObjectWhenNotOnConfigurator() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();
		MyTemplate myTemplate = new MyTemplate();
		objectConfigurator.configure(myTemplate);
		assertNull(myTemplate.object);
		assertNull(myTemplate.anotherObject);
	}

	@Test
	public void shouldConfigureInternalObjectWhenItIsOnConfigurator() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();
		Float object = new Float(100f);
		objectConfigurator.add("object", object);

		MyTemplate myTemplate = new MyTemplate();
		objectConfigurator.configure(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
		assertNull(myTemplate.anotherObject);
	}

	@Test
	public void shouldConfigureMultipleInternalObjectsWhenOnConfigurator() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();

		Float object = new Float(100f);
		Float anotherObject = new Float(100f);

		objectConfigurator.add("object", object);
		objectConfigurator.add("anotherObject", anotherObject);

		MyTemplate myTemplate = new MyTemplate();
		objectConfigurator.configure(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);

		assertNotNull(myTemplate.anotherObject);
		assertSame(anotherObject, myTemplate.anotherObject);
	}

	@Test
	public void shouldNotFailIfObjectSetterNotFoundWhenObjectOnConfigurator() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();

		Float object = new Float(100f);
		Float anotherObject = new Float(100f);

		objectConfigurator.add("object", object);
		objectConfigurator.add("anotherObject", anotherObject);

		MyTemplate2 myTemplate = new MyTemplate2();
		objectConfigurator.configure(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
	}

	@Test
	public void shouldNotConfigureIfNotSetterForObjectInConfigurator() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();

		Float object = new Float(100f);

		objectConfigurator.add("object", object);

		MyTemplate3 myTemplate = new MyTemplate3();
		objectConfigurator.configure(myTemplate);
		assertNull(myTemplate.object);
	}

	@Test
	public void objectConfiguratorUsageExample1() {

		ObjectConfigurator objectConfigurator = new ObjectConfigurator() {
			{
				add("object", new Float(100f));
			}
		};

		MyTemplate myTemplate = new MyTemplate();
		objectConfigurator.configure(myTemplate);

		assertNotNull(myTemplate.object);
	}

	@Test
	public void objectConfiguratorUsageExample2() {

		final Object someValue = new Float(100f);

		ObjectConfigurator objectConfigurator = new ObjectConfigurator() {
			@Override
			public void configure(Object object) {
				super.configure(object);
				setField(object, "object", someValue);
			}
		};

		MyTemplate myTemplate = new MyTemplate();
		objectConfigurator.configure(myTemplate);

		assertNotNull(myTemplate.object);
	}
}
