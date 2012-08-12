package com.gemserk.commons.gdx.scene2d.actions;

import com.gemserk.commons.gdx.scene2d.ActionAdapter;
import com.gemserk.commons.gdx.scene2d.ActorFocusListener;

@Deprecated
public class MonitorTouchFocusAction extends ActionAdapter {

	boolean hasFocus = false;
	ActorFocusListener focusListener;

	public MonitorTouchFocusAction(ActorFocusListener focusListener) {
		this.focusListener = focusListener;
	}

	@Override
	public boolean act(float delta) {
		return true;
		// Actor focusedActor = stage.getTouchFocus(0);
		//
		// if (hasFocus) {
		// if (focusedActor != actor) {
		// focusListener.focusLost(actor);
		// hasFocus = false;
		// }
		// return super.act(delta);
		// }
		//
		// if (!hasFocus) {
		// if (focusedActor == actor) {
		// focusListener.focusGained(actor);
		// hasFocus = true;
		// }
		// return super.act(delta);
		// }

	}
}