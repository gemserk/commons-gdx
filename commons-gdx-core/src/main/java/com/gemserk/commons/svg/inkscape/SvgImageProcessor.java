package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;


public class SvgImageProcessor implements SvgElementProcessor {

	@Override
	public void process(SvgParser svgParser, Element element) {
		svgParser.handle(getSvgElement(element), element);
	}
	
	protected SvgElement getSvgElement(Element element) {
		return getSvgImage(element);
	}

	protected SvgImage getSvgImage(Element element) {
		String id = element.getAttribute("id");
		float x = Float.parseFloat(element.getAttribute("x"));
		float y = Float.parseFloat(element.getAttribute("y"));
		float width = Float.parseFloat(element.getAttribute("width"));
		float height = Float.parseFloat(element.getAttribute("height"));

		Matrix3f transform = new Matrix3f();
		transform.setIdentity();

		// should be processed before by the TransformProcessor?
		SvgInkscapeUtils.getTransform(element, transform);

		SvgImageImpl svgImage = new SvgImageImpl();
		svgImage.setId(id);
		svgImage.setX(x);
		svgImage.setY(y);
		svgImage.setWidth(width);
		svgImage.setHeight(height);
		svgImage.setTransform(transform);
		
		return svgImage;
	}

	@Override
	public boolean handles(Element element) {
		return element.getNodeName().equals("image");
	}

}