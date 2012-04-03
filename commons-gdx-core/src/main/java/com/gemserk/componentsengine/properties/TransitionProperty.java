package com.gemserk.componentsengine.properties;

import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.componentsengine.properties.Property;

public class TransitionProperty<T> implements Property<T> {

	Transition<T> transition;

	public TransitionProperty(Transition<T> transition) {
		this.transition = transition;
	}

	@Override
	public T get() {
		return transition.get();
	}

	@Override
	public void set(T value) {
		transition.start(value);
	}

}