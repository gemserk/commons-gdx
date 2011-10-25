package com.gemserk.commons.signals.reflection;

import java.lang.reflect.Method;

import com.gemserk.commons.signals.Signal;
import com.gemserk.commons.signals.SignalHandler;

public class InvokeMethodSignalHandler implements SignalHandler {

	Object owner;
	Method method;
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public void setOwner(Object owner) {
		this.owner = owner;
	}

	public InvokeMethodSignalHandler(Object instance, Method method) {
		this.owner = instance;
		this.method = method;
	}

	@Override
	public void onSignal(Signal signal, Object source) {
		try {
			method.invoke(owner, signal, source);
		} catch (Exception e) {
			throw new RuntimeException("Failed to invoke method " + method.getName() + " on onSignal(" + signal.getName() + ")", e);
		}
	}

}
