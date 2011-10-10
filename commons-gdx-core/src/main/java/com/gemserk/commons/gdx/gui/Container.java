package com.gemserk.commons.gdx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Container implements Control {

	private final String id;
	private ArrayList<Control> controls;
	
	Control parent = new NullControl();

	public ArrayList<Control> getControls() {
		return controls;
	}

	public Container() {
		this("");
	}

	public Container(String id) {
		controls = new ArrayList<Control>();
		this.id = id;
	}

	@Override
	public void update() {
		for (int i = 0; i < controls.size(); i++)
			controls.get(i).update();
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		for (int i = 0; i < controls.size(); i++)
			controls.get(i).draw(spriteBatch);
	}

	public void add(Control control) {
		controls.add(control);
	}

	public void remove(Control control) {
		controls.remove(control);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	public <T extends Control> T findControl(String id) {
		for (int i = 0; i < controls.size(); i++) {
			Control control = controls.get(i);
			if (control.getId().equals(id))
				return (T) control;
			if (control instanceof Container) {
				Container container = (Container) control;
				T child = (T) container.findControl(id);
				if (child != null)
					return (T) child;
			}
		}
		return null;
	}

	@Override
	public void setPosition(float x, float y) {
		
	}

	@Override
	public void setX(float x) {
		
	}

	@Override
	public void setY(float y) {
		
	}

	@Override
	public void setParent(Control parent) {
		this.parent = parent;
	}

}
