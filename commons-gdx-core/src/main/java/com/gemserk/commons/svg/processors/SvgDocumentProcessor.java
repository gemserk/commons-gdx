package com.gemserk.commons.svg.processors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SvgDocumentProcessor {

	SvgElementProcessor handler;

	public void process(Document document, SvgElementProcessor handler) {
		this.handler = handler;
		this.handler.setDocument(document);
		Element root = document.getDocumentElement();
		internalProcessElement(root);
	}

	void internalProcessChildren(Element element) {
		NodeList list = element.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i) instanceof Element) {
				Element childElement = (Element) list.item(i);
				internalProcessElement(childElement);
			}
		}
	}

	private void internalProcessElement(Element element) {
		handler.preProcess(element);
		if (handler.processElement(element))
			internalProcessChildren(element);
		handler.postProcessElement(element);
	}

}