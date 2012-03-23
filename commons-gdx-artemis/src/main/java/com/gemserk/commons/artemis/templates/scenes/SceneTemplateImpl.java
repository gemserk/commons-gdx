package com.gemserk.commons.artemis.templates.scenes;

import com.gemserk.commons.artemis.WorldWrapper;
import com.gemserk.componentsengine.utils.Parameters;
import com.gemserk.componentsengine.utils.ParametersWrapper;

/**
 * Base implementation of the SceneTemplate class.
 * @author acoppes
 *
 */
public class SceneTemplateImpl implements SceneTemplate {
	
	Parameters parameters;
	
	public SceneTemplateImpl() {
		parameters = new ParametersWrapper();
	}

	@Override
	public void apply(WorldWrapper scene) {
		
	}

	@Override
	public Parameters getParameters() {
		return parameters;
	}

}
