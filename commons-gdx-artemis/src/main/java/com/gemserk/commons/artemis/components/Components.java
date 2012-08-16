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

	public static final Class<ParticleEmitterComponent> particleEmitterComponentClass = ParticleEmitterComponent.class;
	public static final ComponentType particleEmitterComponentType = ComponentTypeManager.getTypeFor(particleEmitterComponentClass);

	public static SpatialComponent getSpatialComponent(Entity e) {
		return (SpatialComponent) e.getComponent(spatialComponentType.getId());
	}
	
	public static RenderableComponent getRenderableComponent(Entity e) {
		return (RenderableComponent) e.getComponent(renderableComponentType.getId());
	}

	public static SpriteComponent getSpriteComponent(Entity e) {
		return (SpriteComponent) e.getComponent(spriteComponentType.getId());
	}

	public static ScriptComponent getScriptComponent(Entity e) {
		return (ScriptComponent) e.getComponent(scriptComponentType.getId());
	}

	public static PhysicsComponent getPhysicsComponent(Entity e) {
		return (PhysicsComponent) e.getComponent(physicsComponentType.getId());
	}

	public static CameraComponent getCameraComponent(Entity e) {
		return (CameraComponent) e.getComponent(cameraComponentType.getId());
	}

	public static PreviousStateCameraComponent getPreviousStateCameraComponent(Entity e) {
		return (PreviousStateCameraComponent) e.getComponent(previousStateCameraComponentType.getId());
	}
	
	public static TextComponent getTextComponent(Entity e) {
		return (TextComponent) e.getComponent(textComponentType.getId());
	}

	public static PreviousStateSpatialComponent getPreviousStateSpatialComponent(Entity e) {
		return (PreviousStateSpatialComponent) e.getComponent(previousStateSpatialComponentType.getId());
	}

	public static SoundSpawnerComponent getSoundSpawnerComponent(Entity e) {
		return (SoundSpawnerComponent) e.getComponent(soundSpawnerComponentType.getId());
	}

	public static MovementComponent getMovementComponent(Entity e) {
		return (MovementComponent) e.getComponent(movementComponentType.getId());
	}

	public static AnimationComponent getAnimationComponent(Entity e) {
		return (AnimationComponent) e.getComponent(animationComponentType.getId());
	}

	public static PropertiesComponent getPropertiesComponent(Entity e) {
		return (PropertiesComponent) e.getComponent(propertiesComponentType.getId());
	}
	
	public static ParticleEmitterComponent getParticleEmitterComponent(Entity e) {
		return (ParticleEmitterComponent) e.getComponent(particleEmitterComponentType.getId());
	}
	
	public static final Class<FrustumCullingComponent> frustumCullingComponentClass = FrustumCullingComponent.class;
	public static final ComponentType frustumCullingComponentType = ComponentTypeManager.getTypeFor(frustumCullingComponentClass);

	public static FrustumCullingComponent getFrustumCullingComponent(Entity e) {
		return (FrustumCullingComponent) e.getComponent(frustumCullingComponentType.getId());
	}
	
	public static final Class<GroupComponent> groupComponentClass = GroupComponent.class;
	public static final ComponentType groupComponentType = ComponentTypeManager.getTypeFor(groupComponentClass);
	
	public static GroupComponent getGroupComponent(Entity e) {
		return (GroupComponent) e.getComponent(groupComponentType.getId());
	}
	
	public static final Class<LinearVelocityLimitComponent> linearVelocityComponentClass = LinearVelocityLimitComponent.class;
	public static final ComponentType linearVelocityComponentType = ComponentTypeManager.getTypeFor(linearVelocityComponentClass);
	
	public static LinearVelocityLimitComponent getLinearVelocityComponent(Entity e) {
		return (LinearVelocityLimitComponent) e.getComponent(linearVelocityComponentType.getId());
	}
}
