package com.gemserk.resources;

import java.util.ArrayList;

import com.gemserk.resources.dataloaders.DataLoader;

public class CustomResourceManager<K> extends ResourceManagerImpl<K> {

	private ArrayList<K> registeredResources = new ArrayList<K>();

	public ArrayList<K> getRegisteredResources() {
		return registeredResources;
	}

	@SuppressWarnings("rawtypes")
	public void add(K id, DataLoader dataLoader) {
		super.add(id, dataLoader);
		registeredResources.add(id);
	}
	
	@Override
	public void unloadAll() {
		super.unloadAll();
		registeredResources.clear();
	}

}
