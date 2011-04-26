package com.gemserk.commons.gdx.box2d;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.gemserk.commons.gdx.Libgdx2dCameraTransformImpl;

public class Box2DCustomDebugRenderer extends Box2DDebugRenderer {
	
	private final Libgdx2dCameraTransformImpl camera;

	public Box2DCustomDebugRenderer(Libgdx2dCameraTransformImpl camera) {
		this.camera = camera;
	}
	
	@Override
	public void render(com.badlogic.gdx.physics.box2d.World world) {
		camera.push();
		super.render(world);
		camera.pop();
	}
}