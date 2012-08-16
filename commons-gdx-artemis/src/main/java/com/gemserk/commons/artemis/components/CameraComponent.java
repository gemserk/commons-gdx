package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.gemserk.commons.gdx.camera.Camera;
import com.gemserk.commons.gdx.camera.Libgdx2dCamera;

public class CameraComponent extends Component {
	
	public static final int type = ComponentTypeManager.getTypeFor(CameraComponent.class).getId();

	public static CameraComponent get(Entity e) {
		return (CameraComponent) e.getComponent(type);
	}
	
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
