package com.gemserk.commons.svg.processors;

import java.util.Stack;

import org.w3c.dom.Element;

import com.gemserk.commons.svg.inkscape.GemserkNamespace;
import com.gemserk.commons.svg.inkscape.SvgNamespace;
import com.gemserk.commons.svg.inkscape.SvgTransformUtils;
import com.gemserk.vecmath.Matrix3f;

public class SvgTransformProcessor extends SvgElementProcessor {

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

		element.setAttributeNS(GemserkNamespace.namespace, GemserkNamespace.attributeAbsoluteTransform, SvgTransformUtils.serializeTransform(transform));
		return true;
	}

}