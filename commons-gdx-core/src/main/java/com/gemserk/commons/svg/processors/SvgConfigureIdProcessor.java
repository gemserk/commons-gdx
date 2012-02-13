package com.gemserk.commons.svg.processors;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.gemserk.commons.svg.inkscape.SvgNamespace;

public class SvgConfigureIdProcessor extends SvgElementProcessor {

	@Override
	public boolean processElement(Element element) {
		Attr attributeNode = element.getAttributeNode(SvgNamespace.attributeId);
		if (attributeNode == null) {
			System.out.println("attr is null " + SvgNamespace.getId(element));
			return true;
		}
		// element.setIdAttribute(SvgNamespace.attributeId, true);
		element.setIdAttributeNode(attributeNode, true);
		// element.setIdAttributeNS("svg", "id", true);
		// element.getAttributeNode(name)
		System.out.println(attributeNode.getValue());
		return true;
	}

}