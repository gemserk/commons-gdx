package com.gemserk.commons.svg.inkscape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

import com.gemserk.vecmath.Matrix3f;
import com.gemserk.vecmath.Vector2f;

public class SvgConvertUtils {

	private static enum Command {
		None, AbsoluteMoveTo, RelativeMoveTo, AbsoluteLineTo, RelativeLineTo, AbsoluteElipticalArc, RelativeElipticalArc, ClosePath,
	}

	@SuppressWarnings("serial")
	private static Map<String, Command> commandMap = new HashMap<String, Command>() {
		{
			put("m", Command.RelativeMoveTo);
			put("M", Command.AbsoluteMoveTo);
			put("l", Command.RelativeLineTo);
			put("L", Command.AbsoluteLineTo);
			put("z", Command.ClosePath);
			put("Z", Command.ClosePath);
			put("A", Command.AbsoluteElipticalArc);
			put("a", Command.RelativeElipticalArc);
		}
	};

	public static SvgGroup getSvgGroup(Element element) {
		String id = element.getAttribute("id");

		Matrix3f transform = new Matrix3f();
		transform.setIdentity();

		SvgInkscapeUtils.getTransform(element, transform);

		SvgGroupImpl svgGroup = new SvgGroupImpl();

		svgGroup.setId(id);
		svgGroup.setTransform(transform);

		return svgGroup;
	}

	public static SvgImage getSvgImage(Element element) {
		String id = element.getAttribute("id");
		float x = Float.parseFloat(element.getAttribute("x"));
		float y = Float.parseFloat(element.getAttribute("y"));
		float width = Float.parseFloat(element.getAttribute("width"));
		float height = Float.parseFloat(element.getAttribute("height"));

		Matrix3f transform = new Matrix3f();
		transform.setIdentity();

		// should be processed before by the TransformProcessor?
		SvgInkscapeUtils.getTransform(element, transform);

		SvgImageImpl svgImage = new SvgImageImpl();
		svgImage.setId(id);
		svgImage.setX(x);
		svgImage.setY(y);
		svgImage.setWidth(width);
		svgImage.setHeight(height);
		svgImage.setTransform(transform);

		return svgImage;
	}

	public static SvgInkscapePath getSvgPath(Element element) {

		String id = element.getAttribute("id");
		String label = SvgInkscapeUtils.getLabel(element);
		String d = element.getAttribute("d");

		StringTokenizer tokens = new StringTokenizer(d, ", ");

		ArrayList<Vector2f> pointList = new ArrayList<Vector2f>();

		Vector2f relativePoint = null;
		
		Command currentCommand = Command.None;

		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();

			Command command = commandMap.get(token);

			if (command != null) {
				currentCommand = command;
				relativePoint = null;
				continue;
			}

			if (currentCommand == Command.None)
				continue;

			if (currentCommand == Command.RelativeMoveTo || currentCommand == Command.RelativeLineTo) {
				float x = Float.parseFloat(token);
				float y = Float.parseFloat(tokens.nextToken());

				Vector2f newPoint = new Vector2f(x, y);

				if (relativePoint != null)
					newPoint.add(relativePoint);

				pointList.add(newPoint);

				relativePoint = new Vector2f(newPoint);
			} else if (currentCommand == Command.AbsoluteMoveTo || currentCommand == Command.AbsoluteLineTo) {
				float x = Float.parseFloat(token);
				float y = Float.parseFloat(tokens.nextToken());
				pointList.add(new Vector2f(x, y));
			} else if (currentCommand == Command.AbsoluteElipticalArc) {

				float rx = Float.parseFloat(token);
				float ry = Float.parseFloat(tokens.nextToken());

				// TODO: ...

				// (rx ry x-axis-rotation large-arc-flag sweep-flag x y)+

			} else if (currentCommand == Command.RelativeElipticalArc) {

			}

			// other commands?

		}

		Vector2f[] points = new Vector2f[pointList.size()];
		pointList.toArray(points);

		SvgInkscapePathImpl svgPath = new SvgInkscapePathImpl();
		svgPath.setId(id);
		svgPath.setPoints(points);
		svgPath.setLabel(label);
		return svgPath;
	}

}
