package com.gemserk.commons.artemis.templates;

import com.gemserk.componentsengine.utils.Parameters;


public abstract class EntityTemplateWithDefaultParameters implements EntityTemplate {
	
	protected ParametersWithFallBack parameters = new ParametersWithFallBack();

	@Override
	public void setParameters(Parameters parameters) {
		this.parameters.setParameters(parameters);
	}

}