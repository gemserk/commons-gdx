package com.gemserk.commons.gdx.resources.dataloaders;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.timeline.KeyFrame;
import com.gemserk.animation4j.timeline.TimelineConfigurator;
import com.gemserk.animation4j.timeline.TimelineValue;
import com.gemserk.commons.utils.FileHelper;
import com.gemserk.resources.dataloaders.DataLoader;

public class TimelineConfiguratorDataLoader extends DataLoader<TimelineConfigurator> {

	@SuppressWarnings("serial")
	private static Map<String, InterpolationFunction> tweenEquationMap = new HashMap<String, InterpolationFunction>() {
		{
			put("Linear.INOUT", InterpolationFunctions.linear());
			put("Cubic.IN", InterpolationFunctions.cubicEaseIn);
			put("Cubic.OUT", InterpolationFunctions.cubicEaseOut);
			put("Cubic.INOUT", InterpolationFunctions.cubicEaseInOut);
			put("Quad.IN", InterpolationFunctions.quadraticEaseIn());
			put("Quad.OUT", InterpolationFunctions.quadraticEaseOut());
			// put("Quad.INOUT", InterpolationFunctions.linear);
		}
	};

	FileHandle fileHandle;

	public TimelineConfiguratorDataLoader(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
	}

	private InterpolationFunction getInterpolationFunction(String tweenEquation) {
		if (!tweenEquationMap.containsKey(tweenEquation))
			throw new IllegalArgumentException("Tween equation " + tweenEquation + " not supported yet.");
		return tweenEquationMap.get(tweenEquation);
	}

	@Override
	public TimelineConfigurator load() {
		TimelineConfigurator timelineDefinition = new TimelineConfigurator();

		String timelineFileContents = FileHelper.read(fileHandle.read());

		String[] lines = timelineFileContents.split("\n");

		String currentPropertyId = null;

		for (String line : lines) {

			String[] parts = line.split(";");

			if (parts.length < 7)
				continue;

			String objectId = parts[0];
			String targetClassName = parts[1];
			int tweenType = Integer.parseInt(parts[2]);

			String propertyId = timelineDefinition.getPropertyIdForType(objectId, tweenType);
			TimelineValue<?> timelineValue = timelineDefinition.getTimelineValue(propertyId, targetClassName, tweenType);

			if (!propertyId.equals(currentPropertyId))
				currentPropertyId = propertyId;

			float delay = Integer.parseInt(parts[3]) * 0.001f;
			float duration = Integer.parseInt(parts[4]) * 0.001f;

			float[] targets = new float[parts.length - 6];
			InterpolationFunction[] functions = new InterpolationFunction[targets.length];

			for (int i = 0; i < targets.length; i++) {
				targets[i] = Float.parseFloat(parts[i + 6]);
				functions[i] = getInterpolationFunction(parts[5]);
			}

			timelineValue.addKeyFrame(new KeyFrame(delay + duration, targets, functions));
		}

		return timelineDefinition;
	}

}
