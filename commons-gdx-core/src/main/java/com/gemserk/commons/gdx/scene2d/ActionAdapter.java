package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ActionAdapter extends Action {

	protected boolean initialized;

	public ActionAdapter() {
		initialized = false;
	}

	@Override
	public void setActor(Actor actor) {
		if (actor == null && this.actor != null) {
			end();
			initialized = false;
		}
		super.setActor(actor);
	}

	@Override
	public boolean act(float delta) {
		if (!initialized) {
			begin();
			initialized = true;
		}
		return update(delta);
	}

	public void begin() {

	}

	public abstract boolean update(float delta);

	public void end() {

	}

}
