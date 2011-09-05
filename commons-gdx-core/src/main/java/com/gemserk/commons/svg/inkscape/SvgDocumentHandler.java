package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;


public abstract class SvgDocumentHandler implements SvgElementHandler {

	@Override
	public void handle(SvgParser svgParser, SvgElement svgElement, Element element) {
		if (!(svgElement instanceof SvgDocument))
			return;
		SvgDocument svgDocument = (SvgDocument) svgElement;
		handle(svgParser, svgDocument, element);
	}

	protected abstract void handle(SvgParser svgParser, SvgDocument svgDocument, Element element);
	
	@Override
	public void postHandle(SvgParser svgParser, SvgElement svgElement, Element element) {}

}