package com.gemserk.commons.artemis.components;

import com.artemis.ComponentType;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

/**
 * Utility class to retrieve common Components from an Entity, for example, SpatialComponent.
 */
public class Components {

	public static final Class<SpriteComponent> spriteComponentClass = SpriteComponent.class;
	public static final Class<SpatialComponent> spatialComponentClass = SpatialComponent.class;
	public static final Class<ScriptComponent> scriptComponentClass = ScriptComponent.class;
	public static final Class<PhysicsComponent> physicsComponentClass = PhysicsComponent.class;
	public static final Class<CameraComponent> cameraComponentClass = CameraComponent.class;
	public static final Class<PreviousStateSpatialComponent> previousStateSpatialComponentClass = PreviousStateSpatialComponent.class;
	public static final Class<PreviousStateCameraComponent> previousStateCameraComponentClass = PreviousStateCameraComponent.class;
	public static final Class<TextComponent> textComponentClass = TextComponent.class;
	public static final Class<SoundSpawnerComponent> soundSpawnerComponentClass = SoundSpawnerComponent.class;
	public static final Class<MovementComponent> movementComponentClass = MovementComponent.class;
	public static final Class<AnimationComponent> animationComponentClass = AnimationComponent.class;
	public static final Class<PropertiesComponent> propertiesComponentClass = PropertiesComponent.class;
	
	public static final ComponentType spriteComponentType = ComponentTypeManager.getTypeFor(spriteComponentClass);
	public static final ComponentType spatialComponentType = ComponentTypeManager.getTypeFor(spatialComponentClass);
	public static final ComponentType scriptComponentType = ComponentTypeManager.getTypeFor(scriptComponentClass);
	public static final ComponentType physicsComponentType = ComponentTypeManager.getTypeFor(physicsComponentClass);
	public static final ComponentType cameraComponentType = ComponentTypeManager.getTypeFor(cameraComponentClass);

	public static SpatialComponent spatialComponent(Entity e) {
		return spatialComponentClass.cast(e.getComponent(spatialComponentType));
	}

	public static SpriteComponent spriteComponent(Entity e) {
		return spriteComponentClass.cast(e.getComponent(spriteComponentType));
	}

	public static ScriptComponent scriptComponent(Entity e) {
		return scriptComponentClass.cast(e.getComponent(scriptComponentType));
	}

	public static PhysicsComponent physicsComponent(Entity e) {
		return physicsComponentClass.cast(e.getComponent(physicsComponentType));
	}

	public static CameraComponent getCameraComponent(Entity e) {
		return cameraComponentClass.cast(e.getComponent(cameraComponentType));
	}

	public static PreviousStateSpatialComponent getPreviousStateSpatialComponent(Entity e) {
		return e.getComponent(previousStateSpatialComponentClass);
	}

	public static PreviousStateCameraComponent getPreviousStateCameraComponent(Entity e) {
		return e.getComponent(previousStateCameraComponentClass);
	}

	public static TextComponent getTextComponent(Entity e) {
		return e.getComponent(textComponentClass);
	}

	public static SoundSpawnerComponent getSoundSpawnerComponent(Entity e) {
		return e.getComponent(soundSpawnerComponentClass);
	}

	public static MovementComponent getMovementComponent(Entity e) {
		return e.getComponent(movementComponentClass);
	}

	public static AnimationComponent getAnimationComponent(Entity e) {
		return e.getComponent(animationComponentClass);
	}

	public static PropertiesComponent getPropertiesComponent(Entity e) {
		return e.getComponent(propertiesComponentClass);
	}
}
