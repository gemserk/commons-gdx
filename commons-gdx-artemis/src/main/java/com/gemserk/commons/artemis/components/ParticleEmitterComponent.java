package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;

public class ParticleEmitterComponent extends Component {
	
	public static final int type = ComponentTypeManager.getTypeFor(ParticleEmitterComponent.class).getId();

	public static ParticleEmitterComponent get(Entity e) {
		return (ParticleEmitterComponent) e.getComponent(type);
	}

	public ParticleEmitter particleEmitter;

	public ParticleEmitterComponent(ParticleEmitter particleEmitter) {
		this.particleEmitter = particleEmitter;
	}

}