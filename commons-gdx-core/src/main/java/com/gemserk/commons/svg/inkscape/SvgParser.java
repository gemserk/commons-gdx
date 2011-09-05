package com.gemserk.commons.svg.inkscape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SvgParser {

	private ArrayList<SvgElementHandler> handlers = new ArrayList<SvgElementHandler>();

	class SvgConverters {

		Map<String, SvgElementConverter> converters = new HashMap<String, SvgElementConverter>() {
			{
				put("g", SvgInkscapeConvertUtils.groupConverter());
				put("image", SvgInkscapeConvertUtils.imageConverter());
				put("path", SvgConvertUtils.pathConverter());
			}
		};

		public SvgElementConverter<SvgElement> get(Element element) {
			return converters.get(element.getNodeName().toLowerCase());
		}

	}

	SvgConverters svgConverters = new SvgConverters();

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

				if (!processChildren) {
					// post process element
					postProcessElement(childElement);
					continue;
				}

				loadChildren(childElement);

				// post process element
				postProcessElement(childElement);
			}
		}
	}

	private void processElement(Element element) {
		SvgElementConverter<SvgElement> converter = svgConverters.get(element);
		if (converter == null)
			return;
		handle(converter.convert(element), element);
	}

	private void postProcessElement(Element element) {
		SvgElementConverter<SvgElement> converter = svgConverters.get(element);
		if (converter == null)
			return;
		postHandle(converter.convert(element), element);
	}

	public void handle(SvgElement svgElement, Element element) {
		for (int i = 0; i < handlers.size(); i++) {
			SvgElementHandler handler = handlers.get(i);
			handler.handle(this, svgElement, element);
		}
	}

	public void postHandle(SvgElement svgElement, Element element) {
		for (int i = 0; i < handlers.size(); i++) {
			SvgElementHandler handler = handlers.get(i);
			handler.postHandle(this, svgElement, element);
		}
	}

	public void processChildren(boolean process) {
		this.processChildren = process;
	}

}