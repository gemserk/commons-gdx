package com.gemserk.commons.artemis.components;

import java.util.ArrayList;
import java.util.Collections;

import com.artemis.Component;
import com.gemserk.commons.artemis.scripts.Script;

public class ScriptComponent extends Component {

	private ArrayList<Script> scripts;

	public ArrayList<Script> getScripts() {
		return scripts;
	}
	
	public void setScripts(ArrayList<Script> scripts) {
		this.scripts = scripts;
	}

	public ScriptComponent(Script... scripts) {
		if (scripts == null)
			throw new RuntimeException("Cant create a ScriptComponent with null scripts");
		this.scripts = new ArrayList<Script>(scripts.length);
		Collections.addAll(this.scripts, scripts);
	}

}
