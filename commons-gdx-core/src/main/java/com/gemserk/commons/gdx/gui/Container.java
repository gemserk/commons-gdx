package com.gemserk.commons.gdx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Container  implements Control {

	private ArrayList<Control> controls;
	
	public ArrayList<Control> getControls() {
		return controls;
	}
	
	public Container() {
		controls = new ArrayList<Control>();
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
	
}
