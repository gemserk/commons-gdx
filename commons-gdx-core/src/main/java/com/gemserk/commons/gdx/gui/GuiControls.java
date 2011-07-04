package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gemserk.commons.gdx.gui.TextButton.ButtonHandler;

public class GuiControls {
	
	static interface Builder<T> {
		
		T build();
		
	}
	
	public static class TextButtonBuilder implements Builder<TextButton> {
		
		BitmapFont font;
		
		String text;
		
		float x,y;

		Color overColor, notOverColor;

		float w;

		float h;

		ButtonHandler buttonHandler = new ButtonHandler();
		
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
		
		public TextButtonBuilder boundsOffset(float w, float h) {
			this.w = w;
			this.h = h;
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
		
		public TextButtonBuilder handler(ButtonHandler buttonHandler) {
			this.buttonHandler = buttonHandler;
			return this;
		}
		

		@Override
		public TextButton build() {
			if (font == null)
				font = new BitmapFont();
			TextButton textButton = new TextButton(font, text, x, y);
			textButton.setNotOverColor(notOverColor);
			textButton.setOverColor(overColor);
			textButton.setBoundsOffset(w, h);
			textButton.setButtonHandler(buttonHandler);
			return textButton;
		}
		
	}
	
	private static TextButtonBuilder textButtonBuilder = new TextButtonBuilder();
	
	public static TextButtonBuilder textButton() {
		return textButtonBuilder;
	}

}
