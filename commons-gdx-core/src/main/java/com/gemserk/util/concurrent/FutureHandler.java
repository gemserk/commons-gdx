package com.gemserk.util.concurrent;

public interface FutureHandler<T> {

	void done(T t);

	void failed(Exception e);

}