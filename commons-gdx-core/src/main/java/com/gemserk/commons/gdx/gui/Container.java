package com.gemserk.commons.gdx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Container extends ControlImpl {

	private ArrayList<Control> controls;
	
	public ArrayList<Control> getControls() {
		return controls;
	}

	public Container() {
		this("");
	}

	public Container(String id) {
		controls = new ArrayList<Control>();
		setId(id);
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
	public void invalidate() {
		super.invalidate();
		for (int i = 0; i < getControls().size(); i++) 
			getControls().get(i).invalidate();
	}

}
