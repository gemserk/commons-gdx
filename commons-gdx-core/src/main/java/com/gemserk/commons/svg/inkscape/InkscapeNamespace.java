package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class InkscapeNamespace {

	private static final String namespace = "http://www.inkscape.org/namespaces/inkscape";
	
	public static final String attributeLabel = "label";
	
	public static String getType(Element element) {
		return element.getAttributeNS(namespace, "type");
	}

	public static String getLabel(Element element) {
		return element.getAttributeNS(namespace, "label");
	}

}
