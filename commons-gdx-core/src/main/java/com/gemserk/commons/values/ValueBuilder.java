package com.gemserk.commons.values;

public class ValueBuilder {
	
	public static FloatValue floatValue(float f) {
		return new FloatValue(f);
	}
	
	public static IntValue intValue(int i) {
		return new IntValue(i);
	}
	
	public static BooleanValue booleanValue(boolean b) {
		return new BooleanValue(b);
	}

}
