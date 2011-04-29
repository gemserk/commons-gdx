package com.gemserk.commons.svg.inkscape;


public abstract class SvgDocumentHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement) {
		if (!(svgElement instanceof SvgDocument))
			return;
		SvgDocument svgDocument = (SvgDocument) svgElement;
		handle(svgParser, svgDocument);
	}

	protected abstract void handle(SvgParser svgParser, SvgDocument svgDocument);

}