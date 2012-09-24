package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class BackKeyListener extends InputAdapter {

	private int[] keyCodes;
	private final StageKeyUpListener listener;

	public BackKeyListener(StageKeyUpListener listener) {
		this.listener = listener;
		keyCodes = new int[] {Keys.BACK, Keys.ESCAPE};
	}
	
	@Override
	public boolean keyUp(int keycode) {
		boolean matches = false;
		for (int i = 0; i < keyCodes.length; i++) {
			if(keyCodes[i]==keycode){
				matches=true;
				break;
			}
		}
		if(matches)
			return listener.handleKeyUp(keycode);
		else 
			return false;
	}
	
	
}
