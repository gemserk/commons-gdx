package com.gemserk.commons.artemis.components;

import com.artemis.ComponentType;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;

/**
 * Utility class to retrieve common Components from an Entity, for example, SpatialComponent.
 */
public class Components {

	public static final Class<SpriteComponent> spriteComponentClass = SpriteComponent.class;
	public static final ComponentType spriteComponentType = ComponentTypeManager.getTypeFor(spriteComponentClass);

	public static final Class<RenderableComponent> renderableComponentClass = RenderableComponent.class;
	public static final ComponentType renderableComponentType = ComponentTypeManager.getTypeFor(renderableComponentClass);

	public static final Class<SpatialComponent> spatialComponentClass = SpatialComponent.class;
	public static final ComponentType spatialComponentType = ComponentTypeManager.getTypeFor(spatialComponentClass);

	public static final Class<ScriptComponent> scriptComponentClass = ScriptComponent.class;
	public static final ComponentType scriptComponentType = ComponentTypeManager.getTypeFor(scriptComponentClass);

	public static final Class<PhysicsComponent> physicsComponentClass = PhysicsComponent.class;
	public static final ComponentType physicsComponentType = ComponentTypeManager.getTypeFor(physicsComponentClass);

	public static final Class<CameraComponent> cameraComponentClass = CameraComponent.class;
	public static final ComponentType cameraComponentType = ComponentTypeManager.getTypeFor(cameraComponentClass);

	public static final Class<PreviousStateCameraComponent> previousStateCameraComponentClass = PreviousStateCameraComponent.class;
	public static final ComponentType previousStateCameraComponentType = ComponentTypeManager.getTypeFor(previousStateCameraComponentClass);

	public static final Class<TextComponent> textComponentClass = TextComponent.class;
	public static final ComponentType textComponentType = ComponentTypeManager.getTypeFor(textComponentClass);

	public static final Class<PreviousStateSpatialComponent> previousStateSpatialComponentClass = PreviousStateSpatialComponent.class;
	public static final ComponentType previousStateSpatialComponentType = ComponentTypeManager.getTypeFor(previousStateSpatialComponentClass);

	public static final Class<SoundSpawnerComponent> soundSpawnerComponentClass = SoundSpawnerComponent.class;
	public static final ComponentType soundSpawnerComponentType = ComponentTypeManager.getTypeFor(soundSpawnerComponentClass);

	public static final Class<MovementComponent> movementComponentClass = MovementComponent.class;
	public static final ComponentType movementComponentType = ComponentTypeManager.getTypeFor(movementComponentClass);

	public static final Class<AnimationComponent> animationComponentClass = AnimationComponent.class;
	public static final ComponentType animationComponentType = ComponentTypeManager.getTypeFor(animationComponentClass);

	public static final Class<PropertiesComponent> propertiesComponentClass = PropertiesComponent.class;
	public static final ComponentType propertiesComponentType = ComponentTypeManager.getTypeFor(propertiesComponentClass);

	public static SpatialComponent spatialComponent(Entity e) {
		return spatialComponentClass.cast(e.getComponent(spatialComponentType));
	}
	
	public static RenderableComponent getRenderableComponent(Entity e) {
		return renderableComponentClass.cast(e.getComponent(renderableComponentType));
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

	public static PreviousStateCameraComponent getPreviousStateCameraComponent(Entity e) {
		return previousStateCameraComponentClass.cast(e.getComponent(previousStateCameraComponentType));
	}
	
	public static TextComponent getTextComponent(Entity e) {
		return textComponentClass.cast(e.getComponent(textComponentType));
	}

	public static PreviousStateSpatialComponent getPreviousStateSpatialComponent(Entity e) {
		return previousStateSpatialComponentClass.cast(e.getComponent(previousStateSpatialComponentType));
	}

	public static SoundSpawnerComponent getSoundSpawnerComponent(Entity e) {
		return soundSpawnerComponentClass.cast(e.getComponent(soundSpawnerComponentClass));
	}

	public static MovementComponent getMovementComponent(Entity e) {
		return movementComponentClass.cast(e.getComponent(movementComponentType));
	}

	public static AnimationComponent getAnimationComponent(Entity e) {
		return animationComponentClass.cast(e.getComponent(animationComponentType));
	}

	public static PropertiesComponent getPropertiesComponent(Entity e) {
		return propertiesComponentClass.cast(e.getComponent(propertiesComponentType));
	}
	
}
