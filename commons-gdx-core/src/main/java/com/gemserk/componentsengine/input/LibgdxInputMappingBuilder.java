package com.gemserk.componentsengine.input;

import com.badlogic.gdx.Input;

public class LibgdxInputMappingBuilder<K> {

	private final InputDevicesMonitorImpl<K> inputDevicesMonitor;

	private final Input input;

	public LibgdxInputMappingBuilder(InputDevicesMonitorImpl<K> inputDevicesMonitor, Input input) {
		this.inputDevicesMonitor = inputDevicesMonitor;
		this.input = input;
	}

	public void monitorKey(K id, final int keyCode) {
		inputDevicesMonitor.button(id, keyButtonMonitor(input, keyCode));
	}
	
	public void monitorKeys(K id, final int... keyCodes) {
		inputDevicesMonitor.button(id, keyButtonMonitor(input, keyCodes));
	}

	public void monitorPointerDown(K id, final int pointer) {
		inputDevicesMonitor.button(id, pointerDownButtonMonitor(input, pointer));
	}

	public void monitorPointerX(K id, final int pointer) {
		inputDevicesMonitor.analog(id, pointerXCoordinateMonitor(input, pointer));
	}

	public void monitorPointerY(K id, final int pointer) {
		inputDevicesMonitor.analog(id, pointerYCoordinateMonitor(input, pointer));
	}

	public void monitorMouseLeftButton(K id) {
		inputDevicesMonitor.button(id, leftMouseButtonMonitor(input));
	}

	public void monitorMouseRightButton(K id) {
		inputDevicesMonitor.button(id, rightMouseButtonMonitor(input));
	}

	public void monitorMouseMiddleButton(K id) {
		inputDevicesMonitor.button(id, middleMouseButtonMonitor(input));
	}

	// TODO: move them to a factory class

	public static ButtonMonitor leftMouseButtonMonitor(final Input input) {
		return mouseButtonMonitor(input, Input.Buttons.LEFT);
	}

	public static ButtonMonitor middleMouseButtonMonitor(final Input input) {
		return mouseButtonMonitor(input, Input.Buttons.MIDDLE);
	}

	public static ButtonMonitor rightMouseButtonMonitor(final Input input) {
		return mouseButtonMonitor(input, Input.Buttons.RIGHT);
	}
	
	public static ButtonMonitor keyButtonMonitor(final Input input, final int keyCode) {
		return new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				return input.isKeyPressed(keyCode);
			}
		};
	}
	
	public static ButtonMonitor keyButtonMonitor(final Input input, final int...keyCodes) {
		return new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				for (int i = 0; i < keyCodes.length; i++) {
					if (input.isKeyPressed(keyCodes[i]))
						return true;
				}
				return false;
			}
		};
	}

	public static ButtonMonitor mouseButtonMonitor(final Input input, final int button) {
		return new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				return input.isButtonPressed(button);
			}
		};
	}

	public static AnalogInputMonitor pointerXCoordinateMonitor(final Input input, final int pointer) {
		return new AnalogInputMonitor() {
			@Override
			protected float newValue() {
				return input.getX(pointer);
			}
		};
	}

	public static AnalogInputMonitor pointerYCoordinateMonitor(final Input input, final int pointer) {
		return new AnalogInputMonitor() {
			@Override
			protected float newValue() {
				return input.getY(pointer);
			}
		};
	}
	
	public static ButtonMonitor pointerDownButtonMonitor(final Input input, final int pointer) {
		return new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				return input.isTouched(pointer);
			}
		};
	}

	public static ButtonMonitor anyPointerButtonMonitor(final Input input) {
		final int maxPointers = 5;
		return new ButtonMonitor() {
			@Override
			protected boolean isDown() {
				for (int i = 0; i < maxPointers; i++) {
					if (input.isTouched(i))
						return true;
				}
				return false;
			}
		};
	}
	
	public static AnalogInputMonitor anyPointerXCoordinateMonitor(final Input input) {
		return new AnalogInputMonitor() {
			final int maxPointers = 5;
			@Override
			protected float newValue() {
				for (int i = 0; i < maxPointers; i++) {
					if (input.isTouched(i))
						return input.getX(i);
				}
				return input.getX(0);
			}
		};
	}

	public static AnalogInputMonitor anyPointerYCoordinateMonitor(final Input input) {
		return new AnalogInputMonitor() {
			final int maxPointers = 5;
			@Override
			protected float newValue() {
				for (int i = 0; i < maxPointers; i++) {
					if (input.isTouched(i))
						return input.getY(i);
				}
				return input.getY(0);
			}
		};
	}
	
}