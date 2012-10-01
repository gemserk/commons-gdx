package com.gemserk.commons.applications.decorators;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.animation4j.animations.AnimationManager;
import com.gemserk.animation4j.animations.events.AnimationEventHandler;
import com.gemserk.animation4j.animations.events.AutoRemoveAnimationHandler;
import com.gemserk.animation4j.gdx.converters.LibgdxConverters;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.commons.admob.AdsAnimation;
import com.gemserk.commons.admob.AdsAnimation.Type;
import com.gemserk.commons.admob.AdsParameters;
import com.gemserk.commons.applications.DimensionArray;
import com.gemserk.commons.applications.DimensionWithDensity;
import com.gemserk.commons.monitors.BooleanMonitor;
import com.gemserk.componentsengine.input.InputDevicesMonitorImpl;
import com.gemserk.componentsengine.input.LibgdxInputMappingBuilder;

public class AdSimulatorApplicationDelegate implements ApplicationListener {

	protected static final Logger logger = LoggerFactory.getLogger(AdSimulatorApplicationDelegate.class);

	private final ApplicationListener applicationListener;
	private final AdMobViewSimulatorImpl adMobViewSimulator;

	private SpriteBatch spriteBatch;
	private Sprite houseAdSprite;
	private InputDevicesMonitorImpl<String> inputMonitor;
	private boolean enabled;
	private AdsParameters adsParameters;

	private final Vector2 position = new Vector2();

	BooleanMonitor adsVisibleMonitor = new BooleanMonitor(false);

	private final Color adsColor = new Color(1f, 1f, 1f, 0f);

	AnimationManager animationManager;

	private int horizontalAlign;
	private int verticalAlign;

	public AdSimulatorApplicationDelegate(ApplicationListener applicationListener, AdMobViewSimulatorImpl adMobViewSimulator, boolean enabled) {
		this.applicationListener = applicationListener;
		this.adMobViewSimulator = adMobViewSimulator;
		this.animationManager = new AnimationManager();
		this.enabled = enabled;
	}

