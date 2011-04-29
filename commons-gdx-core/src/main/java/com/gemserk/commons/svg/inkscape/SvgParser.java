package com.gemserk.commons.svg.inkscape;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SvgParser {

	ArrayList<SvgElementHandler> handlers = new ArrayList<SvgElementHandler>();

	@SuppressWarnings("serial")
	ArrayList<SvgElementProcessor> processors = new ArrayList<SvgElementProcessor>() {
		{
			add(new SvgInkscapeGroupProcessor());
			add(new SvgInkscapeImageProcessor());
		}
	};
	
	private Boolean processChildren;

	public SvgParser() {

	}

	public void addHandler(SvgElementHandler handler) {
		handlers.add(handler);
	}

	public void parse(InputStream svg) {

		try {
			internalParse(svg);
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse SVG file", e);
		}

	}

	private void internalParse(InputStream svg) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return new InputSource(new ByteArrayInputStream(new byte[0]));
			}
		});

		Document doc = builder.parse(svg);
		Element root = doc.getDocumentElement();

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

		handle(svgDocument);

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

	public void handle(SvgElement svgElement) {
		for (int i = 0; i < handlers.size(); i++) {
			SvgElementHandler handler = handlers.get(i);
			handler.handle(this, svgElement);
		}
	}
	
	public void processChildren(boolean process) { 
		this.processChildren = process;
	}

}