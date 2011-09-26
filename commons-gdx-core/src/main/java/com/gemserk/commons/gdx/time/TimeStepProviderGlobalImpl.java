package com.gemserk.commons.gdx.time;

import com.gemserk.commons.gdx.GlobalTime;

public class TimeStepProviderGlobalImpl implements TimeStepProvider {

	@Override
	public float getDelta() {
		return GlobalTime.getDelta();
	}

	@Override
	public float getAlpha() {
		return GlobalTime.getAlpha();
	}
	
}
