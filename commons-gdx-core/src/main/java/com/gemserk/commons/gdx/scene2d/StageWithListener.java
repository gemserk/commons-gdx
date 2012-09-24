package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StageWithListener extends Stage {

	private final InputProcessor inputProcessor;

	public StageWithListener(InputProcessor inputProcessor) {
		super();
		this.inputProcessor = inputProcessor;
	}

	public StageWithListener(float width, float height, boolean stretch, SpriteBatch batch, InputProcessor inputProcessor) {
		super(width, height, stretch, batch);
		this.inputProcessor = inputProcessor;
	}

	public StageWithListener(float width, float height, boolean stretch, InputProcessor inputProcessor) {
		super(width, height, stretch);
		this.inputProcessor = inputProcessor;
	}

	public boolean keyDown (int keycode) {
		if(super.keyDown(keycode))
			return true;
		
		return inputProcessor.keyDown(keycode);
	}

	public boolean keyUp (int keycode) {
		if(super.keyUp(keycode))
			return true;
		
		return inputProcessor.keyUp(keycode);
	}

	public boolean keyTyped (char character) {
		if(super.keyTyped(character))
			return true;
		
		return inputProcessor.keyTyped(character);
	}

	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		if(super.touchDown(screenX, screenY, pointer, button))
			return true;
		
		return inputProcessor.touchDown(screenX, screenY, pointer, button);
	}

	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if(super.touchUp(screenX, screenY, pointer, button))
			return true;
		
		return inputProcessor.touchUp(screenX, screenY, pointer, button);
	}

	public boolean touchDragged (int screenX, int screenY, int pointer) {
		if(super.touchDragged(screenX, screenY, pointer))
			return true;
		
		return inputProcessor.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		if(super.mouseMoved(screenX, screenY))
			return true;
		
		return inputProcessor.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled (int amount) {
		if(super.scrolled(amount))
			return true;
		
		return inputProcessor.scrolled(amount);
	}

}
