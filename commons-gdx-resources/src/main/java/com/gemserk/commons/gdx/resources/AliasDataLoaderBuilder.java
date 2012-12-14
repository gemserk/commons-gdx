package com.gemserk.commons.gdx.resources;

import com.gemserk.resources.Resource;
import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.dataloaders.DataLoader;

public class AliasDataLoaderBuilder<T> implements DataLoaderBuilder<T> {
	private final String resourceId;

	public AliasDataLoaderBuilder(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public boolean isVolatile() {
		return true;
	}

	@Override
	public DataLoader<T> build(final ResourceManager<String> resourceManager) {
		return new DataLoader<T>() {

			@Override
			public T load() {
				Resource<T> resource = resourceManager.get(resourceId);
				if (resource == null)
					throw new RuntimeException("Failed to get alias resource " + resourceId);
				return resource.get();
			}

			@Override
			public void unload(T t) {
				// doesn't unload the referenced resource.
			}
		};
	}
}