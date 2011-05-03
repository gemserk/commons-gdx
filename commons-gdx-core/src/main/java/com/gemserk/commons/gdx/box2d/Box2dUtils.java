package com.gemserk.commons.gdx.box2d;

import com.badlogic.gdx.math.Vector2;

public class Box2dUtils {
	
	public static Vector2[] initArray(int count) {
		return initArray(new Vector2[count]);
	}

	static Vector2[] initArray(Vector2[] array) {
		for (int i = 0; i < array.length; i++) 
			array[i] = new Vector2();
		return array;
	}
	
	public static Vector2[] createRectangle(float width, float height) {
		return createRectangle(width, height, initArray(4));
	}

	public static Vector2[] createRectangle(float width, float height, Vector2[] vertices) {
		vertices[0].set(-width * 0.5f, -height * 0.5f);
		vertices[1].set(width * 0.5f, -height * 0.5f);
		vertices[2].set(width * 0.5f, height * 0.5f);
		vertices[3].set(-width * 0.5f, height * 0.5f);
		return vertices;
	}

}
