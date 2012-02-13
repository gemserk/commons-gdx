package com.gemserk.commons.svg.processors;

import org.w3c.dom.Element;

import com.gemserk.commons.svg.inkscape.SvgNamespace;

public class SvgConfigureIdProcessor extends SvgElementProcessor {

	@Override
	public boolean processElement(Element element) {
		if ("".equals(element.getAttribute(SvgNamespace.attributeId)))
			return true;
		element.setIdAttribute(SvgNamespace.attributeId, true);
		return true;
	}

}