package com.gemserk.commons.gdx.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.gemserk.commons.gdx.Game;
import com.gemserk.commons.gdx.GameTransitions.ScreenTransition;
import com.gemserk.commons.gdx.GameTransitions.TransitionHandler;
import com.gemserk.commons.gdx.GameTransitions.TransitionScreen;
import com.gemserk.commons.gdx.Screen;

public class TransitionBuilder {

	private final Screen screen;
	private final Game game;

	float leaveTime;
	float enterTime;

	boolean shouldDisposeCurrentScreen;
	boolean shouldRestartNextScreen;

	TransitionHandler leaveTransitionHandler = new TransitionHandler();

	private boolean transitioning = false;

	public TransitionBuilder leaveTime(float leaveTime) {
		this.leaveTime = leaveTime;
		return this;
	}

	public TransitionBuilder enterTime(float enterTime) {
		this.enterTime = enterTime;
		return this;
	}

	public TransitionBuilder leaveTime(int leaveTime) {
		return leaveTime((float) leaveTime * 0.001f);
	}

	public TransitionBuilder enterTime(int enterTime) {
		return enterTime((float) enterTime * 0.001f);
	}

	public TransitionBuilder disposeCurrent() {
		this.shouldDisposeCurrentScreen = true;
		return this;
	}

	public TransitionBuilder disposeCurrent(boolean disposeCurrent) {
		this.shouldDisposeCurrentScreen = disposeCurrent;
		return this;
	}

	public TransitionBuilder restartScreen() {
		this.shouldRestartNextScreen = true;
		return this;
	}

	public TransitionBuilder leaveTransitionHandler(TransitionHandler transitionHandler) {
		this.leaveTransitionHandler = transitionHandler;
		return this;
	}

	public TransitionBuilder parameter(String key, Object value) {
		screen.getParameters().put(key, value);
		return this;
	}

	public TransitionBuilder(final Game game, final Screen screen) {
		this.game = game;
		this.screen = screen;
		this.leaveTransitionHandler = new TransitionHandler();
		this.leaveTime = 0.25f;
		this.enterTime = 0.25f;
	}

	public void start() {
		if (transitioning) {
			Gdx.app.log("Commons-gdx", "Can't start a new transition if already in a transition");
			return;
		}

		transitioning = true;

		if (shouldRestartNextScreen)
			screen.dispose();

		final Screen currentScreen = game.getScreen();
		game.setScreen(new TransitionScreen(new ScreenTransition( //
				new FadeOutTransition(currentScreen, leaveTime, leaveTransitionHandler), //
				new FadeInTransition(screen, enterTime, new TransitionHandler() {
					public void onEnd() {
						game.setScreen(screen, true);
						// disposes current transition screen, not previous screen.
						if (shouldDisposeCurrentScreen)
							currentScreen.dispose();
						transitioning = false;
					};
				}))) {
			@Override
			public void resume() {
				super.resume();
				Gdx.input.setCatchBackKey(true);
			}
		});
	}

}