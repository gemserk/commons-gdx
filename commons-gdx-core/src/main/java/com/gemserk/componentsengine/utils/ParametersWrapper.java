package com.gemserk.componentsengine.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Lets you work with a map<string, object> in an easy way, using generic methods.
 */
public class ParametersWrapper implements Parameters {

	private Map<String, Object> wrappedParameters;

	/**
	 * Sets the map from where the properties will be extracted.
	 * 
	 * @param wrappedParameters
	 *            The new map with properties to be extracted.
	 */
	public void setWrappedParameters(Map<String, Object> wrappedParameters) {
		this.wrappedParameters = wrappedParameters;
	}

	/**
	 * Returns the original map from where the properties are being extracted.
	 */
	public Map<String, Object> getWrappedParameters() {
		return wrappedParameters;
	}

	/**
	 * Builds a new ParametersWrapper using a new instance of a HashMap<String, Object>
	 */
	public ParametersWrapper() {
		this(new HashMap<String, Object>());
	}

	/**
	 * Builds a new ParametersWrapper using the specified map of parameters.
	 */
	public ParametersWrapper(Map<String, Object> wrappedParameters) {
		this.wrappedParameters = wrappedParameters;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String id) {
		return (T) wrappedParameters.get(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String id, T defaultValue) {
		if (!wrappedParameters.containsKey(id))
			return defaultValue;
		return (T) wrappedParameters.get(id);
	}

	@Override
	public Parameters put(String id, Object value) {
		wrappedParameters.put(id, value);
		return this;
	}

	@Override
	public Parameters putAll(Map<String, Object> values) {
		Set<String> keySet = values.keySet();
		for (String key : keySet) 
			put(key, values.get(key));
		return this;
	}

	@Override
	public void clear() {
		wrappedParameters.clear();
	}

}