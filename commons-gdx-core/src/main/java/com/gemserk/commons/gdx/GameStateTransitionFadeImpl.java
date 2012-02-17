package com.gemserk.commons.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.commons.gdx.graphics.ImmediateModeRendererUtils;
import com.gemserk.commons.gdx.graphics.Mesh2d;
import com.gemserk.commons.gdx.graphics.Mesh2dUtils;

public class GameStateTransitionFadeImpl extends GameStateImpl {

	GameState currentGameState;

	private Mesh2d fadeRectangle;
	private Transition<Color> colorTransition;

	private Color color = new Color();

	public GameStateTransitionFadeImpl(GameState currentGameState, float time, Color fromColor, Color toColor) {
		this.currentGameState = currentGameState;
		colorTransition = Transitions.transition(color).startObject(fromColor).endObject(time, toColor).build();
		fadeRectangle = Mesh2dUtils.quad(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void init() {
		Mesh2dUtils.setColor(fadeRectangle, color.r, color.g, color.b, color.a);
		currentGameState.show();
	}

	@Override
	public void update() {
		if (colorTransition.isFinished())
			return;
		
		colorTransition.update(getDelta());
		
		Mesh2dUtils.setColor(fadeRectangle, color.r, color.g, color.b, color.a);

		if (colorTransition.isFinished()) 
			onTransitionFinished();
	}

	protected void onTransitionFinished() {

	}

	@Override
	public void render() {
		currentGameState.render();
		
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ImmediateModeRendererUtils.draw(GL10.GL_TRIANGLES, fadeRectangle);
		Gdx.gl10.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void dispose() {

	}

}
