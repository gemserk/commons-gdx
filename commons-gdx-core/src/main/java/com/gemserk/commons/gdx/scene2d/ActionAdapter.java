package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActionAdapter extends Action {
	
	protected Actor target;
	protected boolean done;
	
	public ActionAdapter() {
		done = false;
	}

	@Override
	public void setTarget(Actor target) {
		this.target = target;
	}

	@Override
	public Actor getTarget() {
		return target;
	}

	@Override
	public void act(float delta) {
		
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public Action copy() {
		throw new UnsupportedOperationException("not implemented");
	}

}
