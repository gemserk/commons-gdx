package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.gemserk.commons.gdx.camera.Camera;

public class PreviousStateCameraComponent extends Component {
	
	public static final int type = ComponentTypeManager.getTypeFor(PreviousStateCameraComponent.class).getId();

	public static PreviousStateCameraComponent get(Entity e) {
		return (PreviousStateCameraComponent) e.getComponent(type);
	}

	private Camera camera;

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public PreviousStateCameraComponent(Camera camera) {
		this.camera = camera;
	}

}
