package com.gemserk.commons.artemis.templates;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.componentsengine.utils.Parameters;
import com.gemserk.componentsengine.utils.ParametersWrapper;

public class ParametersWithFallBackTest {

	@Test
	public void shouldReturnObjectFromParametersIfFound() {
		ParametersWithFallBack parametersWithFallBack = new ParametersWithFallBack();
		Parameters parameters = new ParametersWrapper();
		parametersWithFallBack.setParameters(parameters);

		parameters.put("a", 100f);

		Float a = parametersWithFallBack.get("a");
		assertThat(a, IsEqual.equalTo(100f));

	}

	@Test
	public void shouldReturnObjectFromParametersFallbackIfNotFoundOnParameters() {
		ParametersWithFallBack parametersWithFallBack = new ParametersWithFallBack();
		Parameters parameters = new ParametersWrapper();
		parametersWithFallBack.setParameters(parameters);

		parametersWithFallBack.put("a", 100f);

		Float a = parametersWithFallBack.get("a");
		assertThat(a, IsEqual.equalTo(100f));
	}

	@Test
	public void shouldReturnObjectFromParametersIfFoundWithDefaultValue() {
		ParametersWithFallBack parametersWithFallBack = new ParametersWithFallBack();
		Parameters parameters = new ParametersWrapper();
		parametersWithFallBack.setParameters(parameters);

		parameters.put("a", 100f);

		Float a = parametersWithFallBack.get("a", 500f);
		assertThat(a, IsEqual.equalTo(100f));
	}

	@Test
	public void shouldReturnObjectFromParametersFallbackIfNotFoundOnParametersWithDefaultValue() {
		ParametersWithFallBack parametersWithFallBack = new ParametersWithFallBack();
		Parameters parameters = new ParametersWrapper();
		parametersWithFallBack.setParameters(parameters);

		parametersWithFallBack.put("a", 100f);

		Float a = parametersWithFallBack.get("a", 500f);
		assertThat(a, IsEqual.equalTo(100f));
	}

	@Test
	public void shouldReturnDefaultValueIfObjectNotFound() {
		ParametersWithFallBack parametersWithFallBack = new ParametersWithFallBack();
		Parameters parameters = new ParametersWrapper();
		parametersWithFallBack.setParameters(parameters);
		Float a = parametersWithFallBack.get("a", 500f);
		assertThat(a, IsEqual.equalTo(500f));
	}

}
