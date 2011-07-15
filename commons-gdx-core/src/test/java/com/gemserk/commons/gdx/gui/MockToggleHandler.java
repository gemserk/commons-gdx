package com.gemserk.commons.gdx.gui;

import com.gemserk.commons.gdx.gui.ToggleableImageButton.ToggleHandler;

public class MockToggleHandler extends ToggleHandler {
	
	boolean toggleCalled;
	boolean value;
	
	@Override
	public void onToggle(boolean value) {
		this.toggleCalled = true;
		this.value = value;
	}
	
}
