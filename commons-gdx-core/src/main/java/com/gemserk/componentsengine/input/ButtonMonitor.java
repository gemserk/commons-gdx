package com.gemserk.componentsengine.input;

public abstract class ButtonMonitor {
	
	public enum Status {PRESSED, RELEASED, HOLDED}
		
	boolean pressed;
	boolean released;	
	boolean holded;
	
	protected abstract boolean isDown();
	
	public void update(){
		boolean down = isDown();
		if(down){
			if(!pressed && !holded){
				pressed = true;
			}
			else{
				pressed = false;
				holded = true;
			}
		}else{
			if(holded && !released){
				released = true;
				holded = false;
			}else if(pressed){
				released = true;
				pressed = false;
			}else{
				released = false;
			}
			
		}
		
	}
	
	public boolean isPressed() {
		return pressed;
	}
	public boolean isReleased() {
		return released;
	}
	public boolean isHolded() {
		return holded;
	}
	
	public boolean status(Status status){
		switch (status) {
		case PRESSED:
			return isPressed();
		case RELEASED:
			return isReleased();
		case HOLDED:
			return isHolded();
		default:
			return false;
		}
	}
	
}