package com.gemserk.commons.svg.inkscape;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SvgParser {

	private ArrayList<SvgElementHandler> handlers = new ArrayList<SvgElementHandler>();

	@SuppressWarnings("serial")
	private ArrayList<SvgElementProcessor> processors = new ArrayList<SvgElementProcessor>() {
		{
			add(new SvgInkscapeGroupProcessor());
			add(new SvgInkscapeImageProcessor());
			add(new SvgInkscapePathProcessor());
		}
	};

	private Boolean processChildren;

	public void addHandler(SvgElementHandler handler) {
		handlers.add(handler);
	}

	/**
	 * Parses a SVG Document and provides a way to process custom logic when SVG Element are parsed.
	 * 
	 * @param document
	 */
	public void parse(Document document) {
		Element root = document.getDocumentElement();

		String widthString = root.getAttribute("width");
		while (Character.isLetter(widthString.charAt(widthString.length() - 1))) {
			widthString = widthString.substring(0, widthString.length() - 1);
		}

		String heightString = root.getAttribute("height");
		while (Character.isLetter(heightString.charAt(heightString.length() - 1))) {
			heightString = heightString.substring(0, heightString.length() - 1);
		}

		float width = Float.parseFloat(widthString);
		float height = Float.parseFloat(heightString);

		String id = root.getAttribute("id");

		SvgDocumentImpl svgDocument = new SvgDocumentImpl();
		svgDocument.setId(id);
		svgDocument.setWidth(width);
		svgDocument.setHeight(height);

		handle(svgDocument, root);

		loadChildren(root);
	}

	void loadChildren(Element element) {
		NodeList list = element.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i) instanceof Element) {
				processChildren = true;
				Element childElement = (Element) list.item(i);
				processElement(childElement);

				if (!processChildren)
					continue;

				loadChildren(childElement);
			}
		}
	}

	private void processElement(Element element) {
		for (int i = 0; i < processors.size(); i++) {
			SvgElementProcessor processor = (SvgElementProcessor) processors.get(i);
			if (!processor.handles(element))
				continue;
			processor.process(this, element);
		}
	}

	public void handle(SvgElement svgElement, Element element) {
		for (int i = 0; i < handlers.size(); i++) {
			SvgElementHandler handler = handlers.get(i);
			handler.handle(this, svgElement, element);
		}
	}

	public void processChildren(boolean process) {
		this.processChildren = process;
	}

}