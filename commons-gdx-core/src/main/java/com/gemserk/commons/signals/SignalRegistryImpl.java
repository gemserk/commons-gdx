package com.gemserk.commons.signals;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;

public class SignalRegistryImpl implements SignalRegistry {

	private Pool<Signal> eventPool = new Pool<Signal>(new PoolObjectFactory<Signal>() {
		@Override
		public Signal createObject() {
			return new Signal();
		}
	}, 32);

	private Pool<SignalSender> senderEventHandlePool = new Pool<SignalSender>(new PoolObjectFactory<SignalSender>() {
		@Override
		public SignalSender createObject() {
			return new SignalSender();
		}
	}, 32);

	private Pool<SignalReceiver> receiverEventHandlePool = new Pool<SignalReceiver>(new PoolObjectFactory<SignalReceiver>() {
		@Override
		public SignalReceiver createObject() {
			return new SignalReceiver();
		}
	}, 32);

	private Map<String, Signal> signals = new HashMap<String, Signal>();

	private Signal getEvent(String name) {
		if (!signals.containsKey(name))
			signals.put(name, eventPool.newObject());
		return signals.get(name);
	}

	@Override
	public SignalSender register(String eventName) {
		Signal signal = getEvent(eventName);
		signal.name = eventName;
		signal.signalRegistry = this;
		signal.senderCount++;

		SignalSender signalSender = senderEventHandlePool.newObject();
		signalSender.signal = signal;

		return signalSender;
	}

	@Override
	public SignalReceiver subscribe(String eventName, SignalHandler signalHandler) {
		Signal signal = getEvent(eventName);
		signal.name = eventName;
		signal.receivers.add(signalHandler);
		signal.signalRegistry = this;

		SignalReceiver signalReceiver = receiverEventHandlePool.newObject();
		signalReceiver.signal = signal;
		signalReceiver.signalHandler = signalHandler;

		return signalReceiver;
	}

	@Override
	public void unregister(SignalSender signalSender) {
		Signal signal = signalSender.signal;
		if (signal == null)
			return;

		signal.senderCount--;

		if (signal.senderCount == 0) {
			signals.remove(signal.name);
			eventPool.free(signal);
		}

		signalSender.signal = null;

		senderEventHandlePool.free(signalSender);
	}

	@Override
	public void unsubscribe(SignalReceiver signalReceiver) {
		Signal signal = signalReceiver.signal;
		if (signal == null)
			return;

		signal.receivers.remove(signalReceiver.signalHandler);

		if (signal.receivers.isEmpty() && signal.senderCount == 0) {
			signals.remove(signal.name);
			eventPool.free(signal);
		}

		signalReceiver.signal = null;
		signalReceiver.signalHandler = null;

		receiverEventHandlePool.free(signalReceiver);
	}

}