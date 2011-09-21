package com.gemserk.componentsengine.input;

public interface ButtonsMonitor<K> extends MonitorUpdater {

	void button(K id, ButtonMonitor buttonMonitor);
	
	ButtonMonitor getButton(K id);

}
