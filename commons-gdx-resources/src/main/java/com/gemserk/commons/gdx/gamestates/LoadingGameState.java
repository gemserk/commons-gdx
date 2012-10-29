package com.gemserk.commons.gdx.gamestates;

import com.gemserk.commons.gdx.GameStateImpl;
import com.gemserk.resources.progress.TaskQueue;

public class LoadingGameState extends GameStateImpl {

	private TaskQueue taskQueue;

	// 30ms in ns...
	private final long loadTimePerFrame = 30L * 1000000L;

	private long loadTime;

	public TaskQueue getTaskQueue() {
		return taskQueue;
	}

	public void setTaskQueue(TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
	}

	public LoadingGameState() {
		taskQueue = new TaskQueue();
	}

	@Override
	public void render() {
		super.render();
		// should be on the update if the update was processed once
		long lastDelta = System.nanoTime();
		long currentDelta = lastDelta;

		loadTime = 0;

		// this allows to perform more than one task in the same render call
		while (!taskQueue.isDone() && loadTime <= loadTimePerFrame) {
			taskQueue.processNext();
			currentDelta = System.nanoTime();
			loadTime += currentDelta - lastDelta;
		}

	}

}