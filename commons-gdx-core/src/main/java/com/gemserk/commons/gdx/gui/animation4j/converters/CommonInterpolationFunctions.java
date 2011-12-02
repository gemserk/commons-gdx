package com.gemserk.commons.gdx.gui.animation4j.converters;

import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;

/**
 * Common InterpolationFunction arrays to be used with Transitions and Animations.
 */
public class CommonInterpolationFunctions {

	public static final InterpolationFunction[] easeInEaseIn = { InterpolationFunctions.easeIn(), InterpolationFunctions.easeIn() };

	public static final InterpolationFunction[] easeOutEaseOut = { InterpolationFunctions.easeOut(), InterpolationFunctions.easeOut() };

}
