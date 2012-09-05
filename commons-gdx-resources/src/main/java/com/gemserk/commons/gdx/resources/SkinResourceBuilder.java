package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gemserk.resources.ResourceManager;

public class SkinResourceBuilder implements ResourceBuilder<Skin> {

	private final ResourceManager<String> resourceManager;

	FileHandle skinFile;
	// FileHandle textureFile;
	String textureAtlasResourceId;

	@Override
	public boolean isVolatile() {
		return false;
	}

	public SkinResourceBuilder(ResourceManager<String> resourceManager) {
		this.resourceManager = resourceManager;
	}

	public SkinResourceBuilder skinFile(FileHandle skinFile) {
		this.skinFile = skinFile;
		return this;
	}

	// public SkinResourceBuilder textureFile(FileHandle textureFile) {
	// this.textureFile = textureFile;
	// return this;
	// }

	public SkinResourceBuilder textureAtlas(String textureAtlasResourceId) {
		this.textureAtlasResourceId = textureAtlasResourceId;
		return this;
	}

	@Override
	public Skin build() {
		if (skinFile == null)
			throw new RuntimeException("Failed to create Skin, skinFile is a required parameter.");
		// if (textureFile == null)
		// throw new RuntimeException("Failed to create Skin, textureFile is a required parameter.");
		TextureAtlas textureAtlas = resourceManager.getResourceValue(textureAtlasResourceId);
		if (textureAtlas == null)
			throw new RuntimeException("Failed to create Skin, textureAtlas " + textureAtlasResourceId + " resource not found.");
		// return new Skin(skinFile, textureFile);
		return new Skin(skinFile, textureAtlas);
	}
}