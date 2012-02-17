package com.gemserk.commons.gdx.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinResourceBuilder implements ResourceBuilder<Skin> {

	FileHandle skinFile;
	FileHandle textureFile;
	
	@Override
	public boolean isVolatile() {
		return false;
	}
	
	public SkinResourceBuilder skinFile(FileHandle skinFile) {
		this.skinFile = skinFile;
		return this;
	}
	
	public SkinResourceBuilder textureFile(FileHandle textureFile) {
		this.textureFile = textureFile;
		return this;
	}
	
	@Override
	public Skin build() {
		if (skinFile == null)
			throw new RuntimeException("Failed to create Skin, skinFile is a required parameter.");
		if (textureFile == null)
			throw new RuntimeException("Failed to create Skin, textureFile is a required parameter.");
		return new Skin(skinFile, textureFile);
	}

}