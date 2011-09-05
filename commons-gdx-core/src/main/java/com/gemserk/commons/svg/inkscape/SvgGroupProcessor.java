package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class SvgGroupProcessor extends SvgElementProcessor {

	@Override
	protected SvgElement getSvgElement(Element element) {
		return SvgConvertUtils.getSvgGroup(element);
	}

	@Override
	public boolean handles(Element element) {
		return element.getNodeName().equals("g");
	}

}