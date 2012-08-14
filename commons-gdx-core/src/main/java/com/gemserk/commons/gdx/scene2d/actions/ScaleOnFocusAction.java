package com.gemserk.commons.gdx.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gemserk.animation4j.gdx.scenes.scene2d.Scene2dConverters;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.commons.gdx.scene2d.ActionAdapter;
import com.gemserk.commons.gdx.scene2d.ActorDecorator;
import com.gemserk.commons.gdx.scene2d.ActorFocusListener;

public class ScaleOnFocusAction extends ActionAdapter {

	float width, height;
	Transition<?> focusTransition;
	ActorDecorator actorDecorator;

	MonitorTouchFocusAction monitorTouchFocusAction;

	public ScaleOnFocusAction() {
		this(new ActorDecorator());
	}

	public ScaleOnFocusAction(final ActorDecorator actorDecorator) {
		this.actorDecorator = actorDecorator;

		monitorTouchFocusAction = new MonitorTouchFocusAction(new ActorFocusListener() {

			@Override
			public void focusLost(Actor actor) {
				focusTransition = Transitions.transition(actorDecorator, Scene2dConverters.actorDecoratorSizeTypeConverter) //
						.start(actor.getWidth(), actor.getHeight()) //
						.end(0.1f, width * 1f, height * 1f) //
						.build();
			}

			@Override
			public void focusGained(Actor actor) {
				focusTransition = Transitions.transition(actorDecorator, Scene2dConverters.actorDecoratorSizeTypeConverter) //
						.start(actor.getWidth(), actor.getHeight()) //
						.end(0.1f, width * 1.1f, height * 1.1f) //
						.build();
			}
		});

	}
	
	@Override
	public void setActor(Actor actor) {
		super.setActor(actor);
		width = actor.getWidth();
		height = actor.getHeight();
		actorDecorator.setActor(actor);
		monitorTouchFocusAction.setActor(actor);
	}

	@Override
	public boolean update(float delta) {
		monitorTouchFocusAction.act(delta);

		if (focusTransition != null)
			focusTransition.update(delta);
		
		return false;
	}
}