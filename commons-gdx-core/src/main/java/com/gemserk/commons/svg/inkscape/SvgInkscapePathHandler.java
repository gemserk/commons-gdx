package com.gemserk.commons.svg.inkscape;


public abstract class SvgInkscapePathHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement) {
		if (!(svgElement instanceof SvgInkscapePath))
			return;
		SvgInkscapePath svgPath = (SvgInkscapePath) svgElement;
		handle(svgParser, svgPath);
	}

	protected abstract void handle(SvgParser svgParser, SvgInkscapePath svgPath);

}