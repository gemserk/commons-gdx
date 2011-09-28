package com.gemserk.commons.reflection;

import static org.junit.Assert.*;

import org.junit.Test;


public class InjectorTest {

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
		Injector injector = new Injector();
		MyTemplate myTemplate = new MyTemplate();
		injector.injectMembers(myTemplate);
		assertNull(myTemplate.object);
		assertNull(myTemplate.anotherObject);
	}

	@Test
	public void shouldConfigureInternalObjectWhenItIsOnConfigurator() {
		Injector injector = new Injector();
		Float object = new Float(100f);
		injector.add("object", object);

		MyTemplate myTemplate = new MyTemplate();
		injector.injectMembers(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
		assertNull(myTemplate.anotherObject);
	}

	@Test
	public void shouldConfigureMultipleInternalObjectsWhenOnConfigurator() {
		Injector injector = new Injector();

		Float object = new Float(100f);
		Float anotherObject = new Float(100f);

		injector.add("object", object);
		injector.add("anotherObject", anotherObject);

		MyTemplate myTemplate = new MyTemplate();
		injector.injectMembers(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);

		assertNotNull(myTemplate.anotherObject);
		assertSame(anotherObject, myTemplate.anotherObject);
	}

	@Test
	public void shouldNotFailIfObjectSetterNotFoundWhenObjectOnConfigurator() {
		Injector injector = new Injector();

		Float object = new Float(100f);
		Float anotherObject = new Float(100f);

		injector.add("object", object);
		injector.add("anotherObject", anotherObject);

		MyTemplate2 myTemplate = new MyTemplate2();
		injector.injectMembers(myTemplate);
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
	}

	@Test
	public void shouldNotConfigureIfNotSetterForObjectInConfigurator() {
		Injector injector = new Injector();

		Float object = new Float(100f);

		injector.add("object", object);

		MyTemplate3 myTemplate = new MyTemplate3();
		injector.injectMembers(myTemplate);
		
		assertNotNull(myTemplate.object);
		assertSame(object, myTemplate.object);
	}

	@Test
	public void objectConfiguratorUsageExample1() {

		Injector injector = new Injector() {
			{
				add("object", new Float(100f));
			}
		};

		MyTemplate myTemplate = new MyTemplate();
		injector.injectMembers(myTemplate);

		assertNotNull(myTemplate.object);
	}

	@Test
	public void objectConfiguratorUsageExample2() {

		final Object someValue = new Float(100f);

		Injector injector = new Injector() {
			@Override
			public void injectMembers(Object object) {
				super.injectMembers(object);
				setField(object, "object", someValue);
			}
		};

		MyTemplate myTemplate = new MyTemplate();
		injector.injectMembers(myTemplate);

		assertNotNull(myTemplate.object);
	}
}
