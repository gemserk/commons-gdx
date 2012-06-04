package com.gemserk.commons.gdx.resources;

import com.gemserk.resources.Resource;
import com.gemserk.resources.ResourceManager;

public class AliasResourceBuilder<T> implements ResourceBuilder<T> {

	ResourceManager<String> resourceManager;
	private final String resourceId;
	
	public AliasResourceBuilder(ResourceManager<String> resourceManager, String resourceId) {
		this.resourceManager = resourceManager;
		this.resourceId = resourceId;
	}
	
	@Override
	public boolean isVolatile() {
		return true;
	}

	@Override
	public T build() {
		Resource<T> resource = resourceManager.get(resourceId);
		return resource.get();
	}
}
