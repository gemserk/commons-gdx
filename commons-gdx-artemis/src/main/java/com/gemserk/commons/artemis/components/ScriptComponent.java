package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.artemis.scripts.Script;

public class ScriptComponent extends Component {

	private Script[] scripts;

	public Script[] getScripts() {
		return scripts;
	}

	public ScriptComponent(Script... scripts) {
		if (scripts == null)
			throw new RuntimeException("Cant create a ScriptComponent with null scripts");
		this.scripts = scripts;
	}

}
