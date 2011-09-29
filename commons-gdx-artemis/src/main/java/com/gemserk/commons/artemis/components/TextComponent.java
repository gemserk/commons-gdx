package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TextComponent extends Component {

	public Color color = new Color(1f, 1f, 1f, 1f);
	
	public float x,y;
	public float cx, cy;
	
	public CharSequence text;
	public BitmapFont font;
	
	public TextComponent() {
		
	}

	public TextComponent(CharSequence text, BitmapFont font, float x, float y, float cx, float cy) {
		this.text = text;
		this.font = font;
		this.x = x;
		this.y = y;
		this.cx = cx;
		this.cy = cy;
	}
	

}
