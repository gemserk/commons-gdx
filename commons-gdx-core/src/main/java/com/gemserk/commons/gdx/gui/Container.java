package com.gemserk.commons.gdx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Container  implements Control {

	ArrayList<Control> controls;
	
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
	
}
