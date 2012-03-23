package com.gemserk.commons.artemis.templates.scenes;

import com.gemserk.commons.artemis.WorldWrapper;
import com.gemserk.componentsengine.utils.Parameters;

/**
 * Represents the concept of a fixed template for a scene of a game.
 * 
 * @author acoppes
 * 
 */
public interface SceneTemplate {

	/**
	 * Applies the scene template to the Artemis World stored in the WorldWrapper.
	 * 
	 * @param scene
	 */
	void apply(WorldWrapper scene);

	/**
	 * Returns an instance of Parameters to configure the needed parameters to apply the template.
	 */
	Parameters getParameters();

}
