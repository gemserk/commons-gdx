package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;

public class GemserkNamespace {
	
	public static final String namespace = "gemserk";
	
	public static final String attributeAbsoluteTransform = "absoluteTransform";
	
	public static Matrix3f getAbsoluteTransform(Element element) {
		String transformAttributeValue = element.getAttributeNS(namespace, attributeAbsoluteTransform);
		Matrix3f m = new Matrix3f();
		m.setIdentity();
		return SvgInkscapeUtils.parseTransformAttribute(transformAttributeValue, m);
	}
	

}
