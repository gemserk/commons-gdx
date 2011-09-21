package com.gemserk.componentsengine.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CachedMonitorFactory implements MonitorFactory, MonitorUpdater {

	MonitorFactory monitorFactory;

	Map<String, ButtonMonitor> keyboardButtonMonitors = new HashMap<String, ButtonMonitor>();
	
	Map<String, ButtonMonitor> mouseButtonMonitors = new HashMap<String, ButtonMonitor>();

	Map<String, CoordinatesMonitor> coordinateMonitors = new HashMap<String, CoordinatesMonitor>();

	public CachedMonitorFactory(MonitorFactory monitorFactory) {
		this.monitorFactory = monitorFactory;
	}

	@Override
	public ButtonMonitor keyboardButtonMonitor(String button) {
		if (!keyboardButtonMonitors.containsKey(button))
			keyboardButtonMonitors.put(button, monitorFactory.keyboardButtonMonitor(button));
		return keyboardButtonMonitors.get(button);
	}

	@Override
	public ButtonMonitor mouseButtonMonitor(String button) {
		if (!mouseButtonMonitors.containsKey(button))
			mouseButtonMonitors.put(button, monitorFactory.mouseButtonMonitor(button));
		return mouseButtonMonitors.get(button);
	}

	final static String MOUSE_COORDINATES_KEY = "mouseCoordinatesMonitor";

	@Override
	public CoordinatesMonitor mouseCoordinatesMonitor() {
		if (!coordinateMonitors.containsKey(MOUSE_COORDINATES_KEY)) 
			coordinateMonitors.put(MOUSE_COORDINATES_KEY, monitorFactory.mouseCoordinatesMonitor());
		return coordinateMonitors.get(MOUSE_COORDINATES_KEY);
	}

	final static String MOUSE_WHEEL_KEY = "mouseWheelMonitor";
	
	@Override
	public CoordinatesMonitor mouseWheelMonitor() {
		if (!coordinateMonitors.containsKey(MOUSE_WHEEL_KEY)) 
			coordinateMonitors.put(MOUSE_WHEEL_KEY, monitorFactory.mouseWheelMonitor());
		return coordinateMonitors.get(MOUSE_WHEEL_KEY);
	}

	@Override
	public void update() {
		for (Entry<String, ButtonMonitor> entry : keyboardButtonMonitors.entrySet())
			entry.getValue().update();

		for (Entry<String, ButtonMonitor> entry : mouseButtonMonitors.entrySet())
			entry.getValue().update();
		
		for (Entry<String, CoordinatesMonitor> entry : coordinateMonitors.entrySet())
			entry.getValue().update();
	}

}
