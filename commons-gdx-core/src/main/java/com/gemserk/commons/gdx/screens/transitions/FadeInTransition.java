package com.gemserk.commons.gdx.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.commons.gdx.GameTransitions;
import com.gemserk.commons.gdx.GameTransitions.TransitionHandler;
import com.gemserk.commons.gdx.Screen;
import com.gemserk.commons.gdx.graphics.ImmediateModeRendererUtils;
import com.gemserk.commons.gdx.graphics.Mesh2d;
import com.gemserk.commons.gdx.graphics.Mesh2dUtils;

public class FadeInTransition extends GameTransitions.EnterTransition {

	private final float time;
	private Mesh2d fadeRectangle;

	private Transition<Color> colorTransition;

	private final Color startColor = new Color(0f, 0f, 0f, 1f);
	private final Color endColor = new Color(0f, 0f, 0f, 0f);

	public FadeInTransition(Screen screen, float time) {
		super(screen, time);
		this.time = time;
		fadeRectangle = Mesh2dUtils.quad(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public FadeInTransition(Screen screen, float time, TransitionHandler transitionHandler) {
		super(screen, time, transitionHandler);
		this.time = time;
		fadeRectangle = Mesh2dUtils.quad(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void init() {
		super.init();
		// colorTransition = Transitions.transitionBuilder(startColor).end(endColor).time(time).build();
		colorTransition = Transitions.transition(startColor).endObject(time, endColor).build();
		Color color = colorTransition.get();
		Mesh2dUtils.setColor(fadeRectangle, color.r, color.g, color.b, color.a);
	}

	@Override
	public void postRender(float delta) {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ImmediateModeRendererUtils.draw(GL10.GL_TRIANGLES, fadeRectangle);
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void internalUpdate(float delta) {
		super.internalUpdate(delta);
		colorTransition.update(delta);
		Color color = colorTransition.get();
		Mesh2dUtils.setColor(fadeRectangle, color.r, color.g, color.b, color.a);
	}

}