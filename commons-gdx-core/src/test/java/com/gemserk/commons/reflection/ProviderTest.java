package com.gemserk.commons.reflection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ProviderTest {

	static interface Provider {

		<T> T get(T t);
		
		<T> T get(Class<? extends T> clazz);
		
	}

	static class ProviderImpl implements Provider {

		private final ObjectConfigurator objectConfigurator;
		private Map<Class<?>, Object> instances;

		public ProviderImpl(ObjectConfigurator objectConfigurator) {
			this.objectConfigurator = objectConfigurator;
			this.instances = new HashMap<Class<?>, Object>();
		}

		@Override
		public <T> T get(T t) {
			objectConfigurator.configure(t);
			instances.put(t.getClass(), t);
			return t;
		}

		@Override
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

	static class MyTemplate {

		Object myObject;

		public void setMyObject(Object myObject) {
			this.myObject = myObject;
		}

	}

	@Test
	public void shouldConfigureTemplate() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();
		objectConfigurator.add("myObject", new Float(100f));

		ProviderImpl providerImpl = new ProviderImpl(objectConfigurator);

		MyTemplate myTemplate = providerImpl.get(new MyTemplate());

		assertNotNull(myTemplate.myObject);
	}

	@Test
	public void shouldConfigureTemplateWithNewInstance() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();
		objectConfigurator.add("myObject", new Float(100f));

		ProviderImpl providerImpl = new ProviderImpl(objectConfigurator);

		MyTemplate myTemplate = providerImpl.get(MyTemplate.class);

		assertNotNull(myTemplate);
		assertNotNull(myTemplate.myObject);
	}

	@Test
	public void shouldReturnAlreadyConfiguredInstance() {
		ObjectConfigurator objectConfigurator = new ObjectConfigurator();
		objectConfigurator.add("myObject", new Float(100f));

		ProviderImpl providerImpl = new ProviderImpl(objectConfigurator);

		MyTemplate myTemplate1 = new MyTemplate();

		MyTemplate myTemplate2 = providerImpl.get(myTemplate1);
		MyTemplate myTemplate3 = providerImpl.get(MyTemplate.class);

		assertSame(myTemplate1, myTemplate2);
		assertSame(myTemplate1, myTemplate3);
	}

}
