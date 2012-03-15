package com.gemserk.commons.gdx.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gemserk.commons.gdx.scene2d.ActionAdapter;
import com.gemserk.commons.gdx.scene2d.ActorFocusListener;

public class MonitorTouchFocusAction extends ActionAdapter {

	boolean hasFocus = false;
	ActorFocusListener focusListener;

	public MonitorTouchFocusAction(ActorFocusListener focusListener) {
		this.focusListener = focusListener;
	}

	@Override
	public void setTarget(Actor target) {
		super.setTarget(target);
	}

	@Override
	public void act(float delta) {
		Actor actor = getTarget();
		Stage stage = actor.getStage();

		Actor focusedActor = stage.getTouchFocus(0);

		if (hasFocus) {
			if (focusedActor != actor) {
				focusListener.focusLost(actor);
				hasFocus = false;
			}
			return;
		}

		if (!hasFocus) {
			if (focusedActor == actor) {
				focusListener.focusGained(actor);
				hasFocus = true;
			}
			return;
		}

	}
}