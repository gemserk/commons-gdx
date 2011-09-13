package com.gemserk.util.concurrent;

import java.util.concurrent.Callable;

public interface FutureHandleCallable<T> extends FutureHandler<T>, Callable<T> {
	
}
