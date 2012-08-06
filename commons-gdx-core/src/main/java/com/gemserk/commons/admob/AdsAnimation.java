package com.gemserk.commons.admob;

public class AdsAnimation {

	public static enum Type {
		Alpha, Translation
	}

	public Type type;
	public long duration;

	public AdsAnimation(Type type, long duration) {
		this.type = type;
		this.duration = duration;
	}

}