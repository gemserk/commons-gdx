package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.commons.gdx.gui.TextButton.ButtonHandler;

public class GuiControls {

	static interface Builder<T> {

		T build();

	}

	public static class TextButtonBuilder implements Builder<TextButton> {

		BitmapFont font;

		String text;

		float x, y;

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

	public static class ImageButtonBuilder implements Builder<ImageButton> {
		
		ImageButton imageButton;
		
		private ImageButtonBuilder newButton(Sprite sprite) {
			imageButton = new ImageButton(sprite);
			return this;
		}
		
		public ImageButtonBuilder position(float x, float y) {
			imageButton.setPosition(x, y);
			return this;
		}
		
		public ImageButtonBuilder size(float w, float h) {
			imageButton.setSize(w, h);
			return this;
		}
		
		public ImageButtonBuilder color(Color color) {
			imageButton.setColor(color);
			return this;
		}

		public ImageButtonBuilder color(float r, float g, float b, float a) {
			imageButton.setColor(r, g, b, a);
			return this;
		}

		public ImageButtonBuilder handler(ButtonHandler handler) {
			imageButton.setButtonHandler(handler);
			return this;
		}

		@Override
		public ImageButton build() {
			return imageButton;
		}

	}
	
	public static class LabelBuilder implements Builder<Text> {
		
		Text text;

		private LabelBuilder newText(String text) {
			this.text = new Text(text);
			return this;
		}
		
		public LabelBuilder position(float x, float y) {
			text.setPosition(x, y);
			return this;
		}
		
		public LabelBuilder center(float cx, float cy) {
			text.setCenter(cx, cy);
			return this;
		}
		
		public LabelBuilder font(BitmapFont font) {
			text.setFont(font);
			return this;
		}
		
		public LabelBuilder color(float r, float g, float b, float a) {
			text.setColor(r, g, b, a);
			return this;
		}
		
		public LabelBuilder color(Color color) {
			text.setColor(color);
			return this;
		}
		
		@Override
		public Text build() {
			if (text.getFont() == null)
				text.setFont(new BitmapFont());
			return text;
		}
		
	}

	private static TextButtonBuilder textButtonBuilder = new TextButtonBuilder();
	private static ImageButtonBuilder imageButtonBuilder = new ImageButtonBuilder();
	private static LabelBuilder labelBuilder = new LabelBuilder();

	public static TextButtonBuilder textButton() {
		return textButtonBuilder;
	}
	
	public static ImageButtonBuilder imageButton(Sprite sprite) {
		return imageButtonBuilder.newButton(sprite);
	}
	
	public static LabelBuilder label(String text) {
		return labelBuilder.newText(text);
	}


}