	@Override
	public void create() {
		applicationListener.create();
		Texture.setEnforcePotImages(false);
		Texture texture = new Texture(Gdx.files.internal("test/house-ad.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		houseAdSprite = new Sprite(texture);
		spriteBatch = new SpriteBatch();
		Texture.setEnforcePotImages(true);

		adsParameters = new AdsParameters().delay(0).horizontalAlign(0).verticalAlign(0);

		inputMonitor = new InputDevicesMonitorImpl<String>();
		new LibgdxInputMappingBuilder<String>(inputMonitor, Gdx.input) {
			{
				monitorKeys("toggleAds", Keys.BACKSPACE, Keys.STAR);
			}
		};

	}

	@Override
	public void resize(int width, int height) {
		applicationListener.resize(width, height);
		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);

		DimensionWithDensity current = getCurrentDimension(width, height);
		float densityFactor = current.getDensityFactor();
		houseAdSprite.setSize(320 * densityFactor, 50 * densityFactor);
	}

	private DimensionWithDensity getCurrentDimension(int width, int height) {
		for (int i = 0; i < DimensionArray.availableDimensions.length; i++) {
			DimensionWithDensity dimension = DimensionArray.availableDimensions[i];
			if (dimension.getWidth() == width && dimension.getHeight() == height)
				return dimension;
		}
		return DimensionArray.availableDimensions[0];
	}

	@Override
	public void render() {

		float delta = Gdx.graphics.getRawDeltaTime();

		applicationListener.render();

		animationManager.update(delta);

		inputMonitor.update();

		if (inputMonitor.getButton("toggleAds").isReleased())
			enabled = !enabled;

		if (!enabled)
			return;

		adsVisibleMonitor.update(adMobViewSimulator.isVisible());

		AdsParameters newAdsParameters = adMobViewSimulator.getAdsParameters();

		if (newAdsParameters != adsParameters && adsVisibleMonitor.isChanged()) {
			adsParameters = newAdsParameters;

			boolean visible = adsVisibleMonitor.getValue();

			animationManager.clear();

			ArrayList<AdsAnimation> animations = adsParameters.animations;

			if (visible)
				adsColor.set(1f, 1f, 1f, 1f);
			else if (animations.size() == 0)
				adsColor.set(1f, 1f, 1f, 0f);

			for (int i = 0; i < animations.size(); i++) {
				AdsAnimation adsAnimation = animations.get(i);
				if (adsAnimation.type == Type.Alpha) {

					float[] startValue = visible ? new float[] { 0f } : new float[] { 1f };
					float[] endValue = visible ? new float[] { 1f } : new float[] { 0f };

					TimelineAnimation animation = Builders.animation(Builders.timeline() //
							.value(Builders //
									.timelineValue(adsColor, LibgdxConverters.colorOpacityConverter) //
									.keyFrame(0f, startValue) //
									.keyFrame(((float) adsAnimation.duration) * 0.001f, endValue) //
							) //
							) //
							.speed(1f) //
							.delay(((float) adsParameters.delay) * 0.001f) //
							.build();

					animation.start();

					animationManager.add(animation, new AutoRemoveAnimationHandler(animationManager), new AnimationEventHandler() {

					});

				} else if (adsAnimation.type == Type.Translation) {

					float[] startValue = visible ? new float[] { 0f, 75f } : new float[] { 0f, 0f };
					float[] endValue = visible ? new float[] { 0f, 0f } : new float[] { 0f, 75f };

					TimelineAnimation animation = Builders.animation(Builders.timeline() //
							.value(Builders //
									.timelineValue(position, LibgdxConverters.vector2Converter) //
									.keyFrame(0f, startValue) //
									.keyFrame(((float) adsAnimation.duration) * 0.001f, endValue) //
							) //
							) //
							.speed(1f) //
							.delay(((float) adsParameters.delay) * 0.001f) //
							.build();

					animation.start();

					animationManager.add(animation, new AutoRemoveAnimationHandler(animationManager), new AnimationEventHandler() {

					});

				}

			}

		}

		float cx = getCenterX(adsParameters);
		float cy = getCenterY(adsParameters);

		houseAdSprite.setColor(adsColor);
		houseAdSprite.setPosition(position.x + Gdx.graphics.getWidth() * cx - houseAdSprite.getWidth() * cx, position.y + Gdx.graphics.getHeight() * cy - houseAdSprite.getHeight() * cy);

		spriteBatch.begin();
		houseAdSprite.draw(spriteBatch);
		spriteBatch.end();

		if (houseAdSprite.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && Gdx.input.justTouched()) {
			logger.info("User touched ads");
		}
	}

	private float getCenterY(AdsParameters adsParameters) {
		int verticalAlign = getVerticalAlign(adsParameters);
		if (verticalAlign == AdsParameters.VERTICAL_BOTTOM)
			return 0f;
		if (verticalAlign == AdsParameters.VERTICAL_TOP)
			return 1f;
		return 0.5f;
	}

	private int getVerticalAlign(AdsParameters adsParameters) {
		if (adsParameters.verticalAlign != null)
			this.verticalAlign = adsParameters.verticalAlign;
		return verticalAlign;
	}

	private float getCenterX(AdsParameters adsParameters) {
		int horizontalAlign = getHorizontalAlign(adsParameters);
		if (horizontalAlign == AdsParameters.HORIZONTAL_LEFT)
			return 0f;
		if (horizontalAlign == AdsParameters.HORIZONTAL_RIGHT)
			return 1f;
		return 0.5f;
	}

	private int getHorizontalAlign(AdsParameters adsParameters) {
		if (adsParameters.horizontalAlign != null)
			this.horizontalAlign = adsParameters.horizontalAlign;
		return horizontalAlign;
	}

	@Override
	public void pause() {
		applicationListener.pause();
	}

	@Override
	public void resume() {
		applicationListener.resume();
	}

	@Override
	public void dispose() {
		applicationListener.dispose();
		spriteBatch.dispose();
	}

}