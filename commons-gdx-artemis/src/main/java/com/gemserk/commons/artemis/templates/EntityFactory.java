package com.gemserk.commons.artemis.templates;

import com.artemis.Entity;
import com.gemserk.componentsengine.utils.Parameters;

public interface EntityFactory {
	
	/**
	 * Creates a new Entity and returns it applying no templates. 
	 */
	Entity instantiate();

	/**
	 * Creates a new Entity and applies the specified EntityTemplate.
	 * 
	 * @param template
	 *            The EntityTemplate to apply to the new Entity.
	 * @return A new Entity with the EntityTemplate applied.
	 */
	Entity instantiate(EntityTemplate template);

	/**
	 * Creates a new Entity and applies the specified EntityTemplate.
	 * 
	 * @param template
	 *            The EntityTemplate to apply to the new Entity.
	 * @param parameters
	 *            The Parameters to use when applying the EntityTemplate to the new Entity.
	 * @return A new Entity with the EntityTemplate applied.
	 */
	Entity instantiate(EntityTemplate template, Parameters parameters);

}