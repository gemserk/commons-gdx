package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public abstract class SvgInkscapeGroupHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement, Element element) {
		if (!(svgElement instanceof SvgInkscapeGroup))
			return;
		SvgInkscapeGroup svgInkscapeGroup = (SvgInkscapeGroup) svgElement;
		handle(svgParser, svgInkscapeGroup, element);
	}

	@Override
	public void postHandle(SvgParser svgParser, SvgElement svgElement, Element element) {
		if (!(svgElement instanceof SvgInkscapeGroup))
			return;
		SvgInkscapeGroup svgInkscapeGroup = (SvgInkscapeGroup) svgElement;
		postHandle(svgParser, svgInkscapeGroup, element);
	}

	protected void handle(SvgParser svgParser, SvgInkscapeGroup svgInkscapeGroup, Element element) {
		
	}

	protected void postHandle(SvgParser svgParser, SvgInkscapeGroup svgInkscapeGroup, Element element) {
		
	}

}