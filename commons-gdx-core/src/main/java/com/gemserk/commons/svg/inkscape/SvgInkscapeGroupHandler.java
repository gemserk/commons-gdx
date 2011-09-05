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

	protected abstract void handle(SvgParser svgParser, SvgInkscapeGroup svgInkscapeGroup, Element element);
	
	@Override
	public void postHandle(SvgParser svgParser, SvgElement svgElement, Element element) {}

}