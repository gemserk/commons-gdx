package com.gemserk.commons.gdx.camera;

public interface Camera {

	float getX();

	float getY();

	float getZoom();

	float getAngle();

	void setPosition(float x, float y);

	void setZoom(float zoom);

	void setAngle(float angle);

}