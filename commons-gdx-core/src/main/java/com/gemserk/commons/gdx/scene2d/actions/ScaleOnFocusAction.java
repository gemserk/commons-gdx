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
						.start(actor.width, actor.height) //
						.end(0.1f, width * 1f, height * 1f) //
						.build();
			}

			@Override
			public void focusGained(Actor actor) {
				focusTransition = Transitions.transition(actorDecorator, Scene2dConverters.actorDecoratorSizeTypeConverter) //
						.start(actor.width, actor.height) //
						.end(0.1f, width * 1.1f, height * 1.1f) //
						.build();
			}
		});

	}

	@Override
	public void setTarget(Actor target) {
		super.setTarget(target);
		width = target.width;
		height = target.height;
		actorDecorator.setActor(target);
		monitorTouchFocusAction.setTarget(target);
	}

	@Override
	public void act(float delta) {
		monitorTouchFocusAction.act(delta);

		if (focusTransition != null)
			focusTransition.update(delta);
	}
}