package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.ScaledNumericValue;

public class ParticleEmitterUtils {

	public static void scaleValue(ScaledNumericValue value, float s) {
		value.setHigh(value.getHighMin() * s, value.getHighMax() * s);
		value.setLow(value.getLowMin() * s, value.getLowMax() * s);

		if (!value.isRelative()) {
			float[] scaling = value.getScaling();
			for (int i = 0; i < scaling.length; i++)
				scaling[i] *= s;
		}
	}

	public static void scaleEmitter(ParticleEmitter emitter, float s) {
		scaleValue(emitter.getScale(), s);
		scaleValue(emitter.getVelocity(), s);
		scaleValue(emitter.getGravity(), s);
		scaleValue(emitter.getWind(), s);
	}

}
