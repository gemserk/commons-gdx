package com.gemserk.commons.svg.inkscape;

import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;


public class SvgParseGroupTest {
	
	@Test
	public void test() {
		
		InputStream svgStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-groups.svg");
		Document document = new DocumentParser().parse(svgStream);
		
		SvgParser svgParser = new SvgParser();
		
		svgParser.addHandler(new SvgInkscapeGroupHandler() {
			@Override
			protected void handle(SvgParser svgParser, SvgInkscapeGroup svgInkscapeGroup, Element element) {
				String id = svgInkscapeGroup.getId();
				if (!"group-path".equals(id)) 
					return;
				Matrix3f transform = svgInkscapeGroup.getTransform();
				
				Matrix3f expectedMatrix = new Matrix3f(new float[] { 1, 0, 20, 0, 1, 10, 0, 0, 1 });
				assertThat(transform, IsEqual.equalTo(expectedMatrix));
			}
		});
		
		svgParser.parse(document);
		
	}

}
