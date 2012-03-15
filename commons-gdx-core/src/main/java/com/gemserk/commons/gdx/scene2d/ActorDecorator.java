package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

public class ActorDecorator {

	private Actor actor;

	private float cx, cy;
	private float x, y;

	public ActorDecorator() {
		this(0f, 0f, 0.5f, 0.5f);
	}

	public ActorDecorator(float x, float y, float cx, float cy) {
		this.x = x;
		this.y = y;
		this.cx = cx;
		this.cy = cy;
	}

	public Actor getActor() {
		return actor;
	}

	public float getWidth() {
		return actor.width;
	}

	public float getHeight() {
		return actor.height;
	}

	public void setWidth(float width) {
		actor.width = width;
		center(actor, x, y, cx, cy);
	}

	public void setHeight(float height) {
		actor.height = height;
		center(actor, x, y, cx, cy);
	}

	public void setActor(Actor actor) {
		this.actor = actor;
		this.x = actor.x + actor.width * cx;
		this.y = actor.y + actor.height * cy;
		center(actor, x, y, cx, cy);
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	private void center(Actor actor, float x, float y, float cx, float cy) {
		actor.x = x - actor.width * cx;
		actor.y = y - actor.height * cy;
		if (actor instanceof Table)
			((Table) actor).invalidate();
	}

}