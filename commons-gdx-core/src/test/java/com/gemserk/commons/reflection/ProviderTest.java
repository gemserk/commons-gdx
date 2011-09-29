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
	public void shouldConfigureTemplateWithNewInstance() {
		InjectorImpl injectorImpl = new InjectorImpl();
		injectorImpl.configureField("myObject", new Float(100f));

		ProviderImpl providerImpl = new ProviderImpl(injectorImpl);

		MyTemplate myTemplate = providerImpl.getInstance(MyTemplate.class);

		assertNotNull(myTemplate);
		assertNotNull(myTemplate.myObject);
	}

	@Test
	public void shouldReturnAlreadyConfiguredInstance() {
		InjectorImpl injectorImpl = new InjectorImpl();
		injectorImpl.configureField("myObject", new Float(100f));

		ProviderImpl providerImpl = new ProviderImpl(injectorImpl);

		MyTemplate myTemplate2 = providerImpl.getInstance(MyTemplate.class);
		MyTemplate myTemplate3 = providerImpl.getInstance(MyTemplate.class);

		assertSame(myTemplate2, myTemplate3);
	}

}
