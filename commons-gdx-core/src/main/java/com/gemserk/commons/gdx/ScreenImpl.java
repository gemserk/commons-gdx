package com.gemserk.commons.gdx;

import com.gemserk.componentsengine.utils.Parameters;

/**
 * Screen implementation with internal state to call the GameState methods in the correct way and only once when required.
 * 
 * @author acoppes
 * 
 */
public class ScreenImpl implements Screen {

	private final GameState gameState;

	private boolean paused = true;
	private boolean visible = false;
	private boolean inited = false;

	private float delta;

	/**
	 * used to fix the time step based on <a href="http://gafferongames.com/game-physics/fix-your-timestep/">fix your timestep</a>
	 * */
	private float dt = 0.01f;
	private float maxFrameTime = 0.25f;
	
	private float accumulator;

	protected float getDelta() {
		return delta;
	}

	public ScreenImpl(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void init() {
		if (inited)
			return;
		inited = true;
		gameState.init();
	}

	@Override
	public void dispose() {
		if (!inited)
			return;
		inited = false;
		gameState.dispose();
	}

	@Override
	public void restart() {
		dispose();
		init();
	}

	@Override
	public void render() {
		if (!visible)
			return;
		gameState.setDelta(this.delta);
		gameState.render();
	}

	@Override
	public void update() {
		if (paused)
			return;

		// float t = 0f;
		float frameTime = getDelta();

		// note: max frame time to avoid spiral of death
		if (frameTime > maxFrameTime)
			frameTime = maxFrameTime;

		accumulator += frameTime;

		while (accumulator >= dt) {
			GlobalTime.setDelta(dt);

			gameState.setDelta(dt);
			gameState.update();

			// previousState = currentState;
			// integrate( currentState, t, dt );

			// t += dt;
			accumulator -= dt;
		}

		float alpha = accumulator / dt;
		GlobalTime.setAlpha(alpha);
		
		gameState.setAlpha(alpha);

		// const double alpha = accumulator / dt;
		//
		// State state = currentState*alpha + previousState * ( 1.0 - alpha );
		//
		// render( state );
	}

	@Override
	public void resize(int width, int height) {
		// call gamestate resize?
	}

	@Override
	public void show() {
		if (visible)
			return;
		visible = true;
		gameState.show();
	}

	@Override
	public void hide() {
		if (!visible)
			return;
		visible = false;
		gameState.hide();
	}

	@Override
	public void pause() {
		if (paused)
			return;
		paused = true;
		gameState.pause();
	}

	@Override
	public void resume() {
		if (!paused)
			return;
		paused = false;
		gameState.resume();
	}

	@Override
	public void setDelta(float delta) {
		this.delta = delta;
	}

	@Override
	public Parameters getParameters() {
		return gameState.getParameters();
	}

}