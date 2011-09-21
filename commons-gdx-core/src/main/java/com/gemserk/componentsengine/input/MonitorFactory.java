package com.gemserk.componentsengine.input;

public interface MonitorFactory {

	ButtonMonitor keyboardButtonMonitor(String button);

	ButtonMonitor mouseButtonMonitor(String button);

	CoordinatesMonitor mouseCoordinatesMonitor();

	CoordinatesMonitor mouseWheelMonitor();
	
}