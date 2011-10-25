package com.gemserk.commons.artemis.events.reflection;

import java.lang.reflect.Method;

import com.gemserk.commons.artemis.events.Event;
import com.gemserk.commons.artemis.events.EventListener;

public class InvokeMethodEventListener extends EventListener {

	protected Object owner;
	protected Method method;

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setOwner(Object owner) {
		this.owner = owner;
	}

	@Override
	public void onEvent(Event event) {
		try {
			method.invoke(owner, event);
		} catch (Exception e) {
			throw new RuntimeException("Failed to invoke method " + method.getName() + " for event " + event.getId(), e);
		}
	}

}