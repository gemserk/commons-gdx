package com.gemserk.commons.svg.inkscape;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;
import com.gemserk.vecmath.Vector3f;

public class SvgParseSvgUseTest {

	private Document document;

	public static interface SvgUse extends SvgElement {

		float getX();

		float getY();

		float getWidth();

		float getHeight();

		Matrix3f getTransform();

		/**
		 * Returns the id of referenced SvgElement.
		 */
		String getReference();

	}

	public static class SvgUseImpl implements SvgUse {

		String id;

		String reference;

		float x, y, width, height;

		Matrix3f transform;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public float getWidth() {
			return width;
		}

		public void setWidth(float width) {
			this.width = width;
		}

		public float getHeight() {
			return height;
		}

		public void setHeight(float height) {
			this.height = height;
		}

		public Matrix3f getTransform() {
			return transform;
		}

		public void setTransform(Matrix3f transform) {
			this.transform = transform;
		}

		public void setReference(String reference) {
			this.reference = reference;
		}

		public String getReference() {
			return reference;
		}

	}

	public static SvgUse getSvgUse(Element element) {
		String id = element.getAttribute("id");
		String reference = element.getAttribute("xlink:href");

		float x = Float.parseFloat(element.getAttribute("x"));
		float y = Float.parseFloat(element.getAttribute("y"));
		float width = Float.parseFloat(element.getAttribute("width"));
		float height = Float.parseFloat(element.getAttribute("height"));

		Matrix3f transform = new Matrix3f();
		transform.setIdentity();

		// should be processed before by the TransformProcessor?
		SvgInkscapeUtils.getTransform(element, transform);

		SvgUseImpl svgUse = new SvgUseImpl();

		svgUse.setId(id);
		svgUse.setX(x);
		svgUse.setY(y);
		svgUse.setWidth(width);
		svgUse.setHeight(height);
		svgUse.setTransform(transform);
		svgUse.setReference(reference);

		return svgUse;
	}

	@Before
	public void setup() {
		InputStream svgStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-svguse.svg");
		document = new DocumentParser().parse(svgStream);
	}

	SvgImage svgImage;
	SvgDocument svgDocument;

	@Test
	public void testGetGroupTransform() {
		SvgParser svgParser = new SvgParser();

		svgParser.svgConverters.converters.put("use", new SvgElementConverter<SvgUse>() {
			@Override
			public SvgUse convert(Element element) {
				return getSvgUse(element);
			}
		});

		svgParser.addHandler(new SvgDocumentHandler() {
			@Override
			protected void handle(SvgParser svgParser, SvgDocument svgDocument, Element element) {
				SvgParseSvgUseTest.this.svgDocument = svgDocument;
			}
		});

		svgParser.addHandler(new SvgInkscapeImageHandler() {

			Matrix3f transform = new Matrix3f();

			private boolean isFlipped(Matrix3f matrix) {
				return matrix.getM00() != matrix.getM11();
			}

			@Override
			protected void handle(SvgParser svgParser, SvgInkscapeImage svgImage, Element element) {
				SvgParseSvgUseTest.this.svgImage = svgImage;
			}

		});

		svgParser.addHandler(new SvgElementHandler() {

			@Override
			public void handle(SvgParser svgParser, SvgElement svgElement, Element element) {
				if (!(svgElement instanceof SvgUse))
					return;
				handle(svgParser, (SvgUse) svgElement, element);
			}

			@Override
			public void postHandle(SvgParser svgParser, SvgElement svgElement, Element element) {

			}
			

			Matrix3f transform = new Matrix3f();

			private boolean isFlipped(Matrix3f matrix) {
				return matrix.getM00() != matrix.getM11();
			}

			protected void handle(SvgParser svgParser, SvgUse svgUse, Element element) {
//				Matrix3f imageTransform = svgImage.getTransform();
				Matrix3f useTransform = svgUse.getTransform();

				float width = svgImage.getWidth();
				float height = svgImage.getHeight();

				transform.set(svgImage.getTransform());

				// Matrix3f transform = svgImage.getTransform();

				Vector3f position = new Vector3f(svgImage.getX() + width * 0.5f, svgImage.getY() + height * 0.5f, 1f);
				transform.transform(position);

				Vector3f direction = new Vector3f(1f, 0f, 0f);
				transform.transform(direction);

				float angle = 360f - (float) (Math.atan2(direction.y, direction.x) * 180 / Math.PI);

				float sx = 1f;
				float sy = 1f;

				if (isFlipped(transform))
					sy = -1f;

				// this stuff should be processed automatically using SVG specification with transformation of the document, etc.
//				float x = position.x;
//				float y = svgDocument.getHeight() - position.y;
				
				position.y = svgDocument.getHeight() - position.y;
				
				useTransform.transform(position);
				
				System.out.println("x: " + position.x);
				System.out.println("y: " + position.y);
				System.out.println("angle: " + angle);

				// handleImageObject(svgImage, element, x, y, width, height, sx, sy, angle);

			}

		});

		svgParser.parse(document);
	}

}
