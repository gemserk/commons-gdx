package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;


public interface SvgElementProcessor {

	void process(SvgParser svgParser, Element element);

	boolean handles(Element element);

}