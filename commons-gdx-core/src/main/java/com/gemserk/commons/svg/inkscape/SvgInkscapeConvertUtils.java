package com.gemserk.commons.svg.inkscape;

import org.w3c.dom.Element;

public class SvgInkscapeConvertUtils {

	public static SvgInkscapeGroup getSvgInkscapeGroup(Element element) {
		String groupMode = SvgInkscapeUtils.getGroupMode(element);
		String label = SvgInkscapeUtils.getLabel(element);
		SvgGroup svgGroup = SvgConvertUtils.getSvgGroup(element);
		SvgInkscapeGroupImpl svgInkscapeGroup = new SvgInkscapeGroupImpl(svgGroup);
		svgInkscapeGroup.setGroupMode(groupMode);
		svgInkscapeGroup.setLabel(label);
		return svgInkscapeGroup;
	}

	public static SvgInkscapeImage getSvgInkscapeImage(Element element) {
		SvgImage svgImage = SvgConvertUtils.getSvgImage(element);
		String label = SvgInkscapeUtils.getLabel(element);
		SvgInkscapeImageImpl svgInkscapeImage = new SvgInkscapeImageImpl(svgImage);
		svgInkscapeImage.setLabel(label);
		return svgInkscapeImage;
	}

	public static SvgElementConverter<SvgGroup> groupConverter() {
		return new SvgElementConverter<SvgGroup>() {
			@Override
			public SvgGroup convert(Element element) {
				return getSvgInkscapeGroup(element);
			}
		};
	}
	
	public static SvgElementConverter<SvgImage> imageConverter() {
		return new SvgElementConverter<SvgImage>() {
			@Override
			public SvgImage convert(Element element) {
				return getSvgInkscapeImage(element);
			}
		};
	}
	
}
