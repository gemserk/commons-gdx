package com.gemserk.commons.artemis.scripts;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

/**
 * Provides an implementation of Script calling a collection of other Scripts.
 * 
 * @author acoppes
 * 
 */
public class ScriptCompositeImpl extends ScriptJavaImpl {

	private Bag<Script> scripts = new Bag<Script>();

	public ScriptCompositeImpl(Script requiredScript, Script... scripts) {
		this.scripts.add(requiredScript);
		if (scripts != null)
			for (int i = 0; i < scripts.length; i++)
				this.scripts.add(scripts[i]);
	}
	
	@Override
	public void enabled(World world, Entity e) {
		for (int i = 0; i < scripts.size(); i++)
			scripts.get(i).enabled(world, e);
	}

	@Override
	public void update(World world, Entity e) {
		for (int i = 0; i < scripts.size(); i++)
			scripts.get(i).update(world, e);
	}
	
	@Override
	public void disabled(World world, Entity e) {
		for (int i = 0; i < scripts.size(); i++)
			scripts.get(i).disabled(world, e);
		
	}

}
