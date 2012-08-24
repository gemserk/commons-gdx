package com.gemserk.commons.gdx.resources;

import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.dataloaders.DataLoader;

/**
 * Defines an API for DataLoader builders.
 */
public interface DataLoaderBuilder<T> {

	/**
	 * Returns true if the DataLoader should be considered volatile and not cached, false otherwise.
	 */
	boolean isVolatile();

	/**
	 * Defines how to build the data to be used in the DataLoader.
	 */
	DataLoader<T> build(ResourceManager<String> resourceManager);

}