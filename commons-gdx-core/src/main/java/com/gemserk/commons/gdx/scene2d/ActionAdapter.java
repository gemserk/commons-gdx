package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Action;

public class ActionAdapter extends Action {

	protected boolean done;

	public void setDone(boolean done) {
		this.done = done;
	}

	public ActionAdapter() {
		done = false;
	}

	@Override
	public boolean act(float delta) {
		return done;
	}

	// @Override
	// public Action copy() {
	// ActionAdapter actionAdapter = new ActionAdapter();
	// actionAdapter.target = this.target;
	// actionAdapter.done = this.done;
	// return actionAdapter;
	// }

}
