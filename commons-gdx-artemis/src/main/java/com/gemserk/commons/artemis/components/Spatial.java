package com.gemserk.commons.artemis.components;

import com.badlogic.gdx.math.Vector2;

/**
 * Provides an abstraction of spatial concept.
 * 
 * @author acoppes
 * 
 */
public interface Spatial {

	float getX();

	float getY();
	
	Vector2 getPosition();

	void setPosition(float x, float y);

	float getAngle();

	void setAngle(float angle);
	
	float getWidth();
	
	float getHeight();
	
	void setSize(float width, float height);
	
	void set(Spatial spatial);

}