package com.gemserk.commons.svg.processors;

import java.util.Stack;

import org.w3c.dom.Element;

import com.gemserk.commons.svg.inkscape.GemserkNamespace;
import com.gemserk.commons.svg.inkscape.SvgNamespace;
import com.gemserk.vecmath.Matrix3f;

public class SvgTransformProcessor extends SvgElementProcessor {

	protected Stack<Matrix3f> transformStack;

	final Matrix3f localTransform = new Matrix3f();

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
		Matrix3f parentTransform = transformStack.peek();

		SvgNamespace.getTransform(element, localTransform);

		Matrix3f absoluteTransform = new Matrix3f();

		absoluteTransform.set(localTransform);
		absoluteTransform.mul(parentTransform);

		// absoluteTransform.set(parentTransform);
		// absoluteTransform.mul(localTransform);

		transformStack.push(absoluteTransform);

		GemserkNamespace.setAbsoluteTransform(element, absoluteTransform);

		return true;
	}

}