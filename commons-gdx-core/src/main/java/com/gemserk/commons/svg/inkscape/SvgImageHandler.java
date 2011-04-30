package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;


public abstract class SvgImageHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement, Element element) {
		if (!(svgElement instanceof SvgImage))
			return;
		SvgImage svgImage = (SvgImage) svgElement;
		handle(svgParser, svgImage, element);
	}

	protected abstract void handle(SvgParser svgParser, SvgImage svgImage, Element element);

}