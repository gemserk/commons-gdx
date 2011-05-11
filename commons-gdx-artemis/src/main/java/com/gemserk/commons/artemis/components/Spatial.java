package com.gemserk.commons.artemis.components;

/**
 * Provides an abstraction of spatial concept.
 * 
 * @author acoppes
 * 
 */
public interface Spatial {

	float getX();

	float getY();

	void setPosition(float x, float y);

	float getAngle();

	void setAngle(float angle);
	
	float getWidth();
	
	float getHeight();
	
	void setSize(float width, float height);

}