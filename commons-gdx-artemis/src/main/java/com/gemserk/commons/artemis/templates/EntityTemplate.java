package com.gemserk.commons.artemis.templates;

import com.artemis.Entity;
import com.gemserk.componentsengine.utils.Parameters;

public interface EntityTemplate {

	/**
	 * Returns the default Parameters of this template, not sure it should be on the API.
	 */
	Parameters getDefaultParameters();

	/**
	 * Applies a template to the specified Entity.
	 * 
	 * @param entity
	 *            The Entity to apply the template to.
	 */
	void apply(Entity entity);

	/**
	 * Applies the current template to the specified Entity.
	 * 
	 * @param entity
	 *            The Entity to apply the template to.
	 * @param parameters
	 *            The Parameters to use when applying the template.
	 */
	void apply(Entity entity, Parameters parameters);

}