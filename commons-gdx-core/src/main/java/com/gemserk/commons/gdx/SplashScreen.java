package com.gemserk.commons.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gemserk.animation4j.event.AnimationEvent;
import com.gemserk.animation4j.event.AnimationEventHandler;
import com.gemserk.animation4j.event.AnimationHandlerManager;
import com.gemserk.animation4j.gdx.converters.LibgdxConverters;
import com.gemserk.animation4j.timeline.TimelineAnimationBuilder;
import com.gemserk.animation4j.timeline.TimelineValueBuilder;
import com.gemserk.animation4j.timeline.sync.ObjectSynchronizer;
import com.gemserk.animation4j.timeline.sync.ReflectionObjectSynchronizer;
import com.gemserk.animation4j.timeline.sync.SynchrnonizedAnimation;
import com.gemserk.animation4j.timeline.sync.TimelineSynchronizer;

public class SplashScreen extends ScreenAdapter {

	private SpriteBatch spriteBatch;

	private Color color = Color.BLACK;

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	private SynchrnonizedAnimation splashAnimation;

	private Sprite sprite;

	private AnimationHandlerManager animationHandlerManager;

	public SplashScreen(Texture texture) {
		this.sprite = new Sprite(texture);
		this.spriteBatch = new SpriteBatch();

		ObjectSynchronizer objectSynchronizer = new ReflectionObjectSynchronizer();

		splashAnimation = new SynchrnonizedAnimation(new TimelineAnimationBuilder() {
			{
				delay(1000);
				value("color", new TimelineValueBuilder<Color>(LibgdxConverters.color()) {
					{
						keyFrame(0, new Color(1f, 1f, 1f, 0f));
						keyFrame(2000, new Color(1f, 1f, 1f, 1f));
						keyFrame(4000, new Color(1f, 1f, 1f, 1f));
						keyFrame(4250, new Color(1f, 1f, 1f, 0.7f));
						keyFrame(4500, new Color(0f, 0f, 0f, 0f));
					}
				});
				speed(1.3f);
			}
		}.build(), new TimelineSynchronizer(objectSynchronizer, this));
		
		animationHandlerManager = new AnimationHandlerManager();
		animationHandlerManager.with(new AnimationEventHandler(){
			@Override
			public void onAnimationFinished(AnimationEvent e) {
				onSplashScreenFinished();
			}
		}).handleChangesOf(splashAnimation);

		splashAnimation.start(1);

	}

	@Override
	public void render(float delta) {
		int centerX = Gdx.graphics.getWidth() / 2;
		int centerY = Gdx.graphics.getHeight() / 2;

		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();

		Texture logo = sprite.getTexture();
		
		float aspect = (float) logo.getWidth() / (float) logo.getHeight();
		float newWidth = Gdx.graphics.getWidth() - 50;
		float newHeight = newWidth / aspect;

		sprite.setColor(color);
		sprite.setPosition(centerX - newWidth / 2, centerY - newHeight / 2);
		sprite.setSize(newWidth, newHeight);

		sprite.draw(spriteBatch);

		spriteBatch.end();

		splashAnimation.update(delta * 1000);
		
		animationHandlerManager.checkAnimationChanges();
		
	}

	protected void onSplashScreenFinished() {
		
	}

	@Override
	public void show() {

	}

	@Override
	public void dispose() {
		this.spriteBatch.dispose();
	}

}