package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.gemserk.vecmath.Matrix3f;

public class GemserkNamespace {

	public static final String namespace = "gemserk";

	public static final String attributeAbsoluteTransform = "absoluteTransform";
	public static final String attributeCenterX = "cx";
	public static final String attributeCenterY = "cy";
	public static final String attributeAngle = "angle";

	public static boolean hasAbsoluteTransformAttribute(Element element) {
		return element.getAttributeNode(attributeAbsoluteTransform) != null;
	}

	public static boolean hasAttribute(Element element, String attribute) {
		return element.getAttributeNodeNS(namespace, attribute) != null;
	}
	
	/**
	 * Returns a new Matrix3f with the values of the absoluteTransform from the element.
	 */
	public static Matrix3f getAbsoluteTransform(Element element) {
		return getAbsoluteTransform(element, new Matrix3f());
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
	
	public static float getCenterX(Element element) {
		return Float.parseFloat(element.getAttributeNS(namespace, attributeCenterX));
	}

	public static float getCenterX(Node node) {
		return Float.parseFloat(node.getAttributes().getNamedItemNS(namespace, attributeCenterX).getNodeValue());
	}
	
	public static float getCenterY(Element element) {
		return Float.parseFloat(element.getAttributeNS(namespace, attributeCenterY));
	}
	
	public static float getCenterY(Node node) {
		return Float.parseFloat(node.getAttributes().getNamedItemNS(namespace, attributeCenterY).getNodeValue());
	}

	public static void setCenter(Element element, float cx, float cy) {
		element.setAttributeNS(namespace, attributeCenterX, Float.toString(cx));
		element.setAttributeNS(namespace, attributeCenterY, Float.toString(cy));
	}
	
	public static float getAngle(Element element) {
		return Float.parseFloat(element.getAttributeNS(namespace, attributeAngle));
	}
	
	public static void setAngle(Element element, float angle) {
		element.setAttributeNS(namespace, attributeAngle, Float.toString(angle));
	}

}
