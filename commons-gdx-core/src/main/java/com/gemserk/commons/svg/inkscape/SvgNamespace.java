package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;

public class SvgNamespace {

	public static final String svgElement = "svg";
	public static final String imageElement = "image";
	public static final String useElement = "use";
	public static final String groupElement = "g";
	public static final String pathElement = "path";

	public static final String attributeId = "id";

	public static final String attributeX = "x";
	public static final String attributeY = "y";
	
	public static final String attributePathData = "d";

	public static final String attributeWidth = "width";
	public static final String attributeHeight = "height";
	public static final String attributeTransform = "transform";
	public static final String attributeXlinkHref = "xlink:href";
	
	public static final String attributeStyle = "style";

	public static boolean isSvg(Element element) {
		return isType(svgElement, element);
	}

	public static boolean isImage(Element element) {
		return isType(imageElement, element);
	}

	public static boolean isUse(Element element) {
		return isType(useElement, element);
	}
	
	public static boolean isGroup(Element element) {
		return isType(groupElement, element);
	}

	public static boolean isPath(Element element) {
		return isType(pathElement, element);
	}
	
	public static boolean isType(String type, Element element) {
		if (element == null)
			return false;
		return type.equalsIgnoreCase(element.getNodeName());
	}

	public static boolean hasAttribute(Element element, String attribute) {
		return element.getAttributeNode(attribute) != null;
	}

	public static String getId(Element element) {
		return element.getAttribute(attributeId);
	}

	public static float getX(Element element) {
		return Float.parseFloat(element.getAttribute(attributeX));
	}

	public static float getY(Element element) {
		return Float.parseFloat(element.getAttribute(attributeY));
	}
	
	public static String getPathData(Element element) {
		return element.getAttribute(attributePathData);
	}
	
	public static String getStyle(Element element) {
		return element.getAttribute(attributeStyle);
	}

	public static void setX(Element element, float x) {
		element.setAttribute(attributeX, Float.toString(x));
	}

	public static void setY(Element element, float y) {
		element.setAttribute(attributeY, Float.toString(y));
	}

	public static float getWidth(Element element) {
		return Float.parseFloat(element.getAttribute(attributeWidth));
	}

	public static float getHeight(Element element) {
		return Float.parseFloat(element.getAttribute(attributeHeight));
	}

	public static Matrix3f getTransform(Element element) {
		return getTransform(element, new Matrix3f());
	}
	
	public static Matrix3f getTransform(Element element, Matrix3f matrix) {
		String transformAttribute = element.getAttribute(attributeTransform);
		return SvgTransformUtils.parseTransform(transformAttribute, matrix);
	}
	
	public static void setTransform(Element element, Matrix3f matrix) {
		element.setAttribute(attributeTransform, SvgTransformUtils.serializeTransform(matrix));
	}

	public static String getXlinkHref(Element element) {
		return element.getAttribute(attributeXlinkHref);
	}

}
