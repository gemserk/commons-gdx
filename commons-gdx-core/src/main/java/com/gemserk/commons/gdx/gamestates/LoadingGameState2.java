package com.gemserk.commons.gdx.gamestates;

import com.gemserk.commons.gdx.GameState;
import com.gemserk.commons.gdx.GameStateImpl;
import com.gemserk.componentsengine.utils.Parameters;
import com.gemserk.resources.progress.TaskQueue;

public abstract class LoadingGameState2 extends GameStateImpl {

	private boolean loading;
	private boolean loaded;

	private TaskQueue taskQueue;

	private final GameState gameState;

	public TaskQueue getTaskQueue() {
		return taskQueue;
	}

	public LoadingGameState2(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void setDelta(float delta) {
		gameState.setDelta(delta);
	}

	@Override
	public Parameters getParameters() {
		return gameState.getParameters();
	}

	@Override
	public void init() {
		loaded = false;
		taskQueue = new TaskQueue();
		internalInit();
		gameState.getParameters().put("taskQueue", taskQueue);
		gameState.init();
		loading = true;
	}

	protected abstract void internalInit();

	protected abstract void internalRender();

	protected abstract void internalDispose();

	@Override
	public void render() {

		if (taskQueue.isDone() && !loaded) {
			gameState.show();
			gameState.resume();
			loaded = true;
			loading = false;
		}

		if (loading) {
			taskQueue.processNext();
			internalRender();
		} else {
			gameState.render();
		}
	}

	@Override
	public void update() {
		if (!loaded)
			return;
		gameState.update();
	}

	@Override
	public void pause() {
		if (!loaded)
			return;
		gameState.pause();
	}

	@Override
	public void resume() {
		if (!loaded)
			return;
		gameState.resume();
	}

	@Override
	public void hide() {
		if (!loaded)
			return;
		gameState.hide();
	}

	@Override
	public void show() {
		if (!loaded)
			return;
		gameState.show();
	}

	@Override
	public void dispose() {
		// Should not be able to dispose if started loading phase...
		internalDispose();
		if (loading)
			throw new RuntimeException("Don't know how to handle this case yet");
		// I believe I should finish loading all stuff and then disposing... 
		if (!loaded)
			return;
		gameState.dispose();
	}

}