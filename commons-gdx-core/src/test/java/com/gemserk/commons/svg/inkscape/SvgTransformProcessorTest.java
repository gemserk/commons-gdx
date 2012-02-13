package com.gemserk.commons.svg.inkscape;

import java.io.InputStream;
import java.util.Stack;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gemserk.vecmath.Matrix3f;

public class SvgTransformProcessorTest {

	public class SvgProcessor {

		protected Document document;

		public void setDocument(Document document) {
			this.document = document;
		}

		public void preProcess(Element element) {

		}

		public void postProcessElement(Element element) {

		}

		/**
		 * Returns true if you want to continue parsing children elements of this node.
		 */
		public boolean processElement(Element element) {
			return true;
		}

	}

	public class SvgDocumentProcessor {

		SvgProcessor handler;

		public void process(Document document, SvgProcessor handler) {
			this.handler = handler;
			this.handler.setDocument(document);
			Element root = document.getDocumentElement();
			internalProcessElement(root);
		}

		void internalProcessElement(Element element) {
			NodeList list = element.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i) instanceof Element) {
					Element childElement = (Element) list.item(i);

					handler.preProcess(childElement);
					if (handler.processElement(childElement))
						internalProcessElement(childElement);
					handler.postProcessElement(childElement);
				}
			}
		}

	}

	public class SvgTransformProcessor extends SvgProcessor {

		protected Stack<Matrix3f> transformStack;

		public SvgTransformProcessor() {
			transformStack = new Stack<Matrix3f>();
			Matrix3f matrix = new Matrix3f();
			matrix.setIdentity();
			transformStack.push(matrix);
		}

		@Override
		public void postProcessElement(Element element) {
			transformStack.pop();
		}

		@Override
		public boolean processElement(Element element) {
			Matrix3f transform = SvgNamespace.getTransform(element);
			Matrix3f parentTransform = transformStack.peek();

			transform.mul(parentTransform);

			transformStack.push(transform);

			element.setAttributeNS(GemserkNamespace.Name, GemserkNamespace.AbsoluteTransform, SvgInkscapeUtils.transformToAttribute(transform));
			return true;
		}

	}

	public class SvgConfigureIdProcessor extends SvgProcessor {

		@Override
		public boolean processElement(Element element) {
			if ("".equals(element.getAttribute(SvgNamespace.attributeId)))
				return true;
			element.setIdAttribute(SvgNamespace.attributeId, true);
			return true;
		}

	}

	public class SvgLogProcessor extends SvgProcessor {

		@Override
		public boolean processElement(Element element) {

			if (!SvgNamespace.isUse(element) && !SvgNamespace.isImage(element) && !SvgNamespace.isSvg(element))
				return true;

			System.out.println("id: " + SvgNamespace.getId(element));
			System.out.println("local: " + element.getAttribute("transform"));
			System.out.println("absolute: " + element.getAttributeNS(GemserkNamespace.Name, GemserkNamespace.AbsoluteTransform));

			return true;
		}

	}

	public class SvgUseProcessor extends SvgProcessor {

		@Override
		public boolean processElement(Element element) {

			if (!SvgNamespace.isUse(element))
				return true;

			// for now, using only one level of indirection...

			String sourceElementId = SvgNamespace.getXlinkHref(element);

			if (sourceElementId.startsWith("#")) {
				sourceElementId = sourceElementId.substring(1);

				Element sourceElement = document.getElementById(sourceElementId);

				if (sourceElement == null)
					throw new RuntimeException("There is no element with id=" + sourceElementId);

				Element clonedSourceElement = (Element) sourceElement.cloneNode(true);

				clonedSourceElement.setAttribute(SvgNamespace.attributeId, SvgNamespace.getId(element));

				Node parentNode = element.getParentNode();
				parentNode.removeChild(element);
				parentNode.appendChild(clonedSourceElement);

				return false;
			} else {
				throw new RuntimeException("Not implemented, xlink:href=" + sourceElementId);
			}

		}

	}

	@Test
	public void test() {
		InputStream svgStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-svguse.svg");

		DocumentParser documentParser = new DocumentParser();
		Document document = documentParser.parse(svgStream);

		// Element rootElement = document.getDocumentElement();
		// rootElement.setIdAttribute("id", true);
		// rootElement.setIdAttributeNode(, isId)

		// Element elementById = document.getElementById("svg2");

		SvgDocumentProcessor svgDocumentProcessor = new SvgDocumentProcessor();

		svgDocumentProcessor.process(document, new SvgConfigureIdProcessor());
		svgDocumentProcessor.process(document, new SvgTransformProcessor());
		svgDocumentProcessor.process(document, new SvgUseProcessor());
		svgDocumentProcessor.process(document, new SvgLogProcessor());
	}
}
