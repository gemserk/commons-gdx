package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;

public class GemserkNamespace {

	public static final String namespace = "gemserk";

	public static final String attributeAbsoluteTransform = "absoluteTransform";

	public static boolean hasAbsoluteTransformAttribute(Element element) {
		return element.getAttributeNode(attributeAbsoluteTransform) != null;
	}

	/**
	 * Returns a new Matrix3f with the values of the absoluteTransform from the element.
	 */
	public static Matrix3f getAbsoluteTransform(Element element) {
		Matrix3f matrix = new Matrix3f();
		matrix.setIdentity();
		return getAbsoluteTransform(element, matrix);
	}

	/**
	 * Stores the values of the element absoluteTransform attribute in the specified Matrix3f.
	 */
	public static Matrix3f getAbsoluteTransform(Element element, Matrix3f matrix) {
		String transform = element.getAttributeNS(namespace, attributeAbsoluteTransform);
		return SvgTransformUtils.parseTransform(transform, matrix);
	}

	public static void setAbsoluteTransform(Element element, Matrix3f absoluteTransform) {
		element.setAttributeNS(namespace, GemserkNamespace.attributeAbsoluteTransform, SvgTransformUtils.serializeTransform(absoluteTransform));
	}

}
