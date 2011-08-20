package com.gemserk.commons.gdx.box2d;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;

public class Box2DCustomDebugRenderer {

	private final Libgdx2dCamera camera;

	private final World world;

	private Box2DDebugRenderer box2dDebugRenderer;

	public Box2DCustomDebugRenderer(Libgdx2dCamera camera, World world) {
		this.camera = camera;
		this.world = world;
		this.box2dDebugRenderer = new Box2DDebugRenderer();
	}

	public void render() {
		camera.apply();
		box2dDebugRenderer.render(world);
		// camera.pop();
	}

}