package com.gemserk.commons.gdx.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public interface MeshSprite {

	void setPosition(float x, float y);
	
	void setX(float x);
	
	void setY(float y);
	
	void setZ(float z);
	
	void setBounds(float x, float y, float width, float height);

	void setSize(float width, float height);

	void translateX(float xAmount);

	void translateY(float yAmount);

	void translate(float xAmount, float yAmount);

	void setColor(Color tint);

	void setColor(float r, float g, float b, float a);

	void setOrigin(float originX, float originY);

	void setRotation(float degrees);

	void rotate(float degrees);

	void setScale(float scaleXY);

	void setScale(float scaleX, float scaleY);

	void scale(float amount);

	float[] getVertices();
	
	short[] getIndices();

	Rectangle getBoundingRectangle();

	float getX();

	float getY();
	
	float getZ();

	float getWidth();

	float getHeight();

	float getOriginX();

	float getOriginY();

	float getRotation();

	float getScaleX();

	float getScaleY();

	Color getColor();

	Texture getTexture();

}