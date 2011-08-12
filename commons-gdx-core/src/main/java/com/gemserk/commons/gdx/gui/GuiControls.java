package com.gemserk.commons.gdx.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GuiControls {

	static interface Builder<T> {

		T build();

	}

	public static class TextButtonBuilder implements Builder<TextButton> {

		BitmapFont defaultFont = new BitmapFont();

		TextButton textButton;

		TextButtonBuilder() {
			reset();
		}

		private void reset() {
			textButton = new TextButton(defaultFont, "", 0, 0);
		}

		public TextButtonBuilder font(BitmapFont font) {
			textButton.setFont(font);
			return this;
		}

		public TextButtonBuilder position(float x, float y) {
			textButton.setPosition(x, y);
			return this;
		}

		public TextButtonBuilder boundsOffset(float w, float h) {
			textButton.setBoundsOffset(w, h);
			return this;
		}

		public TextButtonBuilder id(String id) {
			textButton.setId(id);
			return this;
		}

		public TextButtonBuilder text(String text) {
			textButton.setText(text);
			return this;
		}

		public TextButtonBuilder overColor(Color color) {
			textButton.setOverColor(color);
			return this;
		}

		public TextButtonBuilder overColor(float r, float g, float b, float a) {
			textButton.setOverColor(r, g, b, a);
			return this;
		}

		public TextButtonBuilder notOverColor(Color color) {
			textButton.setNotOverColor(color);
			return this;
		}

		public TextButtonBuilder notOverColor(float r, float g, float b, float a) {
			textButton.setNotOverColor(r, g, b, a);
			return this;
		}

		public TextButtonBuilder handler(ButtonHandler buttonHandler) {
			textButton.setButtonHandler(buttonHandler);
			return this;
		}

		public TextButtonBuilder alignment(HAlignment alignment) {
			textButton.setAlignment(alignment);
			return this;
		}

		public TextButtonBuilder center(float cx, float cy) {
			textButton.setCenter(cx, cy);
			return this;
		}

		@Override
		public TextButton build() {
			TextButton textButton = this.textButton;
			reset();
			return textButton;
		}

	}

	public static class ImageButtonBuilder implements Builder<ImageButton> {

		ImageButton imageButton;

		private ImageButtonBuilder newButton(Sprite sprite) {
			imageButton = new ImageButton(sprite);
			return this;
		}

		public ImageButtonBuilder id(String id) {
			imageButton.setId(id);
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

		public ImageButtonBuilder center(float cx, float cy) {
			imageButton.setCenter(cx, cy);
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

		public LabelBuilder id(String id) {
			text.setId(id);
			return this;
		}

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
