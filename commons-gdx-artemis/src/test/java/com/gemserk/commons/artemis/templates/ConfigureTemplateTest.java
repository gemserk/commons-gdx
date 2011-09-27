package com.gemserk.commons.artemis.templates;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Entity;
import com.gemserk.animation4j.reflection.ReflectionUtils;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public class ConfigureTemplateTest {

	protected static final Logger logger = LoggerFactory.getLogger(ConfigureTemplateTest.TemplateConfiguratorMapImpl.class);

	public static class TemplatesReflectionUtils {
		public static void setField(Object object, String fieldName, Object value) {
			Class<?> clazz = object.getClass();
			Method setter = ReflectionUtils.getSetter(clazz, fieldName);

			String setterName = ReflectionUtils.getSetterName(fieldName);

			if (setter == null)
				throw new RuntimeException(setterName + "() method not found in " + object.getClass());

			try {
				setter.invoke(object, value);
			} catch (Exception e) {
				throw new RuntimeException("failed to set value on " + setterName + "() method from " + object.getClass(), e);
			}
		}
	}

	interface TemplateConfigurator {

		void configure(EntityTemplate entityTemplate);

	}

	class TemplateConfiguratorMapImpl implements TemplateConfigurator {

		private RandomAccessMap<String, Object> configurationMap;

		public TemplateConfiguratorMapImpl() {
			configurationMap = new RandomAccessMap<String, Object>();
		}

		public void configure(EntityTemplate entityTemplate) {
			// for (int i = 0; i < configurationMap.size(); i++) {
			// String name = configurationMap.getKey(i);
			// Object object = configurationMap.get(i);
			// TemplatesReflectionUtils.setField(entityTemplate, name, object);
			// }

			Class<? extends EntityTemplate> clazz = entityTemplate.getClass();
			Field[] fields = clazz.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				Object value = configurationMap.get(fieldName);

				if (value == null) {
					logger.debug("Couldn't find value for field " + fieldName + " from class " + clazz);
					continue;
				}

				TemplatesReflectionUtils.setField(entityTemplate, fieldName, value);
			}

		}

		public void add(String name, Object o) {
			configurationMap.put(name, o);
		}

	}

	class TemplateProvider {

		TemplateConfiguratorMapImpl templateConfiguratorMapImpl;

		public TemplateProvider(TemplateConfiguratorMapImpl templateConfiguratorMapImpl) {
			this.templateConfiguratorMapImpl = templateConfiguratorMapImpl;
		}

		public void register(EntityTemplate entityTemplate) {
			templateConfiguratorMapImpl.configure(entityTemplate);
		}

	}

	static class MyTemplate extends EntityTemplateImpl {

		private Object object;
		private Object anotherObject;

		public void setObject(Object object) {
			this.object = object;
		}

		public void setAnotherObject(Object anotherObject) {
			this.anotherObject = anotherObject;
		}

		@Override
		public void apply(Entity entity) {

		}

	}

	static class MyTemplate2 extends EntityTemplateImpl {

		private Object object;

		public void setObject(Object object) {
			this.object = object;
		}

		@Override
		public void apply(Entity entity) {

		}

	}

	@Test
	public void shouldNotConfigureInternalObjectWhenNotOnConfigurator() {
		TemplateConfiguratorMapImpl templateConfiguratorMapImpl = new TemplateConfiguratorMapImpl();
		MyTemplate myTemplate = new MyTemplate();
		templateConfiguratorMapImpl.configure(myTemplate);
		assertNull(myTemplate.object);
		assertNull(myTemplate.anotherObject);
	}

	@Test
	public void shouldConfigureInternalObjectWhenItIsOnConfigurator() {
		TemplateConfiguratorMapImpl templateConfiguratorMapImpl = new TemplateConfiguratorMapImpl();
		Float object = new Float(100f);
		templateConfiguratorMapImpl.add("object", object);

		MyTemplate myTemplate = new MyTemplate();
		templateConfiguratorMapImpl.configure(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
		assertNull(myTemplate.anotherObject);
	}

	@Test
	public void shouldConfigureMultipleInternalObjectsWhenOnConfigurator() {
		TemplateConfiguratorMapImpl templateConfiguratorMapImpl = new TemplateConfiguratorMapImpl();

		Float object = new Float(100f);
		Float anotherObject = new Float(100f);

		templateConfiguratorMapImpl.add("object", object);
		templateConfiguratorMapImpl.add("anotherObject", anotherObject);

		MyTemplate myTemplate = new MyTemplate();
		templateConfiguratorMapImpl.configure(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);

		assertNotNull(myTemplate.anotherObject);
		assertSame(anotherObject, myTemplate.anotherObject);
	}

	@Test
	public void shouldNotFailIfObjectSetterNotFoundWhenObjectOnConfigurator() {
		TemplateConfiguratorMapImpl templateConfiguratorMapImpl = new TemplateConfiguratorMapImpl();

		Float object = new Float(100f);
		Float anotherObject = new Float(100f);

		templateConfiguratorMapImpl.add("object", object);
		templateConfiguratorMapImpl.add("anotherObject", anotherObject);

		MyTemplate2 myTemplate = new MyTemplate2();
		templateConfiguratorMapImpl.configure(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
	}
}
