package com.gemserk.commons.gdx.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.utils.Logger;

public class AssetManagerRestoreHack extends AssetManager {

	public ArrayList<TextureParameter> textures = new ArrayList<TextureLoader.TextureParameter>();

	@Override
	public synchronized <T> String getAssetFileName(T asset) {
		return "";
	}

	@Override
	public synchronized int getReferenceCount(String fileName) {
		return 0;
	}

	@Override
	public synchronized void setReferenceCount(String fileName, int refCount) {
	}

	@Override
	public synchronized void unload(String fileName) {
	}

	@Override
	public synchronized <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
		TextureParameter textureParameters = (TextureParameter) parameter;
		textures.add(textureParameters);
	}

	@Override
	public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, AssetLoader<T, P> loader) {
	}

	@Override
	public void finishLoading() {
	}

	/************
	 * *********** ************* ********** ******** ******
	 * *****/

	@Override
	public synchronized <T> T get(String fileName, Class<T> type) {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized <T> boolean containsAsset(T asset) {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized boolean isLoaded(String fileName) {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public synchronized boolean isLoaded(String fileName, Class type) {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized <T> void load(String fileName, Class<T> type) {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public synchronized void load(AssetDescriptor desc) {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized boolean update() {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized int getLoadedAssets() {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized int getQueuedAssets() {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized float getProgress() {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized void setErrorListener(AssetErrorListener listener) {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized void dispose() {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized void clear() {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public Logger getLogger() {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}

	@Override
	public synchronized String getDiagnostics() {
		throw new UnsupportedOperationException("This method is not supported in this class");
	}
}
