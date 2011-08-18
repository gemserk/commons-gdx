package com.gemserk.commons.artemis.render;

import com.artemis.Entity;
import com.gemserk.commons.artemis.systems.RenderLayer;

public class MockRenderLayer implements RenderLayer {

	@Override
	public void init() {
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public boolean belongs(Entity entity) {
		return false;
	}

	@Override
	public void add(Entity entity) {
		
	}

	@Override
	public void remove(Entity entity) {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		
	}

}
