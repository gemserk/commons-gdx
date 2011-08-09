package com.gemserk.commons.artemis.templates;

import com.artemis.Entity;
import com.gemserk.componentsengine.utils.Parameters;


public abstract class EntityTemplateWithDefaultParameters implements EntityTemplate {
	
	protected ParametersWithFallBack parameters = new ParametersWithFallBack();

	@Override
	public void apply(Entity entity, Parameters parameters) {
		this.parameters.setParameters(parameters);
		apply(entity);
	}

}