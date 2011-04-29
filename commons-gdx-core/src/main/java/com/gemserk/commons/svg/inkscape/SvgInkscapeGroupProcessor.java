package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;


public class SvgInkscapeGroupProcessor implements SvgElementProcessor {

	@Override
	public void process(SvgParser svgParser, Element element) {
		String id = element.getAttribute("id");
		String groupMode = SvgInkscapeUtils.getGroupMode(element);
		String label = SvgInkscapeUtils.getLabel(element);
		
		SvgInkscapeGroupImpl svgGroup = new SvgInkscapeGroupImpl();
		svgGroup.setId(id);
		svgGroup.setGroupMode(groupMode);
		svgGroup.setLabel(label);
		
		svgParser.handle(svgGroup);
	}

	@Override
	public boolean handles(Element element) {
		return element.getNodeName().equals("g");
	}

}