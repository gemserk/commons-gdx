package com.gemserk.componentsengine.input;

import com.badlogic.gdx.Input;
import com.gemserk.componentsengine.input.AnalogInputMonitor;
import com.gemserk.componentsengine.input.ButtonMonitor;
import com.gemserk.componentsengine.input.InputDevicesMonitorImpl;

public class LibgdxInputMappingBuilder<K> {

	private final InputDevicesMonitorImpl<K> inputDevicesMonitor;

	private final Input input;

	public LibgdxInputMappingBuilder(InputDevicesMonitorImpl<K> inputDevicesMonitor, Input input) {
		this.inputDevicesMonitor = inputDevicesMonitor;
		this.input = input;
	}

	public void monitorKey(K id, final int keyCode) {
		inputDevicesMonitor.button(id, new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				return input.isKeyPressed(keyCode);
			}
		});
	}

	public void monitorPointerDown(K id, final int pointer) {
		inputDevicesMonitor.button(id, new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				return input.isTouched(pointer);
			}
		});
	}

	public void monitorPointerX(K id, final int pointer) {
		inputDevicesMonitor.analog(id, new AnalogInputMonitor() {
			@Override
			protected float newValue() {
				// if (!input.isTouched(pointer))
				// return getValue();
				return input.getX(pointer);
			}
		});
	}

	public void monitorPointerY(K id, final int pointer) {
		inputDevicesMonitor.analog(id, new AnalogInputMonitor() {
			@Override
			protected float newValue() {
				// if (!input.isTouched(pointer))
				// return getValue();
				return input.getY(pointer);
			}
		});
	}

	public void monitorMouseLeftButton(K id) {
		inputDevicesMonitor.button(id, new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				return input.isButtonPressed(Input.Buttons.LEFT);
			}
		});
	}

	public void monitorMouseRightButton(K id) {
		inputDevicesMonitor.button(id, new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				return input.isButtonPressed(Input.Buttons.RIGHT);
			}
		});
	}

	public void monitorMouseMiddleButton(K id) {
		inputDevicesMonitor.button(id, new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				return input.isButtonPressed(Input.Buttons.MIDDLE);
			}
		});
	}

}