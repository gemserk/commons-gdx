package com.gemserk.commons.svg.processors;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gemserk.commons.svg.inkscape.SvgNamespace;

public class SvgUseProcessor extends SvgElementProcessor {

	@Override
	public boolean processElement(Element svgUseElement) {

		if (!SvgNamespace.isUse(svgUseElement))
			return true;

		// for now, using only one level of indirection...

		String sourceElementId = SvgNamespace.getXlinkHref(svgUseElement);

		if (sourceElementId.startsWith("#")) {
			sourceElementId = sourceElementId.substring(1);

			Element sourceElement = document.getElementById(sourceElementId);

			if (sourceElement == null)
				throw new RuntimeException("Can'f find element with id=" + sourceElementId + " for svg:use processor");
			
			Element clonedSourceElement = (Element) sourceElement.cloneNode(true);

			Element svgGroup = document.createElementNS("svg", "g");

			NamedNodeMap svgUseAttributes = svgUseElement.getAttributes();

			for (int i = 0; i < svgUseAttributes.getLength(); i++) {
				Node svgUseAttribute = svgUseAttributes.item(i);
				if ("x".equalsIgnoreCase(svgUseAttribute.getNodeName()))
					continue;
				if ("y".equalsIgnoreCase(svgUseAttribute.getNodeName()))
					continue;
				if ("width".equalsIgnoreCase(svgUseAttribute.getNodeName()))
					continue;
				if ("height".equalsIgnoreCase(svgUseAttribute.getNodeName()))
					continue;
				if ("xlink:href".equalsIgnoreCase(svgUseAttribute.getNodeName()))
					continue;
				if ("id".equalsIgnoreCase(svgUseAttribute.getNodeName()))
					continue;
				svgGroup.setAttribute(svgUseAttribute.getNodeName(), svgUseAttribute.getNodeValue());
			}

			Node svgUseXAttribute = svgUseAttributes.getNamedItem("x");
			Node svgUseYAttribute = svgUseAttributes.getNamedItem("y");

			if (svgUseXAttribute != null && svgUseYAttribute != null && svgGroup.getAttribute("transform") == null) {
				float x = SvgNamespace.getX(svgUseElement);
				float y = SvgNamespace.getY(svgUseElement);
				svgGroup.setAttribute("transform", "translate(" + x + "," + y + ")");
			}

			// for now, we only override the style if it is not null and it is not "display:inline" (default style in inkscape)
			// it should process both styles

			Attr attributeNode = clonedSourceElement.getAttributeNode(SvgNamespace.attributeId);
			attributeNode.setValue(SvgNamespace.getId(svgUseElement) + "-instance");

			Attr attributeNode2 = sourceElement.getAttributeNode(SvgNamespace.attributeId);
			sourceElement.setIdAttributeNode(attributeNode2, true);

			svgGroup.appendChild(clonedSourceElement);

			Node parentNode = svgUseElement.getParentNode();
			parentNode.appendChild(svgGroup);

			return false;
		} else {
			throw new RuntimeException("Not implemented, xlink:href=" + sourceElementId);
		}

	}

}