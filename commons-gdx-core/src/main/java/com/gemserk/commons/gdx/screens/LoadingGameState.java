package com.gemserk.commons.gdx.screens;

import com.gemserk.commons.gdx.GameStateImpl;
import com.gemserk.resources.progress.TaskQueue;

public class LoadingGameState extends GameStateImpl {

	private boolean loading;
	private TaskQueue taskQueue;
	
	public TaskQueue getTaskQueue() {
		return taskQueue;
	}

	@Override
	public void init() {
		super.init();
		taskQueue = getParameters().get("taskQueue");
		loading = !taskQueue.isDone();
	}

	@Override
	public void render() {
		super.render();
		if (loading) {
			taskQueue.processNext();
			loading = !taskQueue.isDone();
		} 
	}

}