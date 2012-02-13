package com.gemserk.commons.svg.processors;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.gemserk.commons.svg.inkscape.GemserkNamespace;
import com.gemserk.commons.svg.inkscape.SvgNamespace;
import com.gemserk.commons.svg.inkscape.SvgTransformUtils;
import com.gemserk.vecmath.Matrix3f;

public class SvgUseProcessor extends SvgElementProcessor {

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

			// Matrix3f clonedElementTransform = GemserkNamespace.getAbsoluteTransform(clonedSourceElement);
			Matrix3f useAbsoluteTransform = GemserkNamespace.getAbsoluteTransform(element);

			clonedSourceElement.setAttribute(SvgNamespace.attributeTransform, SvgTransformUtils.serializeTransform(useAbsoluteTransform));

			// clonedElementTransform.mul(useTransform);
			// clonedSourceElement.setAttribute(GemserkNamespace.attributeAbsoluteTransform, SvgInkscapeUtils.transformToAttribute(clonedElementTransform));

			Node parentNode = element.getParentNode();
			parentNode.removeChild(element);
			parentNode.appendChild(clonedSourceElement);

			return false;
		} else {
			throw new RuntimeException("Not implemented, xlink:href=" + sourceElementId);
		}

	}

}