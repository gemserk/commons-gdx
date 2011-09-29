package com.gemserk.commons.reflection;

import static org.junit.Assert.*;

import org.junit.Test;


public class InjectorImplTest {

	private static class MyTemplate {

		private Object object;
		private Object anotherObject;

		public void setObject(Object object) {
			this.object = object;
		}

		public void setAnotherObject(Object anotherObject) {
			this.anotherObject = anotherObject;
		}

	}

	private static class MyTemplate2 {

		private Object object;

		public void setObject(Object object) {
			this.object = object;
		}

	}

	private static class MyTemplate3 {

		Object object;

	}

	@Test
	public void shouldNotConfigureInternalObjectWhenNotOnConfigurator() {
		Injector injectorImpl = new InjectorImpl();
		MyTemplate myTemplate = new MyTemplate();
		injectorImpl.injectMembers(myTemplate);
		assertNull(myTemplate.object);
		assertNull(myTemplate.anotherObject);
	}

	@Test
	public void shouldConfigureInternalObjectWhenItIsOnConfigurator() {
		InjectorImpl injectorImpl = new InjectorImpl();
		Float object = new Float(100f);
		injectorImpl.configureField("object", object);

		MyTemplate myTemplate = new MyTemplate();
		injectorImpl.injectMembers(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
		assertNull(myTemplate.anotherObject);
	}

	@Test
	public void shouldConfigureMultipleInternalObjectsWhenOnConfigurator() {
		InjectorImpl injectorImpl = new InjectorImpl();

		Float object = new Float(100f);
		Float anotherObject = new Float(100f);

		injectorImpl.configureField("object", object);
		injectorImpl.configureField("anotherObject", anotherObject);

		MyTemplate myTemplate = new MyTemplate();
		injectorImpl.injectMembers(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);

		assertNotNull(myTemplate.anotherObject);
		assertSame(anotherObject, myTemplate.anotherObject);
	}

	@Test
	public void shouldNotFailIfObjectSetterNotFoundWhenObjectOnConfigurator() {
		InjectorImpl injectorImpl = new InjectorImpl();

		Float object = new Float(100f);
		Float anotherObject = new Float(100f);

		injectorImpl.configureField("object", object);
		injectorImpl.configureField("anotherObject", anotherObject);

		MyTemplate2 myTemplate = new MyTemplate2();
		injectorImpl.injectMembers(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
	}

	@Test
	public void shouldNotConfigureIfNotSetterForObjectInConfigurator() {
		InjectorImpl injectorImpl = new InjectorImpl();

		Float object = new Float(100f);

		injectorImpl.configureField("object", object);

		MyTemplate3 myTemplate = new MyTemplate3();
		injectorImpl.injectMembers(myTemplate);
		
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
	}

	@Test
	public void objectConfiguratorUsageExample1() {

		Injector injectorImpl = new InjectorImpl() {
			{
				configureField("object", new Float(100f));
			}
		};

		MyTemplate myTemplate = new MyTemplate();
		injectorImpl.injectMembers(myTemplate);

		assertNotNull(myTemplate.object);
	}

	@Test
	public void objectConfiguratorUsageExample2() {

		final Object someValue = new Float(100f);

		Injector injectorImpl = new InjectorImpl() {
			@Override
			public void injectMembers(Object object) {
				super.injectMembers(object);
				setField(object, "object", someValue);
			}
		};

		MyTemplate myTemplate = new MyTemplate();
		injectorImpl.injectMembers(myTemplate);

		assertNotNull(myTemplate.object);
	}
}