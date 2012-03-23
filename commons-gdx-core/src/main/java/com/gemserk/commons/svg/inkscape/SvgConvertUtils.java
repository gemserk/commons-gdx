package com.gemserk.commons.svg.inkscape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

import com.badlogic.gdx.graphics.Color;
import com.gemserk.vecmath.Matrix3f;
import com.gemserk.vecmath.Vector2f;

public class SvgConvertUtils {

	private static enum Command {
		None, AbsoluteMoveTo, RelativeMoveTo, AbsoluteLineTo, RelativeLineTo, AbsoluteElipticalArc, RelativeElipticalArc, ClosePath, //
		AbsoluteCurveTo, RelativeCurveTo, AbsoluteSmoothCurveTo, RelativeSmoothCurveTo
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
			put("C", Command.AbsoluteCurveTo);
			put("c", Command.RelativeCurveTo);
			put("S", Command.AbsoluteSmoothCurveTo);
			put("s", Command.RelativeSmoothCurveTo);
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

	public static SvgPath getSvgPath(Element element) {

		String id = element.getAttribute("id");
		// String label = SvgInkscapeUtils.getLabel(element);
		String d = element.getAttribute("d");

		Vector2f[] points = pathDataToVector2fList(d);

		SvgPathImpl svgPath = new SvgPathImpl();
		svgPath.setId(id);
		svgPath.setPoints(points);
		return svgPath;
	}

	/**
	 * Returns a list of Vector2f[] built using an SVG path data, it only supports basic stuff.
	 */
	public static Vector2f[] pathDataToVector2fList(String d) {
		StringTokenizer tokens = new StringTokenizer(d, ", ");

		ArrayList<Vector2f> pointList = new ArrayList<Vector2f>();

		Vector2f currentLocation = new Vector2f(0f, 0f);

		Command currentCommand = Command.None;

		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();

			Command command = commandMap.get(token);

			if (command != null) {
				currentCommand = command;
				continue;
			}

			if (currentCommand == Command.None)
				continue;

			// http://www.w3.org/TR/SVG/paths.html#PathData

			if (currentCommand == Command.RelativeMoveTo || currentCommand == Command.RelativeLineTo) {
				float xr = Float.parseFloat(token);
				float yr = Float.parseFloat(tokens.nextToken());

				float x = xr + currentLocation.x;
				float y = yr + currentLocation.y;

				pointList.add(new Vector2f(x, y));

				currentLocation.set(x, y);
			} else if (currentCommand == Command.AbsoluteMoveTo || currentCommand == Command.AbsoluteLineTo) {
				float x = Float.parseFloat(token);
				float y = Float.parseFloat(tokens.nextToken());
				pointList.add(new Vector2f(x, y));
				currentLocation.set(x, y);
			} else {
				throw new UnsupportedOperationException(currentCommand.toString() + " not supported yet");
			}

			// other commands?

		}

		Vector2f[] points = new Vector2f[pointList.size()];
		pointList.toArray(points);
		return points;
	}

	public static Color fillColorFromStyle(String style) {
		return fillColorFromStyle(new Color(), style);
	}

	public static Color fillColorFromStyle(Color color, String style) {
		String fillColorStr = getFillFromStyle(style);

		if (fillColorStr != null)
			colorValue(color, fillColorStr);

		String fillOpacityStr = getFillOpacityFromStyle(style);

		if (fillOpacityStr != null)
			color.a = Float.valueOf(fillOpacityStr);

		return color;
	}

	private static Pattern fillStylePattern = Pattern.compile("fill:(.[^;]*)");
	private static Pattern fillOpacityStylePattern = Pattern.compile("fill\\-opacity:(.[^;]*)");

	public static String getFillFromStyle(String style) {
		Matcher matcher = fillStylePattern.matcher(style.trim());

		while (matcher.find()) {
			if (matcher.groupCount() == 0)
				continue;
			String fill = matcher.group(1);
			return fill;
		}

		return null;
	}

	public static String getFillOpacityFromStyle(String style) {
		Matcher matcher = fillOpacityStylePattern.matcher(style.trim());

		while (matcher.find()) {
			if (matcher.groupCount() == 0)
				continue;
			String fill = matcher.group(1);
			return fill;
		}

		return null;
	}

	public static String replicateHexNotation(CharSequence hexColor) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(hexColor.charAt(0));
		stringBuilder.append(hexColor.charAt(0));
		stringBuilder.append(hexColor.charAt(1));
		stringBuilder.append(hexColor.charAt(1));
		stringBuilder.append(hexColor.charAt(2));
		stringBuilder.append(hexColor.charAt(2));
		return stringBuilder.toString();
	}

	public static Color colorValue(String colorValue) {
		return colorValue(new Color(), colorValue);
	}

	public static Color colorValue(Color color, String colorValue) {
		String hexNotationPrefix = "#";
		String rgbNotationPrefix = "rgb";
		int hexRadix = 16;

		if (colorValue.startsWith(hexNotationPrefix)) {
			String colorHexValue = colorValue.trim().substring(1);
			if (colorHexValue.length() == 3)
				colorHexValue = replicateHexNotation(colorHexValue);
			Color.rgb888ToColor(color, Integer.valueOf(colorHexValue, hexRadix));
			return color;
		} else if (colorValue.startsWith(rgbNotationPrefix)) {
			throw new UnsupportedOperationException("color from rgb() format not supported yet");
		} else {
			throw new UnsupportedOperationException("color style format not supported yet");
		}
	}

	public static float opacityValue(String opacityValue) {
		float opacity = Float.parseFloat(opacityValue);
		if (opacity < 0f)
			opacity = 0f;
		else if (opacity > 1f)
			opacity = 1f;
		return opacity;
	}

	/**
	 * Returns a new implementation of SvgElementConverter which converts the XML Element to SvgGroup.
	 */
	public static SvgElementConverter<SvgGroup> groupConverter() {
		return new SvgElementConverter<SvgGroup>() {
			@Override
			public SvgGroup convert(Element element) {
				return getSvgGroup(element);
			}
		};
	}

	/**
	 * Returns a new implementation of SvgElementConverter which converts the XML Element to SvgImage.
	 */
	public static SvgElementConverter<SvgImage> imageConverter() {
		return new SvgElementConverter<SvgImage>() {
			@Override
			public SvgImage convert(Element element) {
				return getSvgImage(element);
			}
		};
	}

	/**
	 * Returns a new implementation of SvgElementConverter which converts the XML Element to SvgPath.
	 */
	public static SvgElementConverter<SvgPath> pathConverter() {
		return new SvgElementConverter<SvgPath>() {
			@Override
			public SvgPath convert(Element element) {
				return getSvgPath(element);
			}
		};
	}

}
