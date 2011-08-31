package com.gemserk.commons.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Game implements ApplicationListener {

	protected Screen screen;

	public Screen getScreen() {
		return screen;
	}

	@Override
	public void dispose() {
		if (screen == null)
			return;
		screen.hide();
		screen.pause();
		screen.dispose();
	}

	@Override
	public void pause() {
		if (screen != null)
			screen.pause();
	}

	@Override
	public void resume() {
		if (screen != null)
			screen.resume();
	}

	@Override
	public void render() {
		if (screen == null)
			return;

		// // float t = 0f;
		// float frameTime = Gdx.graphics.getDeltaTime();
		//
		// // note: max frame time to avoid spiral of death
		// if (frameTime > 0.25f)
		// frameTime = 0.25f;
		//
		// accumulator += frameTime;
		//
		// while (accumulator >= dt) {
		// GlobalTime.setDelta(dt);
		//
		// screen.setDelta(dt);
		// screen.update();
		//
		// // previousState = currentState;
		// // integrate( currentState, t, dt );
		//
		// // t += dt;
		// accumulator -= dt;
		// }
		//
		// // const double alpha = accumulator / dt;
		// //
		// // State state = currentState*alpha + previousState * ( 1.0 - alpha );
		// //
		// // render( state );

		screen.setDelta(Gdx.graphics.getDeltaTime());
		screen.update();
		screen.render();
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null)
			screen.resize(width, height);
	}

	public void setScreen(Screen screen) {
		setScreen(screen, false);
	}

	public void setScreen(Screen screen, boolean shouldDispose) {
		if (screen == null)
			return;

		Screen currentScreen = getScreen();

		if (currentScreen != null) {
			currentScreen.pause();
			currentScreen.hide();
			if (shouldDispose)
				currentScreen.dispose();
		}

		this.screen = screen;
		screen.init();
		screen.resume();
		screen.show();
	}

	@Override
	public void create() {

	}

}