package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.gemserk.animation4j.gdx.scenes.scene2d.Scene2dConverters;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.animation4j.timeline.TimelineAnimation;

public class Actors {
	
	public static Actor topToast(final String text, final float time, final Skin skin) {

		return new Window("", skin) {

			TimelineAnimation timelineAnimation;

			{
				setMovable(false);

				defaults().spaceBottom(5);

				Label toastLabel = new Label(text, skin);

				width = Gdx.graphics.getWidth() * 0.95f;
				height = toastLabel.getTextBounds().height + 20 + getStyle().titleFont.getLineHeight();

				x = Gdx.graphics.getWidth() * 0.5f - width * 0.5f;

				row().fill().expandX();
				add(toastLabel).align(Align.LEFT).fill(0f, 0f).padLeft(20);

				float outsideY = Gdx.graphics.getHeight() + height;
				float insideY = Gdx.graphics.getHeight() - height + getStyle().titleFont.getLineHeight();

				y = outsideY;

				timelineAnimation = Builders.animation( //
						Builders.timeline() //
								.value(Builders.timelineValue(this, Scene2dConverters.actorPositionTypeConverter) //
										.keyFrame(0f, new float[] { x, outsideY }, //
												InterpolationFunctions.linear(), InterpolationFunctions.easeIn()) //
										.keyFrame(1f, new float[] { x, insideY }) //
										.keyFrame(4f, new float[] { x, insideY }, //
												InterpolationFunctions.linear(), InterpolationFunctions.easeOut()) //
										.keyFrame(5f, new float[] { x, outsideY }) //
								) //
						) //
						.started(true) //
						.delay(0f) //
						.speed(5f / time) //
						.build();
			}

			@Override
			public void act(float delta) {
				super.act(delta);
				timelineAnimation.update(delta);

				if (timelineAnimation.isFinished()) {
					getStage().removeActor(this);
				}
			}

		};

	}

}
