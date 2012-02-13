package com.gemserk.commons.svg.inkscape;

import java.io.InputStream;
import java.util.Stack;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gemserk.vecmath.Matrix3f;

public class SvgTransformProcessorTest {

	public class SvgAnotherHandler {

		protected void processSvgDocument(Element element) {

		}

		protected void processSvgImage(Element element) {

		}

	}

	public class SvgProcessor {

		SvgAnotherHandler handler;

		public void process(Document document) {
			Element root = document.getDocumentElement();
			internalProcessElement(root);
		}

		void internalProcessElement(Element element) {
			NodeList list = element.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i) instanceof Element) {
					Element childElement = (Element) list.item(i);

					preProcess(childElement);

					processElement(childElement);

					internalProcessElement(childElement);

					postProcessElement(childElement);
				}
			}
		}

		protected void preProcess(Element element) {

		}

		protected void postProcessElement(Element element) {

		}

		protected void processElement(Element element) {

		}

		// protected void processElement(Element element) {
		//
		// element.setAttributeNS("gemserk", "absoluteTransform", "1");
		//
		// if (SvgNamespace.isSvg(element)) {
		// String id = SvgNamespace.getId(element);
		// float width = SvgNamespace.getWidth(element);
		// float height = SvgNamespace.getHeight(element);
		// handler.processSvgDocument(element);
		// // we have a document!
		// }
		//
		// if (SvgNamespace.isImage(element)) {
		// handler.processSvgImage(element);
		// }
		//
		// }

	}

	public static final String GemserkNamespace = "gemserk";

	public class SvgTransformProcessor extends SvgProcessor {

		protected Stack<Matrix3f> transformStack;

		public SvgTransformProcessor() {
			transformStack = new Stack<Matrix3f>();
			Matrix3f matrix = new Matrix3f();
			matrix.setIdentity();
			transformStack.push(matrix);
		}

		@Override
		protected void postProcessElement(Element element) {
			transformStack.pop();
		}

		@Override
		protected void processElement(Element element) {
			Matrix3f transform = SvgNamespace.getTransform(element);
			Matrix3f parentTransform = transformStack.peek();

			transform.mul(parentTransform);

			transformStack.push(transform);

			element.setAttributeNS(GemserkNamespace, "absoluteTransform", SvgInkscapeUtils.transformToAttribute(transform));
		}

	}

	public class SvgLogProcessor extends SvgProcessor {

		@Override
		protected void processElement(Element element) {
			System.out.println("id: " + SvgNamespace.getId(element));
			System.out.println("local: " + element.getAttribute("transform"));
			System.out.println("absolute: " + element.getAttributeNS(GemserkNamespace, "absoluteTransform"));
		}

	}

	@Test
	public void test() {
		InputStream svgStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-svguse.svg");
		Document document = new DocumentParser().parse(svgStream);

		SvgProcessor svgProcessor = new SvgTransformProcessor();
		svgProcessor.process(document);

		new SvgLogProcessor().process(document);

	}
}
