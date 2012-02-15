package com.gemserk.commons.svg.processors;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.gemserk.commons.svg.inkscape.GemserkNamespace;
import com.gemserk.commons.svg.inkscape.SvgNamespace;
import com.gemserk.vecmath.Matrix3f;

public class SvgUseProcessor extends SvgElementProcessor {

	private final Matrix3f useAbsoluteTransform = new Matrix3f();
	private final Matrix3f sourceAbsoluteTransform = new Matrix3f();

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

			Attr attributeNode = clonedSourceElement.getAttributeNode(SvgNamespace.attributeId);
			attributeNode.setValue(SvgNamespace.getId(element) + "-instance");

			Attr attributeNode2 = sourceElement.getAttributeNode(SvgNamespace.attributeId);
			sourceElement.setIdAttributeNode(attributeNode2, true);

			// clonedSourceElement.setIdAttributeNode(attributeNode, false);
			// clonedSourceElement.removeAttributeNode(attributeNode);
			// clonedSourceElement.setAttribute(SvgNamespace.attributeId, SvgNamespace.getId(element));

			// GemserkNamespace.getAbsoluteTransform(element, useAbsoluteTransform);

			SvgNamespace.getTransform(element, useAbsoluteTransform);
			GemserkNamespace.getAbsoluteTransform(clonedSourceElement, sourceAbsoluteTransform);

			useAbsoluteTransform.mul(sourceAbsoluteTransform);

			GemserkNamespace.setAbsoluteTransform(clonedSourceElement, useAbsoluteTransform);

			// useAbsoluteTransform.setIdentity();
			// SvgNamespace.setTransform(clonedSourceElement, useAbsoluteTransform);

			Node parentNode = element.getParentNode();
			// parentNode.removeChild(element);
			parentNode.appendChild(clonedSourceElement);

			return false;
		} else {
			throw new RuntimeException("Not implemented, xlink:href=" + sourceElementId);
		}

	}

}