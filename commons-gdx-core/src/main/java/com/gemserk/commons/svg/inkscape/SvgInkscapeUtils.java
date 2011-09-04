package com.gemserk.commons.svg.inkscape;

import java.util.StringTokenizer;

import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;

public class SvgInkscapeUtils {

	/** The namespace for inkscape */
	public static final String INKSCAPE = "http://www.inkscape.org/namespaces/inkscape";
	/** The namespace for sodipodi */
	public static final String SODIPODI = "http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd";
	/** The namespace for xlink */
	public static final String XLINK = "http://www.w3.org/1999/xlink";

	public static String getLabel(Element element) {
		return element.getAttributeNS(INKSCAPE, "label");
	}

	public static boolean isLayer(Element element) {
		String groupMode = element.getAttributeNS(INKSCAPE, "groupmode");
		if (groupMode == null)
			return false;
		return "layer".equals(groupMode);
	}

	public static String getGroupMode(Element element) {
		return element.getAttributeNS(INKSCAPE, "groupmode");
	}

	/**
	 * Process the transform attribute of an Element and applies the specified transforms to a new matrix based on http://www.w3.org/TR/SVG/coords.html#TransformAttribute.
	 * 
	 * @param element
	 *            The SVG element which contains the transform attribute.
	 * @param m
	 *            The matrix to be modified.
	 * @return The new matrix with the transforms applied.
	 */
	public static Matrix3f getTransform(Element element) {
		Matrix3f m = new Matrix3f();
		m.setIdentity();
		return getTransform(element, m);
	}

	/**
	 * Process the transform attribute of an Element and applies the specified transforms to the specified matrix based on http://www.w3.org/TR/SVG/coords.html#TransformAttribute. If matrix is null then a new matrix is returned.
	 * 
	 * @param element
	 *            The SVG element which contains the transform attribute.
	 * @param m
	 *            The matrix to be modified.
	 * @return The modified matrix.
	 */
	public static Matrix3f getTransform(Element element, Matrix3f m) {
		String transform = element.getAttribute("transform");

		if (transform == null)
			return m;

		return parseTransformAttribute(transform, m);
	}

	/**
	 * Parses a SVG Element transform attribute and applies each transform to the specified matrix.
	 * 
	 * @param transformAttribute
	 *            The SVG Element transform attribute.
	 * @param m
	 *            The matrix to be modified.
	 * @return The matrix with all transforms applied.
	 */
	public static Matrix3f parseTransformAttribute(String transformAttribute, Matrix3f m) {

		transformAttribute = transformAttribute.replace(" ", "");

		if (transformAttribute.startsWith("scale")) {
			transformAttribute = transformAttribute.substring(0, transformAttribute.length() - 1);
			transformAttribute = transformAttribute.substring("scale(".length());
			StringTokenizer tokens = new StringTokenizer(transformAttribute, ", ");

			float sx = Float.parseFloat(tokens.nextToken());
			float sy = sx;

			if (tokens.hasMoreTokens())
				sy = Float.parseFloat(tokens.nextToken());

			m.setM00(sx);
			m.setM11(sy);
		} else if (transformAttribute.startsWith("matrix")) {

			transformAttribute = transformAttribute.substring(0, transformAttribute.length() - 1);
			transformAttribute = transformAttribute.substring("matrix(".length());

			StringTokenizer tokens = new StringTokenizer(transformAttribute, ", ");

			float[] tr = new float[6];

			for (int j = 0; j < tr.length; j++) {
				tr[j] = Float.parseFloat(tokens.nextToken());
			}

			m.setM00(tr[0]);
			m.setM10(tr[1]);

			m.setM01(tr[2]);
			m.setM11(tr[3]);

			m.setM02(tr[4]);
			m.setM12(tr[5]);
		} else if (transformAttribute.startsWith("translate")) {
			transformAttribute = transformAttribute.substring(0, transformAttribute.length() - 1);
			transformAttribute = transformAttribute.substring("translate(".length());
			StringTokenizer tokens = new StringTokenizer(transformAttribute, ", ");

			float tx = Float.parseFloat(tokens.nextToken());
			float ty = Float.parseFloat(tokens.nextToken());

			m.setM02(tx);
			m.setM12(ty);
		}
		return m;
	}

	public static boolean isFlipped(Matrix3f matrix) {
		return matrix.getM00() != matrix.getM11();
	}

}
