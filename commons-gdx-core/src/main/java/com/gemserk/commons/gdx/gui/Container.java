package com.gemserk.commons.gdx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Container extends ControlImpl {

	private ArrayList<Control> controls;
	private float width, height;
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}

	public ArrayList<Control> getControls() {
		return controls;
	}

	public Container() {
		this("");
	}

	public Container(String id) {
		this(id, 0f, 0f);
	}
	
	public Container(String id, float width, float height) {
		controls = new ArrayList<Control>();
		setId(id);
		this.width = width;
		this.height = height;
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
		control.setParent(this);
	}

	public void remove(Control control) {
		controls.remove(control);
		control.setParent(new NullControl());
	}
	
	public void removeAll() {
		controls.clear();
	}

	public <T extends Control> T findControl(String id) {
		for (int i = 0; i < controls.size(); i++) {
			Control control = controls.get(i);
			if (id.equals(control.getId()))
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
	public void invalidate() {
		super.invalidate();
		for (int i = 0; i < getControls().size(); i++)
			getControls().get(i).invalidate();
	}

}
