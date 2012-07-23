package com.gemserk.commons.utils.debug;

import android.os.Debug;

public class AndroidProfilerDumper extends ProfilerDumper{

	@Override
	public void startMethodTracing(String name) {
		Debug.startMethodTracing(name);
	}

	@Override
	public void startMethodTracing(String name, int bufferSize) {
		Debug.startMethodTracing(name, bufferSize);
	}

	@Override
	public void stopMethodTracing() {
		Debug.stopMethodTracing();
	}
	
	
}
