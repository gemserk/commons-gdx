package com.gemserk.commons.artemis.components;

import com.artemis.Component;

public class TimerComponent extends Component {

	private float totalTime;
	private float currentTime;

	public float getTotalTime() {
		return totalTime;
	}

	public float getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(float currentTime) {
		this.currentTime = currentTime;
	}

	public boolean isFinished() {
		return currentTime <= 0;
	}

	public void reset() {
		currentTime = totalTime;
	}

	public TimerComponent(float time) {
		this.totalTime = time;
		this.currentTime = time;
	}
}
