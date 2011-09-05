package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class SvgInkscapePathProcessor extends SvgElementProcessor {

	@Override
	protected SvgElement getSvgElement(Element element) {
		return SvgConvertUtils.getSvgPath(element);
	}

	@Override
	public boolean handles(Element element) {
		return element.getNodeName().equals("path");
	}

}