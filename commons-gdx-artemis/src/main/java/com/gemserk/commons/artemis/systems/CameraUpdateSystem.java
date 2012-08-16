package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.commons.artemis.components.CameraComponent;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.PreviousStateCameraComponent;
import com.gemserk.commons.gdx.camera.Camera;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;
import com.gemserk.commons.gdx.time.TimeStepProvider;
import com.gemserk.commons.gdx.time.TimeStepProviderGlobalImpl;

public class CameraUpdateSystem extends EntityProcessingSystem {
	
	private final TimeStepProvider timeStepProvider;
	
	public CameraUpdateSystem() {
		this(new TimeStepProviderGlobalImpl());
	}

	@SuppressWarnings("unchecked")
	public CameraUpdateSystem(TimeStepProvider timeStepProvider) {
		super(Components.cameraComponentClass);
		this.timeStepProvider = timeStepProvider;
	}
	
	@Override
	protected void process(Entity e) {
		CameraComponent cameraComponent = Components.getCameraComponent(e);

		Libgdx2dCamera libgdx2dCamera = cameraComponent.getLibgdx2dCamera();
		Camera camera = cameraComponent.getCamera();

		float newX = camera.getX();
		float newY = camera.getY();
		float newZoom = camera.getZoom();
		float newAngle = camera.getAngle();

		PreviousStateCameraComponent previousStateCameraComponent = Components.getPreviousStateCameraComponent(e);

		if (previousStateCameraComponent != null) {
			float interpolationAlpha = timeStepProvider.getAlpha();
			Camera previousCamera = previousStateCameraComponent.getCamera();
			newX = FloatInterpolator.interpolate(previousCamera.getX(), camera.getX(), interpolationAlpha);
			newY = FloatInterpolator.interpolate(previousCamera.getY(), camera.getY(), interpolationAlpha);
			newZoom = FloatInterpolator.interpolate(previousCamera.getZoom(), camera.getZoom(), interpolationAlpha);
			newAngle = FloatInterpolator.interpolate(previousCamera.getAngle(), camera.getAngle(), interpolationAlpha);
		}

		libgdx2dCamera.move(newX, newY);
		libgdx2dCamera.zoom(newZoom);
		libgdx2dCamera.rotate(newAngle);
	}

}
