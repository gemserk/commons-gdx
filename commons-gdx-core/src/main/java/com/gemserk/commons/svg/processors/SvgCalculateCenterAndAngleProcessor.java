package com.gemserk.commons.svg.processors;

import org.w3c.dom.Element;

import com.gemserk.commons.svg.inkscape.GemserkNamespace;
import com.gemserk.commons.svg.inkscape.SvgNamespace;
import com.gemserk.vecmath.Matrix3f;
import com.gemserk.vecmath.Vector3f;

public class SvgCalculateCenterAndAngleProcessor extends SvgElementProcessor {
	Matrix3f absoluteTransform = new Matrix3f();
	Vector3f position = new Vector3f();
	Vector3f center = new Vector3f();
	Vector3f direction = new Vector3f();

	@Override
	public boolean processElement(Element element) {
		
		if (!SvgNamespace.isImage(element))
			return true;

		if (!SvgNamespace.hasAttribute(element, SvgNamespace.attributeX))
			return true;

		if (!SvgNamespace.hasAttribute(element, SvgNamespace.attributeY))
			return true;

		if (!SvgNamespace.hasAttribute(element, SvgNamespace.attributeWidth))
			return true;

		if (!SvgNamespace.hasAttribute(element, SvgNamespace.attributeHeight))
			return true;

		GemserkNamespace.getAbsoluteTransform(element, absoluteTransform);

		float x = SvgNamespace.getX(element);
		float y = SvgNamespace.getY(element);

		float width = SvgNamespace.getWidth(element);
		float height = SvgNamespace.getHeight(element);

		position.set(x, y, 1f);
		center.set(x + width * 0.5f, y + height * 0.5f, 1f);
		direction.set(1f, 0f, 0f);

		absoluteTransform.transform(position);
		absoluteTransform.transform(center);
		absoluteTransform.transform(direction);

		float angle = 360f - (float) (Math.atan2(direction.y, direction.x) * 180 / Math.PI);

		if (angle >= 360f)
			angle -= 360f;

		GemserkNamespace.setCenter(element, center.x, center.y);
		GemserkNamespace.setAngle(element, angle);

		return true;
	}
}