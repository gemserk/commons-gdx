package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

/**
 * Convert from an XML Element to a SvgElement
 * 
 * @author acoppes
 * 
 */
public interface SvgElementConverter<T extends SvgElement> {

	/**
	 * Returns a SvgElement built from the XML Element.
	 * 
	 * @param element
	 *            The XML Element to be converted.
	 */
	T convert(Element element);

}
