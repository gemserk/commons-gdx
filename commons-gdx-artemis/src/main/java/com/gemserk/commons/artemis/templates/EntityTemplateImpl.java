package com.gemserk.commons.artemis.templates;

import com.artemis.Entity;
import com.gemserk.componentsengine.utils.Parameters;

/**
 * Provides a default EntityTemplate implementation to use as base class which contains a local Parameters to be used as default template parameters.
 * 
 * @author acoppes
 * 
 */
public abstract class EntityTemplateImpl implements EntityTemplate {

	/**
	 * Filled in custom entity templates subclasses of this one.
	 */
	protected ParametersWithFallBack parameters = new ParametersWithFallBack();

	@Override
	public void setParameters(Parameters parameters) {
		this.parameters.setParameters(parameters);
	}
	
	@Override
	public void apply(Entity entity, Parameters parameters) {
		setParameters(parameters);
		apply(entity);
	}

}