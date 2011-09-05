package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

/**
 * Called by the SvgParser to handle a SvgElement.
 * 
 * @author acoppes
 * 
 */
public interface SvgElementHandler {

	/**
	 * Called to handle the SvgElement.
	 * 
	 * @param svgParser
	 *            The SvgParser to continue the SVG parsing or not.
	 * @param svgElement
	 *            The SvgElement to do stuff with.
	 * @param element
	 *            The XML element to get extra information.
	 */
	void handle(SvgParser svgParser, SvgElement svgElement, Element element);

}