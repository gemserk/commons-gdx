package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GuiControls {
	
	static interface Builder<T> {
		
		T build();
		
	}
	
	public static class TextButtonBuilder implements Builder<TextButton> {
		
		BitmapFont font;
		
		String text;
		
		float x,y;

		Color overColor, notOverColor;
		
		TextButtonBuilder() {
			reset();
		}
		
		private void reset() {
			font = null;
			text = "";
		}
		
		public TextButtonBuilder font(BitmapFont font) {
			this.font = font;
			return this;
		}

		public TextButtonBuilder position(float x, float y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public TextButtonBuilder text(String text) {
			this.text = text;
			return this;
		}
		
		public TextButtonBuilder overColor(Color color) {
			this.overColor = color;
			return this;
		}

		public TextButtonBuilder notOverColor(Color color) {
			this.notOverColor = color;
			return this;
		}

		@Override
		public TextButton build() {
			if (font == null)
				font = new BitmapFont();
			TextButton textButton = new TextButton(font, text, x, y);
			textButton.setNotOverColor(notOverColor);
			textButton.setOverColor(overColor);
			return textButton;
		}
		
	}
	
	private static TextButtonBuilder textButtonBuilder = new TextButtonBuilder();
	
	public static TextButtonBuilder textButton() {
		return textButtonBuilder;
	}

}
