package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gemserk.componentsengine.properties.Property;

public class TextComponent extends Component {

	private final Property<String> text;

	private final Property<BitmapFont> font;

	private final Property<Color> color;
	
	public BitmapFont getFont() {
		return this.font.get();
	}
	
	public void setFont(BitmapFont font) {
		this.font.set(font);
	}
	
	public String getText() {
		return this.text.get();
	}

	public void setText(String text) {
		this.text.set(text);
	}
	
	public Color getColor() {
		return color.get();
	}
	
	public void setColor(Color color) {
		this.color.set(color);
	}
	
	public TextComponent(Property<String> text, Property<BitmapFont> font, Property<Color> color) {
		this.text = text;
		this.font = font;
		this.color = color;
	}

}