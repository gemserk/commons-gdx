package com.gemserk.commons.artemis.templates;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.artemis.Entity;
import com.gemserk.commons.reflection.ObjectConfigurator;

public class TemplateProviderTest {

	static class TemplateProvider {

		private final ObjectConfigurator objectConfigurator;
		private Map<Class<?>, Object> instances;

		public TemplateProvider(ObjectConfigurator objectConfigurator) {
			this.objectConfigurator = objectConfigurator;
			this.instances = new HashMap<Class<?>, Object>();
		}

		public <T> T get(T t) {
			objectConfigurator.configure(t);
			instances.put(t.getClass(), t);
			return t;
		}

		public <T> T get(Class<? extends T> clazz) {
			if (instances.containsKey(clazz))
				return clazz.cast(instances.get(clazz));
			try {
				T t = clazz.newInstance();
				instances.put(clazz, t);
				return get(t);
			} catch (Exception e) {
				throw new RuntimeException("Failed to create an instance of " + clazz, e);
			}
		}

	}

	static class MyTemplate extends EntityTemplateImpl {

		Object myObject;

		public void setMyObject(Object myObject) {
			this.myObject = myObject;
		}

		@Override
		public void apply(Entity entity) {

		}

	}

	@Test
	public void shouldConfigureTemplate() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();
		objectConfigurator.add("myObject", new Float(100f));

		TemplateProvider templateProvider = new TemplateProvider(objectConfigurator);

		MyTemplate myTemplate = templateProvider.get(new MyTemplate());

		assertNotNull(myTemplate.myObject);
	}
	
	@Test
	public void shouldConfigureTemplateWithNewInstance() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();
		objectConfigurator.add("myObject", new Float(100f));

		TemplateProvider templateProvider = new TemplateProvider(objectConfigurator);

		MyTemplate myTemplate = templateProvider.get(MyTemplate.class);

		assertNotNull(myTemplate);
		assertNotNull(myTemplate.myObject);
	}
	
	@Test
	public void shouldReturnAlreadyConfiguredInstance() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();
		objectConfigurator.add("myObject", new Float(100f));

		TemplateProvider templateProvider = new TemplateProvider(objectConfigurator);

		MyTemplate myTemplate1 = new MyTemplate();
		
		MyTemplate myTemplate2 = templateProvider.get(myTemplate1);
		MyTemplate myTemplate3 = templateProvider.get(MyTemplate.class);

		assertSame(myTemplate1, myTemplate2);
		assertSame(myTemplate1, myTemplate3);
	}

}
