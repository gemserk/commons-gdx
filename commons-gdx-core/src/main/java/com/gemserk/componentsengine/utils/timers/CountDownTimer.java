package com.gemserk.componentsengine.utils.timers;



public class CountDownTimer implements Timer {

	int time;
	int timeLeft;
	boolean fired;

	public CountDownTimer(int time) {
		this(time, false);
	}

	public CountDownTimer(int time, boolean started) {
		this.time = time;
		this.timeLeft = time;
		this.fired = !started;
	}

	public boolean update(int delta) {
		
		if(fired)
			return false;
		
		timeLeft-=delta;
		if(timeLeft<0){
			fired = true;
			return true;
		}
		return false;
	}

	public void reset() {
		timeLeft = time;
		fired = false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(timeLeft);
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}

	@Override
	public boolean isRunning() {
		return !fired;
	}


}
