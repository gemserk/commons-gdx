package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class SvgInkscapeGroupProcessor extends SvgGroupProcessor {

	@Override
	protected SvgElement getSvgElement(Element element) {
		return SvgInkscapeConvertUtils.getSvgInkscapeGroup(element);
	}

}