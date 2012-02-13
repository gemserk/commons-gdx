package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;

public class SvgNamespace {

	public static final String svgElement = "svg";
	public static final String imageElement = "image";

	public static final String attributeId = "id";
	public static final String attributeWidth = "width";
	public static final String attributeHeight = "height";
	public static final String attributeTransform = "transform";

	public static boolean isSvg(Element element) {
		return isType(svgElement, element);
	}
	
	public static boolean isImage(Element element) {
		return isType(imageElement, element);
	}

	public static boolean isType(String type, Element element) {
		if (element == null)
			return false;
		return type.equalsIgnoreCase(element.getNodeName());
	}

	public static String getId(Element element) {
		return element.getAttribute(attributeId);
	}

	public static float getWidth(Element element) {
		return Float.parseFloat(element.getAttribute(attributeWidth));
	}

	public static float getHeight(Element element) {
		return Float.parseFloat(element.getAttribute(attributeHeight));
	}
	
	public static Matrix3f getTransform(Element element) {
		String transformAttribute = element.getAttribute(attributeTransform);
		Matrix3f m = new Matrix3f();
		m.setIdentity();
		return SvgInkscapeUtils.parseTransformAttribute(transformAttribute, m);
	}

}
