package com.gemserk.commons.reflection;

import static org.junit.Assert.*;

import org.junit.Test;


public class ObjectConfiguratorTest {

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
