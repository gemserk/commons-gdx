package com.gemserk.commons.artemis.templates;

import java.util.Map;

import com.gemserk.componentsengine.utils.Parameters;
import com.gemserk.componentsengine.utils.ParametersWrapper;

/**
 * Another fallback implementation but inverse, it tries to get the parameter from the local parameters first and if it hasn't an object for the specified value then it tries with the fallback.
 * 
 * @author acoppes
 * 
 */
public class ParametersDelegateImpl implements Parameters {

	Parameters delegate = new ParametersWrapper();
	Parameters internal = new ParametersWrapper();

	public Parameters setDelegate(Parameters delegate) {
		this.delegate = delegate;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String id) {
		Object object = internal.get(id);
		if (object == null)
			return delegate.get(id);
		return (T) object;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String id, T defaultValue) {
		Object object = internal.get(id);
		if (object == null)
			return delegate.get(id, defaultValue);
		return (T) object;
	}

	@Override
	public Parameters put(String id, Object value) {
		return internal.put(id, value);
	}

	@Override
	public Parameters putAll(Map<String, Object> values) {
		return internal.putAll(values);
	}

	@Override
	public void clear() {
		internal.clear();
	}

	@Override
	public Parameters remove(String id) {
		internal.remove(id);
		return this;
	}

}