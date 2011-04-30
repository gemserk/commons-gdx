package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;


public abstract class SvgInkscapePathHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement, Element element) {
		if (!(svgElement instanceof SvgInkscapePath))
			return;
		SvgInkscapePath svgPath = (SvgInkscapePath) svgElement;
		handle(svgParser, svgPath, element);
	}

	protected abstract void handle(SvgParser svgParser, SvgInkscapePath svgPath, Element element);

}