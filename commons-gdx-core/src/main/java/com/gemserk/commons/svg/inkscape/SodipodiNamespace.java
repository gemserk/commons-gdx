package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class SodipodiNamespace {

	private static final String namespace = SvgInkscapeUtils.SODIPODI;
	
	public static String getType(Element element) {
		return element.getAttributeNS(namespace, "type");
	}

	public static float getCx(Element element) {
		return Float.parseFloat(element.getAttributeNS(namespace, "cx"));
	}

	public static float getCy(Element element) {
		return Float.parseFloat(element.getAttributeNS(namespace, "cy"));
	}

	public static float getRx(Element element) {
		return Float.parseFloat(element.getAttributeNS(namespace, "rx"));
	}

	public static float getRy(Element element) {
		return Float.parseFloat(element.getAttributeNS(namespace, "ry"));
	}

}
