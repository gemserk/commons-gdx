package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

/**
 * Provides a way to define an XML Element processor.
 * 
 * @author acoppes
 * 
 */
public interface SvgElementProcessor {

	/**
	 * Process the XML element in the way it want to.
	 * 
	 * @param svgParser
	 *            The SvgParser to call more parsing or not.
	 * @param element
	 *            The XML element to process.
	 */
	void process(SvgParser svgParser, Element element);

	/**
	 * Returns true if the processor is able to process the element, false otherwise.
	 * 
	 * @param element
	 *            The XML element to check if it is able to process or not.
	 */
	boolean handles(Element element);

}