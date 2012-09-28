package com.gemserk.commons.artemis.templates;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.gemserk.componentsengine.utils.Parameters;

/**
 * A Parameters implementation that tires to get object from other Parameters instance first, if they fail then it tries to load them from a fall back parameters map.
 * 
 * @author acoppes
 * 
 */
public class ParametersWithFallBack implements Parameters {
	
	private Map<String, Object> fallBackParameters;
	private Parameters parameters;
	
	/**
	 * Sets the Parameters instance to get items from.
	 * 
	 * @param parameters
	 */
	public void setParameters(Parameters parameters) {
		if (parameters == null)
			throw new IllegalArgumentException("fallback parameters can't be null");
		this.parameters = parameters;
	}

	public ParametersWithFallBack() {
		fallBackParameters = new HashMap<String, Object>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String id) {
		Object o = parameters.get(id);
		if (o == null)
			return (T) fallBackParameters.get(id);
		return (T) o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String id, T defaultValue) {
		Object o = get(id);
		if (o == null)
			return defaultValue;
		return (T) o;
	}

	@Override
	public Parameters put(String id, Object value) {
		fallBackParameters.put(id, value);
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
		fallBackParameters.clear();
		if (parameters != null)
			parameters.clear();
	}

	@Override
	public Parameters remove(String id) {
		fallBackParameters.remove(id);
		return this;
	}

}