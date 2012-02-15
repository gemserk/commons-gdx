package com.gemserk.commons.svg.processors;

import java.io.InputStream;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gemserk.commons.svg.inkscape.DocumentParser;
import com.gemserk.commons.svg.inkscape.GemserkNamespace;
import com.gemserk.commons.svg.inkscape.SvgNamespace;

public class SvgApplyTransformProcessorTest {

	public class SvgLogProcessor extends SvgElementProcessor {

		@Override
		public boolean processElement(Element element) {

			if (!SvgNamespace.isUse(element) && !SvgNamespace.isImage(element) && !SvgNamespace.isSvg(element))
				return true;

			System.out.println("id: " + SvgNamespace.getId(element));
			System.out.println("type: " + element.getNodeName());
			System.out.println("local: " + SvgNamespace.getTransform(element));
			System.out.println("absolute: " + GemserkNamespace.getAbsoluteTransform(element));

			if (GemserkNamespace.hasAttribute(element, GemserkNamespace.attributeCenterX))
				System.out.println("center.x: " + GemserkNamespace.getCenterX(element));
			if (GemserkNamespace.hasAttribute(element, GemserkNamespace.attributeCenterY))
				System.out.println("center.y: " + GemserkNamespace.getCenterY(element));
			if (GemserkNamespace.hasAttribute(element, GemserkNamespace.attributeAngle))
				System.out.println("angle: " + GemserkNamespace.getAngle(element));

			return true;
		}

	}

	@Test
	public void test() {
		InputStream svgStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-transforms.svg");

		DocumentParser documentParser = new DocumentParser();
		Document document = documentParser.parse(svgStream);

		SvgDocumentProcessor svgDocumentProcessor = new SvgDocumentProcessor();

		svgDocumentProcessor.process(document, new SvgConfigureIdProcessor());
		svgDocumentProcessor.process(document, new SvgTransformProcessor());
		svgDocumentProcessor.process(document, new SvgUseProcessor());
	
		svgDocumentProcessor.process(document, new SvgCalculateCenterAndAngleProcessor());

		svgDocumentProcessor.process(document, new SvgLogProcessor());
	}
}
