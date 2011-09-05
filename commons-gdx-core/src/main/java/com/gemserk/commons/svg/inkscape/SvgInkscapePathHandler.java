package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public abstract class SvgInkscapePathHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement, Element element) {
		if (!(svgElement instanceof SvgPath))
			return;
		SvgPath svgPath = (SvgPath) svgElement;
		handle(svgParser, svgPath, element);
	}

	@Override
	public void postHandle(SvgParser svgParser, SvgElement svgElement, Element element) {
		if (!(svgElement instanceof SvgPath))
			return;
		SvgPath svgPath = (SvgPath) svgElement;
		postHandle(svgParser, svgPath, element);
	}

	protected void handle(SvgParser svgParser, SvgPath svgPath, Element element) {
	}

	protected void postHandle(SvgParser svgParser, SvgPath svgPath, Element element) {
	}

}