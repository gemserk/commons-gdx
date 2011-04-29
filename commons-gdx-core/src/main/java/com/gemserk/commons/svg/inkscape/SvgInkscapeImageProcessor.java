package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class SvgInkscapeImageProcessor extends SvgImageProcessor {

	@Override
	protected SvgImage getSvgImage(Element element) {
		SvgImage svgImage = super.getSvgImage(element);

		String label = SvgInkscapeUtils.getLabel(element);

		SvgInkscapeImageImpl svgInkscapeImage = new SvgInkscapeImageImpl(svgImage);
		svgInkscapeImage.setLabel(label);

		return svgInkscapeImage;
	}

}