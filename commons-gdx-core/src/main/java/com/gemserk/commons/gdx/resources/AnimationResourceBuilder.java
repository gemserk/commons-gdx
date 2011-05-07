package com.gemserk.commons.gdx.resources;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.animation4j.FrameAnimationImpl;
import com.gemserk.animation4j.gdx.Animation;
import com.gemserk.resources.ResourceManager;

public class AnimationResourceBuilder implements ResourceBuilder<Animation> {

	private final ResourceManager<String> resourceManager;
	
	private final String spriteSheetId;
	
	private class Frame {
		
		int x, y, w, h;
		
		int time;
		
		public Frame(int x, int y, int w, int h, int time) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.time = time;
		}
		
	}
	
	private ArrayList<Frame> frames; 
	
	private boolean loop = false;

	public AnimationResourceBuilder loop(boolean loop) {
		this.loop = loop;
		return this;
	}

	public AnimationResourceBuilder frame(int x, int y, int w, int h, int time) {
		frames.add(new Frame(x, y, w, h, time));
		return this;
	}
	
	public AnimationResourceBuilder(ResourceManager<String> resourceManager, String spriteSheetId) {
		this.resourceManager = resourceManager;
		this.spriteSheetId = spriteSheetId;
		this.frames = new ArrayList<Frame>();
	}

	@Override
	public Animation build() {
		Texture spriteSheet = resourceManager.getResourceValue(spriteSheetId);
		Sprite[] frames = new Sprite[this.frames.size()];
		int[] times = new int[this.frames.size()];
		for (int i = 0; i < frames.length; i++) { 
			Frame frame = this.frames.get(i);
			frames[i] = new Sprite(spriteSheet, frame.x, frame.y, frame.w, frame.h);
			times[i] = frame.time;
		}
		return new Animation(frames, new FrameAnimationImpl(loop, times));
	}

	@Override
	public boolean isCached() {
		return false;
	}

}