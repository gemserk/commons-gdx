package com.gemserk.commons.svg.inkscape;


public abstract class SvgImageHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement) {
		if (!(svgElement instanceof SvgImage))
			return;
		SvgImage svgImage = (SvgImage) svgElement;
		handle(svgParser, svgImage);
	}

	protected abstract void handle(SvgParser svgParser, SvgImage svgImage);

}