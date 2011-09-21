package com.gemserk.commons.gdx.gamestates;

import com.gemserk.commons.gdx.GameStateImpl;
import com.gemserk.resources.progress.TaskQueue;

public class LoadingGameState extends GameStateImpl {

	private TaskQueue taskQueue;
	
	public TaskQueue getTaskQueue() {
		return taskQueue;
	}

	@Override
	public void init() {
		super.init();
		taskQueue = new TaskQueue();
	}

	@Override
	public void render() {
		super.render();
		// should be on the update if the update was processed once 
		if (!taskQueue.isDone()) 
			taskQueue.processNext();
	}
	
}