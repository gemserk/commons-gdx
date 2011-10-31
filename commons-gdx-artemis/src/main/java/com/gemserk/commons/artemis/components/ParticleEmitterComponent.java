package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;

public class ParticleEmitterComponent extends Component {

	public ParticleEmitter particleEmitter;

	public ParticleEmitterComponent(ParticleEmitter particleEmitter) {
		this.particleEmitter = particleEmitter;
	}

}