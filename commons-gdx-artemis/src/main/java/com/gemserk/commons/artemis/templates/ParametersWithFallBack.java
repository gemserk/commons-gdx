package com.gemserk.commons.artemis.templates;

import com.gemserk.componentsengine.utils.Parameters;

public class ParametersWithFallBack implements Parameters {

	private Parameters parameters;
	private Parameters fallBackParameters;

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public void setFallBackParameters(Parameters fallBackParameters) {
		this.fallBackParameters = fallBackParameters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String id) {
		Object o = parameters.get(id);
		if (o == null)
			return fallBackParameters.get(id);
		return (T) o;
	}

	@Override
	public <T> T get(String id, T defaultValue) {
		return parameters.get(id, defaultValue);
	}

	@Override
	public void put(String id, Object value) {
		parameters.put(id, value);
	}

}