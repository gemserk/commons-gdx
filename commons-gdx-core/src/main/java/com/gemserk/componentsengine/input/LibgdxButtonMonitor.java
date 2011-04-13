package com.gemserk.componentsengine.input;

import com.badlogic.gdx.Gdx;

public class LibgdxButtonMonitor extends ButtonMonitor {
	
	private final int key;

	public LibgdxButtonMonitor(int key) {
		this.key = key;
	}
	
	@Override
	protected boolean isDown() {
		return Gdx.input.isKeyPressed(key);
	}
	
}