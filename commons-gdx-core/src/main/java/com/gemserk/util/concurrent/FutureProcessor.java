package com.gemserk.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureProcessor<T> {

	private FutureHandler<T> futureHandler;

	private Future<T> future;

	public void setFuture(Future<T> future) {
		this.future = future;
	}

	public FutureProcessor(FutureHandler<T> futureHandler) {
		this.futureHandler = futureHandler;
	}

	public FutureProcessor(FutureHandler<T> futureHandler, Future<T> future) {
		this.futureHandler = futureHandler;
		this.future = future;
	}

	public void update() {
		if (future == null)
			return;

		if (!future.isDone())
			return;

		try {

			if (future.isCancelled()) {
				futureHandler.failed(null);
			} else {
				futureHandler.done(future.get());
			}

		} catch (InterruptedException e) {
			futureHandler.failed(e);
		} catch (ExecutionException e) {
			futureHandler.failed(e);
		}

		future = null;
	}
	
	public void setFutureHandler(FutureHandler<T> futureHandler) {
		this.futureHandler = futureHandler;
	}
	
	public boolean isWorking(){
		return future!=null;
	}

}