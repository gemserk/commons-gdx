package com.gemserk.commons.artemis.render;

import com.gemserk.commons.artemis.systems.RenderLayer;
import com.gemserk.commons.artemis.systems.Renderable;

public class MockRenderLayer implements RenderLayer {

	@Override
	public void init() {
	}

	@Override
	public void dispose() {
		
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

	@Override
	public boolean belongs(Renderable renderable) {
		return false;
	}

	@Override
	public void add(Renderable renderable) {
		
	}

	@Override
	public void remove(Renderable renderable) {
		
	}

}
