package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;


public interface SvgElementHandler {

	void handle(SvgParser svgParser, SvgElement svgElement, Element element);

}