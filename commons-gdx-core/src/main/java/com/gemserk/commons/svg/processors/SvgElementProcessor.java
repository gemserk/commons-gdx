package com.gemserk.commons.svg.processors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SvgElementProcessor {

	protected Document document;

	public void setDocument(Document document) {
		this.document = document;
	}

	public void preProcess(Element element) {

	}

	public void postProcessElement(Element element) {

	}

	/**
	 * Returns true if you want to continue parsing children elements of this node.
	 */
	public boolean processElement(Element element) {
		return true;
	}

}