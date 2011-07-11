package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.artemis.Script;

public class ScriptComponent extends Component {

	private Script script;

	public Script getScript() {
		return script;
	}

	public void setScript(Script script) {
		this.script = script;
	}

	public ScriptComponent(Script script) {
		this.script = script;
	}

}
