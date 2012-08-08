package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gemserk.animation4j.FrameAnimationImpl;
import com.gemserk.animation4j.gdx.Animation;
import com.gemserk.resources.ResourceManager;

public class AnimationFromTextureAtlasResourceBuilder implements ResourceBuilder<Animation> {

	public static class FrameTransformation {

		public void transform(Sprite sprite) {
		}

	}

	ResourceManager<String> resourceManager;
	Array<Sprite> sprites = null;

	String textureAtlasId;
	String prefix;

	int endFrame = -1;
	int startFrame = -1;

	int time;
	int times[];

	boolean loop;

	FrameTransformation frameTransformation = new FrameTransformation();

	@Override
	public boolean isVolatile() {
		return true;
	}

	public AnimationFromTextureAtlasResourceBuilder prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public AnimationFromTextureAtlasResourceBuilder loop(boolean loop) {
		this.loop = loop;
		return this;
	}

	public AnimationFromTextureAtlasResourceBuilder frameTransformation(FrameTransformation frameTransformation) {
		this.frameTransformation = frameTransformation;
		return this;
	}

	public AnimationFromTextureAtlasResourceBuilder subAnimation(int startFrame, int endFrame) {
		this.startFrame = startFrame;
		this.endFrame = endFrame;
		return this;
	}

	public AnimationFromTextureAtlasResourceBuilder times(int time, int... times) {
		this.time = time;
		this.times = times;
		return this;
	}

	public AnimationFromTextureAtlasResourceBuilder(ResourceManager<String> resourceManager, String textureAtlasId) {
		this.resourceManager = resourceManager;
		this.textureAtlasId = textureAtlasId;
	}

	@Override
	public Animation build() {

		TextureAtlas textureAtlas = resourceManager.getResourceValue(textureAtlasId);

		if (sprites == null) {
			try {
				sprites = textureAtlas.createSprites(prefix);
			} catch (GdxRuntimeException e) {
				throw new RuntimeException("Failed to create animation from texture atlas " + textureAtlasId, e);
			}
		}

		if (endFrame == -1)
			endFrame = sprites.size - 1;

		if (startFrame == -1)
			startFrame = 0;

		Sprite[] frames = new Sprite[endFrame - startFrame + 1];
		int frameNumber = startFrame;

		for (int i = 0; i < frames.length; i++) {
			Sprite sprite = sprites.get(frameNumber);

			if (sprite instanceof AtlasSprite)
				frames[i] = new AtlasSprite(((AtlasSprite) sprite).getAtlasRegion());
			else
				frames[i] = new Sprite(sprite);

			frameTransformation.transform(frames[i]);

			frameNumber++;
		}

		int framesCount = frames.length;

		float[] newTimes = new float[framesCount - 1];
		int lastTime = time;

		// added convert from int time in milliseconds to float time in seconds

		for (int i = 0; i < framesCount - 1; i++) {
			if (i < times.length) {
				newTimes[i] = ((float) times[i]) * 0.001f;
				lastTime = times[i];
			} else
				newTimes[i] = ((float) lastTime) * 0.001f;
		}

		FrameAnimationImpl frameAnimation = new FrameAnimationImpl(0.001f * (float) time, newTimes);
		frameAnimation.setLoop(loop);

		return new Animation(frames, frameAnimation);
	}

}