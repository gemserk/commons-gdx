package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;


public class SvgGroupProcessor implements SvgElementProcessor {

	@Override
	public void process(SvgParser svgParser, Element element) {
		SvgGroup svgGroup = getSvgGroup(element);
		svgParser.handle(svgGroup, element);
	}

	protected SvgGroup getSvgGroup(Element element) {
		String id = element.getAttribute("id");

		Matrix3f transform = new Matrix3f();
		transform.setIdentity();

		// should be processed before by the TransformProcessor?
		SvgInkscapeUtils.getTransform(element, transform);

		SvgGroupImpl svgGroup = new SvgGroupImpl();
		
		svgGroup.setId(id);
		svgGroup.setTransform(transform);
		
		return svgGroup;
	}

	@Override
	public boolean handles(Element element) {
		return element.getNodeName().equals("g");
	}

}