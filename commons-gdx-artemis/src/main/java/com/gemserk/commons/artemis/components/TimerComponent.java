package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.artemis.triggers.Trigger;

public class TimerComponent extends Component {
	
	private final int time;
	
	private int currentTime;
	
	private Trigger trigger;
	
	public int getTime() {
		return time;
	}
	
	public int getCurrentTime() {
		return currentTime;
	}
	
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	
	public boolean isFinished() {
		return currentTime < 0;
	}
	
	public void reset() {
		currentTime = time;
	}
	
	public Trigger getTrigger() {
		return trigger;
	}
	
	public TimerComponent(int time, Trigger trigger) {
		this.time = time;
		this.currentTime = time;
		this.trigger = trigger;
	}

}
