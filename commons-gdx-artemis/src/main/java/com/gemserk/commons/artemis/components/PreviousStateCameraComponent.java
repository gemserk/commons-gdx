package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.gdx.camera.Camera;

public class PreviousStateCameraComponent extends Component {

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
