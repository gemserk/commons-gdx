package com.gemserk.animation4j.timeline;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.animation4j.gdx.converters.CommonGdxConverters;
import com.gemserk.animation4j.gdx.converters.LibgdxConverters;
import com.gemserk.commons.gdx.camera.Camera;

public class TimelineConfigurator {

	private static final String OPACITY_SUFFIX = ".opacity";
	private static final String SCALE_SUFFIX = ".scale";
	private static final String POSITION_SUFFIX = ".position";
	private static final String ROTATION_SUFFIX = ".rotation";

	private String orthographicCameraClassName = OrthographicCamera.class.getName();
	private String spriteClassName = Sprite.class.getName();

	public Map<String, TimelineValue> timelineValues = new LinkedHashMap<String, TimelineValue>();

	private Timeline timeline;

	public Timeline getTimeline() {
		if (timeline == null)
			timeline = new Timeline(new ArrayList<TimelineValue>(timelineValues.values()));
		return timeline;
	}

	public float getTimelineDuration() {
		Timeline timeline = getTimeline();
		ArrayList<TimelineValue> values = timeline.getValues();

		float duration = 0f;

		for (int i = 0; i < values.size(); i++) {
			TimelineValue timelineValue = values.get(i);
			for (int j = 0; j < timelineValue.getKeyFramesCount(); j++) {
				KeyFrame keyFrame = timelineValue.getKeyFrame(j);
				duration = Math.max(duration, keyFrame.getTime());
			}
		}

		return duration;
	}

	public TimelineValue getTimelineValue(String propertyId, String targetClassName, int tweenType) {

		if (targetClassName.equalsIgnoreCase(spriteClassName)) {

			if (!timelineValues.containsKey(propertyId)) {

				switch (tweenType) {
				case 1:
					timelineValues.put(propertyId, new TimelineValueImpl<Sprite>(LibgdxConverters.spritePositionConverter));
					break;
				case 2:
					timelineValues.put(propertyId, new TimelineValueImpl<Sprite>(LibgdxConverters.spriteScaleConverter));
					break;
				case 3:
					timelineValues.put(propertyId, new TimelineValueImpl<Sprite>(LibgdxConverters.spriteRotationConverter));
					break;
				case 4:
					timelineValues.put(propertyId, new TimelineValueImpl<Sprite>(LibgdxConverters.spriteOpacityConverter));
					break;
				default:
					throw new IllegalArgumentException("tweenType not supported yet");
				}

			}

			return timelineValues.get(propertyId);
		} else if (targetClassName.equalsIgnoreCase(orthographicCameraClassName)) {

			if (!timelineValues.containsKey(propertyId)) {

				switch (tweenType) {
				case 1:
					// timelineValues.put(propertyId, new TimelineValueImpl<OrthographicCamera>(LibgdxConverters.orthographicCameraPositionConverter));
					timelineValues.put(propertyId, new TimelineValueImpl<Camera>(CommonGdxConverters.cameraPositionConverter));
					break;
				case 2:
					// timelineValues.put(propertyId, new TimelineValueImpl<OrthographicCamera>(LibgdxConverters.orthographicCameraZoomConverter));
					timelineValues.put(propertyId, new TimelineValueImpl<Camera>(CommonGdxConverters.cameraZoomConverter));
					break;
				default:
					throw new IllegalArgumentException("tweenType not supported yet");
				}

			}
			return timelineValues.get(propertyId);
		}

		throw new IllegalArgumentException("class type " + targetClassName + " not supported yet");
	}

	public String getPropertyIdForType(String objectId, int tweenType) {

		switch (tweenType) {
		case 1:
			return objectId + POSITION_SUFFIX;
		case 2:
			return objectId + SCALE_SUFFIX;
		case 3:
			return objectId + ROTATION_SUFFIX;
		case 4:
			return objectId + OPACITY_SUFFIX;
		default:
			throw new IllegalArgumentException("tweenType not supported yet");
		}

	}

	public void setObject(String objectId, Object object) {
		setObjectForTimelineValue(objectId + POSITION_SUFFIX, object);
		setObjectForTimelineValue(objectId + ROTATION_SUFFIX, object);
		setObjectForTimelineValue(objectId + SCALE_SUFFIX, object);
		setObjectForTimelineValue(objectId + OPACITY_SUFFIX, object);
	}

	private void setObjectForTimelineValue(String propertyId, Object object) {
		TimelineValue timelineValue = timelineValues.get(propertyId);
		if (timelineValue != null)
			timelineValue.setObject(object);
	}

}