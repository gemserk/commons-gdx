package com.gemserk.commons.artemis.templates;

import com.artemis.Entity;
import com.gemserk.componentsengine.utils.Parameters;

public interface EntityTemplate {

	/**
	 * Applies a template to the specified Entity.
	 * 
	 * @param entity
	 *            The Entity to apply the template to.
	 */
	void apply(Entity entity);

	/**
	 * Applies a template to the specified Entity.
	 * 
	 * @param entity
	 *            The Entity to apply the template to.
	 * @param parameters
	 *            The parameters to use when applying the template.
	 */
	void apply(Entity entity, Parameters parameters);

	/**
	 * Sets the parameters to be used when applying the template
	 * 
	 * @param parameters
	 *            The Parameters to use when applying the template.
	 */
	void setParameters(Parameters parameters);

}