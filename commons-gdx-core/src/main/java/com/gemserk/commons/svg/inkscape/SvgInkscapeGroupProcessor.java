package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;


public class SvgInkscapeGroupProcessor extends SvgGroupProcessor {

	@Override
	public void process(SvgParser svgParser, Element element) {
		svgParser.handle(getSvgElement(element), element);
	}
	
	protected SvgElement getSvgElement(Element element) {
		return getSvgInkscapeGroup(element);
	}
	
	protected SvgInkscapeGroup getSvgInkscapeGroup(Element element) {
		String groupMode = SvgInkscapeUtils.getGroupMode(element);
		String label = SvgInkscapeUtils.getLabel(element);
		
		SvgGroup svgGroup = super.getSvgGroup(element);
		
		SvgInkscapeGroupImpl svgInkscapeGroup = new SvgInkscapeGroupImpl(svgGroup);

		svgInkscapeGroup.setGroupMode(groupMode);
		svgInkscapeGroup.setLabel(label);
		return svgInkscapeGroup;
	}

	@Override
	public boolean handles(Element element) {
		return element.getNodeName().equals("g");
	}

}