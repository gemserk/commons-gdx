package com.gemserk.commons.signals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;

/**
 * This test provides some classes for events system copied from the entry <a href=http://altdevblogaday.com/2011/08/17/scripting-with-no-scripts/>scripting with no scripts</a>
 * 
 * To avoid problems with the other event system I changed the concept Event to Signal.
 * 
 * @author acoppes
 * 
 */
public class SignalSystemTest {

	static class Signal {

		String name;
		ArrayList<SignalHandler> receivers;
		int senderCount;

		SignalRegistry eventRegistryImpl;

		public Signal() {
			senderCount = 0;
			receivers = new ArrayList<SignalHandler>();
		}

	}

	static class SignalSender {

		Signal signal;

		public SignalSender() {
			signal = null;
		}

		void signal(Object source) {
			if (signal == null)
				return;
			for (int i = 0; i < signal.receivers.size(); i++)
				signal.receivers.get(i).onSignal(signal, source);
		}

	}

	static class SignalReceiver {

		Signal signal;
		SignalHandler signalHandler;

		public SignalReceiver() {
			signal = null;
			signalHandler = null;
		}

	}

	static interface SignalHandler {

		void onSignal(Signal e, Object source);

	}

	/**
	 * Provides a way to register and unregister SignalSenders and SignalReceivers for Signals.
	 */
	static interface SignalRegistry {

		/**
		 * Registers a SignalSender to send signals for a Signal identified by the given name.
		 * 
		 * @param signalName
		 *            The name of the Signal.
		 */
		SignalSender register(String signalName);

		/**
		 * Unregisters a SignalSender from a Signal to avoid sending new Signals.
		 * 
		 * @param signalSender
		 *            The SignalSender to unregister.
		 */
		void unregister(SignalSender signalSender);

		/**
		 * Subscribes a new SignalHandler to handle signals of the Signal identified by the given name.
		 * 
		 * @param signalName
		 *            The name of the Signal.
		 * @param signalHandler
		 *            The SignalHandler to be registered to handle signals.
		 */
		SignalReceiver subscribe(String signalName, SignalHandler signalHandler);

		/**
		 * Un subscribes the Signal
		 * 
		 * @param signalReceiver
		 */
		void unsubscribe(SignalReceiver signalReceiver);

	}

	static class SignalRegistryImpl implements SignalRegistry {

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
			signal.eventRegistryImpl = this;
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
			signal.eventRegistryImpl = this;

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

	@Test
	public void sendEventsTest() {

		SignalRegistry eventRegistryImpl = new SignalRegistryImpl();

		SignalSender signalSender = eventRegistryImpl.register("event1");

		SignalHandler myEventReceiver = new SignalHandler() {
			@Override
			public void onSignal(Signal e, Object source) {
				System.out.println("event1 " + source.toString());
			}
		};

		SignalReceiver signalReceiver = eventRegistryImpl.subscribe("event1", myEventReceiver);

		signalSender.signal("a");
		signalSender.signal("b");

		eventRegistryImpl.unsubscribe(signalReceiver);

		signalSender.signal("c");

		signalReceiver = eventRegistryImpl.subscribe("event1", myEventReceiver);

		signalSender.signal("d");

		eventRegistryImpl.unregister(signalSender);

		signalSender.signal("e");
	}

}
