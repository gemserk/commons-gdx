package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.gemserk.commons.gdx.camera.Camera;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;

public class CameraComponent extends Component {
	
	private Libgdx2dCamera libgdx2dCamera;
	private Camera camera;
	
	public Libgdx2dCamera getLibgdx2dCamera() {
		return libgdx2dCamera;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setLibgdx2dCamera(Libgdx2dCamera libgdx2dCamera) {
		this.libgdx2dCamera = libgdx2dCamera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public CameraComponent(Libgdx2dCamera libgdx2dCamera, Camera camera) {
		this.libgdx2dCamera = libgdx2dCamera;
		this.camera = camera;
	}

}
