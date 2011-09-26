package com.gemserk.commons.artemis.components;

import com.artemis.Entity;

/**
 * Utility class to retrieve common Components from an Entity, for example, SpatialComponent.
 */
public class Components {

	public static final Class<SpriteComponent> spriteComponentClass = SpriteComponent.class;
	public static final Class<ScriptComponent> scriptComponentClass = ScriptComponent.class;
	public static final Class<PhysicsComponent> physicsComponentClass = PhysicsComponent.class;

	public static final Class<SpatialComponent> spatialComponentClass = SpatialComponent.class;
	public static final Class<PreviousStateSpatialComponent> previousStateSpatialComponentClass = PreviousStateSpatialComponent.class;
	
	public static final Class<CameraComponent> cameraComponentClass = CameraComponent.class;
	public static final Class<PreviousStateCameraComponent> previousStateCameraComponentClass = PreviousStateCameraComponent.class;

	public static SpatialComponent spatialComponent(Entity e) {
		return e.getComponent(spatialComponentClass);
	}

	public static SpriteComponent spriteComponent(Entity e) {
		return e.getComponent(spriteComponentClass);
	}

	public static ScriptComponent scriptComponent(Entity e) {
		return e.getComponent(scriptComponentClass);
	}

	public static PhysicsComponent physicsComponent(Entity e) {
		return e.getComponent(physicsComponentClass);
	}

	public static PreviousStateSpatialComponent getPreviousStateSpatialComponent(Entity e) {
		return e.getComponent(previousStateSpatialComponentClass);
	}
	
	public static CameraComponent getCameraComponent(Entity e) {
		return e.getComponent(cameraComponentClass);
	}

	public static PreviousStateCameraComponent getPreviousStateCameraComponent(Entity e) {
		return e.getComponent(previousStateCameraComponentClass);
	}
	
}
