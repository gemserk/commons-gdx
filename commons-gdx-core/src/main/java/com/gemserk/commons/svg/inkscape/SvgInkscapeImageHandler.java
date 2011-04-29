package com.gemserk.commons.svg.inkscape;


public abstract class SvgInkscapeImageHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement) {
		if (!(svgElement instanceof SvgInkscapeImage))
			return;
		SvgInkscapeImage svgImage = (SvgInkscapeImage) svgElement;
		handle(svgParser, svgImage);
	}

	protected abstract void handle(SvgParser svgParser, SvgInkscapeImage svgImage);

}