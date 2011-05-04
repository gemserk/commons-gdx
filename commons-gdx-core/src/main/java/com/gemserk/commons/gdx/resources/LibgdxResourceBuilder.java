package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.commons.gdx.resources.dataloaders.SoundDataLoader;
import com.gemserk.commons.gdx.resources.dataloaders.TextureDataLoader;
import com.gemserk.resources.Resource;
import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.dataloaders.DataLoader;
import com.gemserk.resources.resourceloaders.CachedResourceLoader;
import com.gemserk.resources.resourceloaders.ResourceLoaderImpl;

public class LibgdxResourceBuilder {

	ResourceManager<String> resourceManager;
	
	private boolean cacheWhenLoad = false;
	
	public void setCacheWhenLoad(boolean cacheWhenLoad) {
		this.cacheWhenLoad = cacheWhenLoad;
	}

	public LibgdxResourceBuilder(ResourceManager<String> resourceManager) {
		this.resourceManager = resourceManager;
	}

	public FileHandle internal(String file) {
		return Gdx.files.internal(file);
	}
	
	public FileHandle absolute(String file) {
		return Gdx.files.absolute(file);
	}

	public void texture(String id, String file) {
		texture(id, internal(file), true);
	}
	
	public void texture(String id, String file, boolean linearFilter) {
		texture(id, internal(file), linearFilter);
	}

	public void texture(String id, FileHandle fileHandle, boolean linearFilter) {
		resourceManager.add(id, new CachedResourceLoader<Texture>(new ResourceLoaderImpl<Texture>(new TextureDataLoader(fileHandle, linearFilter))));
		if (cacheWhenLoad)
			resourceManager.get(id).get();
	}

	public void sound(String id, String file) {
		sound(id, internal(file));
	}

	public void sound(String id, FileHandle fileHandle) {
		resourceManager.add(id, new CachedResourceLoader<Sound>(new ResourceLoaderImpl<Sound>(new SoundDataLoader(fileHandle))));
		if (cacheWhenLoad)
			resourceManager.get(id).get();
	}

	/**
	 * registers a new sprite resource builder returning a new sprite each time it is called.
	 */
	public void sprite(String id, String textureId) {
		final Resource<Texture> texture = resourceManager.get(textureId);
		resourceManager.add(id, new ResourceLoaderImpl<Sprite>(new DataLoader<Sprite>() {
			@Override
			public Sprite load() {
				return new Sprite(texture.get());
			}
		}));
	}

	/**
	 * registers a new sprite resource builder returning a new sprite each time it is called.
	 */
	public void sprite(String id, String textureId, final int x, final int y, final int width, final int height) {
		final Resource<Texture> texture = resourceManager.get(textureId);
		resourceManager.add(id, new ResourceLoaderImpl<Sprite>(new DataLoader<Sprite>() {
			@Override
			public Sprite load() {
				return new Sprite(texture.get(), x, y, width, height);
			}
		}));
	}

}