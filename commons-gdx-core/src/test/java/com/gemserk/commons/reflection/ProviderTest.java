package com.gemserk.commons.reflection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ProviderTest {

	static class MyTemplate {

		Object myObject;

		public void setMyObject(Object myObject) {
			this.myObject = myObject;
		}

	}

	@Test
	public void shouldConfigureTemplate() {
		Injector injector = new Injector();
		injector.add("myObject", new Float(100f));

		ProviderImpl providerImpl = new ProviderImpl(injector);

		MyTemplate myTemplate = providerImpl.get(new MyTemplate());

		assertNotNull(myTemplate.myObject);
	}

	@Test
	public void shouldConfigureTemplateWithNewInstance() {
		Injector injector = new Injector();
		injector.add("myObject", new Float(100f));

		ProviderImpl providerImpl = new ProviderImpl(injector);

		MyTemplate myTemplate = providerImpl.get(MyTemplate.class);

		assertNotNull(myTemplate);
		assertNotNull(myTemplate.myObject);
	}

	@Test
	public void shouldReturnAlreadyConfiguredInstance() {
		Injector injector = new Injector();
		injector.add("myObject", new Float(100f));

		ProviderImpl providerImpl = new ProviderImpl(injector);

		MyTemplate myTemplate1 = new MyTemplate();

		MyTemplate myTemplate2 = providerImpl.get(myTemplate1);
		MyTemplate myTemplate3 = providerImpl.get(MyTemplate.class);

		assertSame(myTemplate1, myTemplate2);
		assertSame(myTemplate1, myTemplate3);
	}

}
