package com.gemserk.commons.gdx.camera;

public class MultipleVirtualViewportBuilder {

	private final float minWidth;
	private final float minHeight;
	private final float maxWidth;
	private final float maxHeight;

	public MultipleVirtualViewportBuilder(float minWidth, float minHeight, float maxWidth, float maxHeight) {
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}

	public VirtualViewport getVirtualViewport(float width, float height) {
		if (width >= minWidth && width <= maxWidth && height >= minHeight && height <= maxHeight)
			return new VirtualViewport(width, height, true);

		float aspect = width / height;

		float scaleForMinSize = minWidth / width;
		float scaleForMaxSize = maxWidth / width;

		float virtualViewportWidth = width * scaleForMaxSize;
		float virtualViewportHeight = virtualViewportWidth / aspect;

		if (virtualViewportHeight >= minHeight && virtualViewportHeight <= maxHeight)
			return new VirtualViewport(virtualViewportWidth, virtualViewportHeight, true);

		virtualViewportWidth = width * scaleForMinSize;
		virtualViewportHeight = virtualViewportWidth / aspect;

		return new VirtualViewport(virtualViewportWidth, virtualViewportHeight, true);
	}

}