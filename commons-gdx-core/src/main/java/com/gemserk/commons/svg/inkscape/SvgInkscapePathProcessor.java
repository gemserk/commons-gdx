package com.gemserk.commons.svg.inkscape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.vecmath.Vector2f;

import org.w3c.dom.Element;

public class SvgInkscapePathProcessor implements SvgElementProcessor {

	private static enum Command {

		None, RelativeMoveTo, AbsoluteMoveTo,
		// RelativeLineTo, AbsoluteLineTo, Etc...

	}

	@SuppressWarnings("serial")
	private static Map<String, Command> commandMap = new HashMap<String, Command>() {
		{
			put("m", Command.RelativeMoveTo);
			put("M", Command.AbsoluteMoveTo);
		}
	};

	private Command currentCommand = Command.None;
	
	@Override
	public void process(SvgParser svgParser, Element element) {
		SvgInkscapePath svgImage = getSvgPath(element);
		svgParser.handle(svgImage, element);
	}

	protected SvgInkscapePath getSvgPath(Element element) {

		String id = element.getAttribute("id");
		String label = SvgInkscapeUtils.getLabel(element);
		String d = element.getAttribute("d");

		StringTokenizer tokens = new StringTokenizer(d, ", ");

		ArrayList<Vector2f> pointList = new ArrayList<Vector2f>();

		Vector2f relativePoint = null;

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

			if (currentCommand == Command.RelativeMoveTo) {

				float x = Float.parseFloat(token);
				float y = Float.parseFloat(tokens.nextToken());

				Vector2f newPoint = new Vector2f(x, y);

				if (relativePoint != null)
					newPoint.add(relativePoint);

				pointList.add(newPoint);

				relativePoint = new Vector2f(newPoint);

			} else if (currentCommand == Command.AbsoluteMoveTo) {
				float x = Float.parseFloat(token);
				float y = Float.parseFloat(tokens.nextToken());
				pointList.add(new Vector2f(x, y));
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

	@Override
	public boolean handles(Element element) {
		return element.getNodeName().equals("path");
	}

}