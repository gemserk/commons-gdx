package com.gemserk.componentsengine.utils;

public class Interval{
	final int min;
	final int max;

	public Interval(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
	
	public int getLength(){
		return max - min;
	}
}