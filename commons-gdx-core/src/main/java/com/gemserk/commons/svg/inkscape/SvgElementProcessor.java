package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

/**
 * Provides a way to define an XML Element processor.
 * 
 * @author acoppes
 * 
 */
public abstract class SvgElementProcessor {

	/**
	 * Process the XML element in the way it want to.
	 * 
	 * @param svgParser
	 *            The SvgParser to call more parsing or not.
	 * @param element
	 *            The XML element to process.
	 */
	public void process(SvgParser svgParser, Element element) {
		svgParser.handle(getSvgElement(element), element);
	}
	
	public void postProcess(SvgParser svgParser, Element element) {
		svgParser.postHandle(getSvgElement(element), element);
	}

	/**
	 * Returns a SvgElement built from the XML Element.
	 * 
	 * @param element
	 *            The XML Element to be converted.
	 */
	protected abstract SvgElement getSvgElement(Element element);

	/**
	 * Returns true if the processor is able to process the element, false otherwise.
	 * 
	 * @param element
	 *            The XML element to check if it is able to process or not.
	 */
	public abstract boolean handles(Element element);

}