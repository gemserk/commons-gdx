package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gemserk.animation4j.gdx.scenes.scene2d.Scene2dConverters;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.animation4j.timeline.TimelineAnimation;

public class Actors {
	
	public static Actor topToast(final String text, final float time, final Skin skin) {
		final Window window = new Window("", skin);
		
		window.setMovable(false);

		window.defaults().spaceBottom(5);

		Label toastLabel = new Label(text, skin);
		toastLabel.setAlignment(Align.left);
		toastLabel.setWrap(true);

		window.row().fillX().expandX();
		window.add(toastLabel).fillX().padLeft(10);
		
		window.invalidate();
		window.setKeepWithinStage(false);
		
		window.setWidth(Gdx.graphics.getWidth() * 0.95f);
		window.setHeight(toastLabel.getTextBounds().height + 20 + window.getStyle().titleFont.getLineHeight());

		window.setX(Gdx.graphics.getWidth() * 0.5f - window.getWidth() * 0.5f);

		float outsideY = Gdx.graphics.getHeight() + window.getHeight();
		float insideY = Gdx.graphics.getHeight() - window.getHeight() + window.getStyle().titleFont.getLineHeight();

		window.setY(outsideY);
		
		final TimelineAnimation timelineAnimation = Builders.animation( //
				Builders.timeline() //
						.value(Builders.timelineValue(window, Scene2dConverters.actorPositionTypeConverter) //
								.keyFrame(0f, new float[] { window.getX(), outsideY }, //
										InterpolationFunctions.linear(), InterpolationFunctions.easeIn()) //
								.keyFrame(1f, new float[] { window.getX(), insideY }) //
								.keyFrame(4f, new float[] { window.getX(), insideY }, //
										InterpolationFunctions.linear(), InterpolationFunctions.easeOut()) //
								.keyFrame(5f, new float[] { window.getX(), outsideY }) //
						) //
				) //
				.started(true) //
				.delay(0f) //
				.speed(5f / time) //
				.build();
		
		window.addAction(new ActionAdapter(){ 
			@Override
			public boolean update(float delta) {
				timelineAnimation.update(delta);
				if (timelineAnimation.isFinished()) { 
					window.remove();
					return true;
				}
				return false;
			}
		});
		
		return window;
	}
	
	public static interface DialogListener {
		
		void optionSelected(int option);
		
	}
	
	public static Actor twoOptionsDialog(String[] texts, final DialogListener dialogListener, String titleText, String firstOption, String secondOption, Skin skin) {
		Window window = new Window(titleText, skin);

		window.setMovable(false);

		TextButton firstOptionButton = new TextButton(firstOption, skin);
		TextButton secondOptionButton = new TextButton(secondOption, skin);

		firstOptionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialogListener.optionSelected(0);
			}
		});

		secondOptionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialogListener.optionSelected(1);
			}
		});

		window.defaults().spaceBottom(10);
		window.row().fill().expandX();

		for (int i = 0; i < texts.length; i++) {
			window.row().padLeft(20);
			Label label = new Label(texts[i], skin);
			label.setWrap(true);
			window.add(label).align(Align.left).colspan(2).fillX();
		}

		window.row().fill().expandX();
		window.add(firstOptionButton).align(Align.center).padLeft(20).padRight(20).expandX();
		window.add(secondOptionButton).align(Align.center).padLeft(20).padRight(20).expandX();

		ScrollPane scrollPane = new ScrollPane(window);

		scrollPane.setWidth(Gdx.graphics.getWidth() * 0.95f);
		scrollPane.setHeight(Gdx.graphics.getHeight() * 0.95f);
		scrollPane.setX(Gdx.graphics.getWidth() * 0.5f - scrollPane.getWidth() * 0.5f);
		scrollPane.setY(Gdx.graphics.getHeight() * 0.5f - scrollPane.getHeight() * 0.5f);

		return scrollPane;
	}
	
	public static Actor threeOptionsDialog(String[] texts, final DialogListener dialogListener, String titleText, String firstOption, String secondOption, String thirdOption, Skin skin) {
		Window window = new Window(titleText, skin);

		window.setMovable(false);

		TextButton firstOptionButton = new TextButton(firstOption, skin);
		TextButton secondOptionButton = new TextButton(secondOption, skin);
		TextButton thirdOptionButton = new TextButton(thirdOption, skin);

		firstOptionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialogListener.optionSelected(0);
			}
		});

		secondOptionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialogListener.optionSelected(1);
			}
		});
		
		thirdOptionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialogListener.optionSelected(2);
			}
		});

		window.defaults().spaceBottom(10);
		window.row().fill().expandX();

		for (int i = 0; i < texts.length; i++) {
			window.row().padLeft(20);
			Label label = new Label(texts[i], skin);
			label.setWrap(true);
			window.add(label).align(Align.left).colspan(2).fillX();
		}

		window.row().fill().expandX().padTop(10);
		window.add(firstOptionButton).align(Align.center).padLeft(30).padRight(30);
		window.row().fill().expandX();
		window.add(secondOptionButton).align(Align.center).padLeft(30).padRight(30);
		window.row().fill().expandX();
		window.add(thirdOptionButton).align(Align.center).padLeft(30).padRight(30);

		ScrollPane scrollPane = new ScrollPane(window);

		scrollPane.setWidth(Gdx.graphics.getWidth() * 0.95f);
		scrollPane.setHeight(Gdx.graphics.getHeight() * 0.95f);
		scrollPane.setX(Gdx.graphics.getWidth() * 0.5f - scrollPane.getWidth() * 0.5f);
		scrollPane.setY(Gdx.graphics.getHeight() * 0.5f - scrollPane.getHeight() * 0.5f);
		
		return scrollPane;
	}


}
