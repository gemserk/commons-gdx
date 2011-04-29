package com.gemserk.commons.svg.inkscape;


public abstract class SvgInkscapeGroupHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement) {
		if (!(svgElement instanceof SvgInkscapeGroup))
			return;
		SvgInkscapeGroup svgInkscapeGroup = (SvgInkscapeGroup) svgElement;
		handle(svgParser, svgInkscapeGroup);
	}

	protected abstract void handle(SvgParser svgParser, SvgInkscapeGroup svgInkscapeGroup);

}