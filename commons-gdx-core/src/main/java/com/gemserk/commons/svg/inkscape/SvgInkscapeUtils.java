package com.gemserk.commons.svg.inkscape;

import java.util.StringTokenizer;

import javax.vecmath.Matrix3f;

import org.w3c.dom.Element;

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

	public static Matrix3f getTransform(Element element, Matrix3f m) {

		m.setIdentity();

		String type = element.getAttribute("transform");

		if (type == null)
			return m;

		if (type.startsWith("scale")) {
			type = type.substring(0, type.length() - 1);
			type = type.substring("scale(".length());
			StringTokenizer tokens = new StringTokenizer(type, ", ");
			float sx = Float.parseFloat(tokens.nextToken());
			float sy = Float.parseFloat(tokens.nextToken());
			m.setM00(sx);
			m.setM11(sy);
			// m.setToScaling(sx, sy, 1f);
		} else if (type.startsWith("matrix")) {

			type = type.substring(0, type.length() - 1);
			type = type.substring("matrix(".length());

			StringTokenizer tokens = new StringTokenizer(type, ", ");

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

		}

		return m;
	}

}
