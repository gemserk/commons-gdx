package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.gemserk.commons.gdx.resources.dataloaders.SoundDataLoader;
import com.gemserk.commons.gdx.resources.dataloaders.TextureDataLoader;
import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.resourceloaders.CachedResourceLoader;
import com.gemserk.resources.resourceloaders.ResourceLoaderImpl;

public class LibgdxResourceBuilder {

	ResourceManager<String> resourceManager;

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
		texture(id, internal(file));
	}

	public void texture(String id, FileHandle fileHandle) {
		resourceManager.add(id, new CachedResourceLoader<Texture>(new ResourceLoaderImpl<Texture>(new TextureDataLoader(fileHandle))));
	}

	public void sound(String id, String file) {
		sound(id, internal(file));
	}

	public void sound(String id, FileHandle fileHandle) {
		resourceManager.add(id, new CachedResourceLoader<Sound>(new ResourceLoaderImpl<Sound>(new SoundDataLoader(fileHandle))));
	}

}