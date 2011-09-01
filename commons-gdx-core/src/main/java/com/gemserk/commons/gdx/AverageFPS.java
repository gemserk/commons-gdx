package com.gemserk.commons.gdx;

import com.badlogic.gdx.Gdx;

public class AverageFPS {
	
	long accumulatedFPS = 0;
	long quantity = 0;
	
	public void start(){
		accumulatedFPS = 0;
		quantity = 0;
	}

	public void update(){
		accumulatedFPS += Gdx.graphics.getFramesPerSecond();
		quantity++;
	}
	
	public int getFPS(){
		return (int) (accumulatedFPS/quantity);
	}
}
