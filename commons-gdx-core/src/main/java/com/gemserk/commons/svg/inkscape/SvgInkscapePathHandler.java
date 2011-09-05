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

	@Override
	public void postHandle(SvgParser svgParser, SvgElement svgElement, Element element) {
		if (!(svgElement instanceof SvgInkscapePath))
			return;
		SvgInkscapePath svgPath = (SvgInkscapePath) svgElement;
		postHandle(svgParser, svgPath, element);
	}

	protected void handle(SvgParser svgParser, SvgInkscapePath svgPath, Element element) {
	}

	protected void postHandle(SvgParser svgParser, SvgInkscapePath svgPath, Element element) {
	}

}