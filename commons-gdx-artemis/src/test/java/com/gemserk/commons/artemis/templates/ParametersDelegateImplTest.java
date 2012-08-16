package com.gemserk.commons.artemis.templates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.junit.Test;

import com.gemserk.componentsengine.utils.ParametersWrapper;

public class ParametersDelegateImplTest {

	@Test
	public void shouldReturnParameterFromDelegate() {
		ParametersWrapper parameters = new ParametersWrapper();
		parameters.put("a", "b");

		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		parametersDelegate.setDelegate(parameters);

		assertEquals("b", parametersDelegate.get("a"));
	}

	@Test
	public void shouldReturnParameterFromItself() {
		ParametersWrapper parameters = new ParametersWrapper();

		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		parametersDelegate.setDelegate(parameters);
		parametersDelegate.put("a", "b");

		assertEquals("b", parametersDelegate.get("a"));
		assertNull(parameters.get("a"));
	}

	@Test
	public void shouldReturnParameterFromDelegate2() {
		ParametersWrapper parameters = new ParametersWrapper();
		parameters.put("a", "b");

		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		parametersDelegate.setDelegate(parameters);

		assertEquals("b", parametersDelegate.get("a", "c"));
	}

	@Test
	public void shouldReturnParameterFromItself2() {
		ParametersWrapper parameters = new ParametersWrapper();

		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		parametersDelegate.setDelegate(parameters);
		parametersDelegate.put("a", "b");

		assertEquals("b", parametersDelegate.get("a", "c"));
		assertNull(parameters.get("a"));
	}

	@Test
	public void shouldReturnDefaultValue() {
		ParametersWrapper parameters = new ParametersWrapper();

		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		parametersDelegate.setDelegate(parameters);

		assertEquals("c", parametersDelegate.get("a", "c"));
	}

	@Test
	public void shouldNotFailIfDelegateIsNull() {
		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		assertEquals(null, parametersDelegate.get("a"));
	}

	@Test
	public void shouldNotFailIfDelegateIsNull2() {
		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		assertEquals("c", parametersDelegate.get("a", "c"));
	}

	@Test
	public void shouldNotClearDelegateWhenClear() {
		ParametersWrapper parameters = new ParametersWrapper();
		parameters.put("a", "b");

		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		parametersDelegate.setDelegate(parameters);
		parametersDelegate.put("c", "d");

		parametersDelegate.clear();
		
		assertEquals("b", parameters.get("a"));
		assertEquals("b", parametersDelegate.get("a"));
		assertEquals(null, parametersDelegate.get("c"));
	}

	@Test
	public void shouldPutAllFromMap() {
		ParametersWrapper parameters = new ParametersWrapper();

		ParametersDelegateImpl parametersDelegate = new ParametersDelegateImpl();
		parametersDelegate.setDelegate(parameters);

		HashMap<String, Object> objects = new HashMap<String, Object>();
		
		objects.put("a", "b");
		objects.put("c", "d");
		
		parametersDelegate.putAll(objects);

		assertEquals("b", parametersDelegate.get("a"));
		assertEquals("d", parametersDelegate.get("c"));
	}
}
