package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class SvgInkscapeImageProcessor extends SvgImageProcessor {

	@Override
	protected SvgElement getSvgElement(Element element) {
		return SvgInkscapeConvertUtils.getSvgInkscapeImage(element);
	}

}