package com.gemserk.commons.svg.inkscape;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class SvgParseGroupTest {
	
	public void test() {
		
		InputStream svgStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-groups.svg");
		Document document = new DocumentParser().parse(svgStream);
		
		SvgParser svgParser = new SvgParser();
		
		svgParser.addHandler(new SvgInkscapeGroupHandler() {
			@Override
			protected void handle(SvgParser svgParser, SvgInkscapeGroup svgInkscapeGroup, Element element) {
				
			}
		});
		
		svgParser.parse(document);
		
	}

}
