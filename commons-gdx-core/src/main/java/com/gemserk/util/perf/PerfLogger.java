package com.gemserk.util.perf;

import com.badlogic.gdx.Gdx;

public class PerfLogger {

	boolean enabled = false;
	FloatSlidingWindowArray deltas;
	IntSlidingWindowArray events;
	int event = -1;
	
	int frames = 1;
	float cummulativeDelta = 1;
	
	public PerfLogger(int windowSize, boolean enabled) {
		deltas = new FloatSlidingWindowArray(windowSize);
		events = new IntSlidingWindowArray(windowSize);
		this.enabled = enabled;
	}
	
	
	public void update(){
		if(enabled){
			float deltaTime = Gdx.graphics.getDeltaTime();
			deltas.add(deltaTime);
			events.add(event);
						
			event = -1;
			
			frames++;
			cummulativeDelta+=deltaTime;			
		}
	}
	
	public void event(int eventId){
		event = eventId;
	}
	
	public void enable(){
		enabled = true;
	}
	
	public void disable(){
		enabled = false;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public FloatSlidingWindowArray getDeltas() {
		return deltas;
	}
	
	public IntSlidingWindowArray getEvents() {
		return events;
	}
	
	public float getAverageDelta() {
		return cummulativeDelta/frames;
	}
	
	public void clear(){
		deltas.clear();
		events.clear();
		event = -1;
	}
}