package com.gemserk.commons.gdx.time;

import com.gemserk.commons.gdx.GameStateImpl;

public class TimeStepProviderGameStateImpl implements TimeStepProvider  {
	
	private final GameStateImpl gameStateImpl;

	public TimeStepProviderGameStateImpl(GameStateImpl gameStateImpl) {
		this.gameStateImpl = gameStateImpl;
	}

	@Override
	public float getDelta() {
		return gameStateImpl.getDelta();
	}

	@Override
	public float getAlpha() {
		return gameStateImpl.getAlpha();		
	}

}
