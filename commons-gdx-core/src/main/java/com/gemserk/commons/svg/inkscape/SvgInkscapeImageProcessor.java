package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class SvgInkscapeImageProcessor extends SvgImageProcessor {
	
	@Override
	protected SvgElement getSvgElement(Element element) {
		return getSvgInkscapeImage(element);
	}

	protected SvgInkscapeImage getSvgInkscapeImage(Element element) {
		SvgImage svgImage = super.getSvgImage(element);

		String label = SvgInkscapeUtils.getLabel(element);

		SvgInkscapeImageImpl svgInkscapeImage = new SvgInkscapeImageImpl(svgImage);
		svgInkscapeImage.setLabel(label);

		return svgInkscapeImage;
	}

}