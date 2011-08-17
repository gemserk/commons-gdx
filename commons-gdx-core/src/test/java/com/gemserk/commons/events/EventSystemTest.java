package com.gemserk.commons.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;

/**
 * This test provides some classes for events system copied from the entry <a href=http://altdevblogaday.com/2011/08/17/scripting-with-no-scripts/>scripting with no scripts</a>
 * 
 * @author acoppes
 * 
 */
public class EventSystemTest {

	static class Event {

		String name;
		ArrayList<EventReceiver> receivers;
		int senderCount;

		EventRegistry eventRegistryImpl;

		public Event() {
			senderCount = 0;
			receivers = new ArrayList<EventReceiver>();
		}

	}

	static class SenderEventHandle {

		Event event;

		public SenderEventHandle() {
			event = null;
		}

		void sendMessage(Object source) {
			if (event == null)
				return;
			for (int i = 0; i < event.receivers.size(); i++)
				event.receivers.get(i).receiveEvent(event, source);
		}

	}

	static class ReceiverEventHandle {

		Event event;
		EventReceiver eventReceiver;

		public ReceiverEventHandle() {
			event = null;
			eventReceiver = null;
		}

	}

	static interface EventReceiver {

		void receiveEvent(Event e, Object source);

	}

	static interface EventRegistry {

		void unsubscribe(ReceiverEventHandle receiverEventHandle);

		void unregister(SenderEventHandle senderEventHandle);

		ReceiverEventHandle subscribe(String eventName, EventReceiver eventReceiver);

		SenderEventHandle register(String eventName);

	}

	static class EventRegistryImpl implements EventRegistry {

		private Pool<Event> eventPool = new Pool<Event>(new PoolObjectFactory<Event>() {
			@Override
			public Event createObject() {
				return new Event();
			}
		}, 32);

		private Pool<SenderEventHandle> senderEventHandlePool = new Pool<SenderEventHandle>(new PoolObjectFactory<SenderEventHandle>() {
			@Override
			public SenderEventHandle createObject() {
				return new SenderEventHandle();
			}
		}, 32);

		private Pool<ReceiverEventHandle> receiverEventHandlePool = new Pool<ReceiverEventHandle>(new PoolObjectFactory<ReceiverEventHandle>() {
			@Override
			public ReceiverEventHandle createObject() {
				return new ReceiverEventHandle();
			}
		}, 32);

		private Map<String, Event> events = new HashMap<String, Event>();

		private Event getEvent(String name) {
			if (!events.containsKey(name))
				events.put(name, eventPool.newObject());
			return events.get(name);
		}

		@Override
		public SenderEventHandle register(String eventName) {
			Event event = getEvent(eventName);
			event.name = eventName;
			event.eventRegistryImpl = this;
			event.senderCount++;

			SenderEventHandle senderEventHandle = senderEventHandlePool.newObject();
			senderEventHandle.event = event;

			return senderEventHandle;
		}

		@Override
		public ReceiverEventHandle subscribe(String eventName, EventReceiver eventReceiver) {
			Event event = getEvent(eventName);
			event.name = eventName;
			event.receivers.add(eventReceiver);
			event.eventRegistryImpl = this;

			ReceiverEventHandle receiverEventHandle = receiverEventHandlePool.newObject();
			receiverEventHandle.event = event;
			receiverEventHandle.eventReceiver = eventReceiver;

			return receiverEventHandle;
		}

		@Override
		public void unregister(SenderEventHandle senderEventHandle) {
			Event event = senderEventHandle.event;
			if (event == null)
				return;

			event.senderCount--;

			if (event.senderCount == 0) {
				events.remove(event.name);
				eventPool.free(event);
			}

			senderEventHandle.event = null;

			senderEventHandlePool.free(senderEventHandle);
		}

		@Override
		public void unsubscribe(ReceiverEventHandle receiverEventHandle) {
			Event event = receiverEventHandle.event;
			if (event == null)
				return;

			event.receivers.remove(receiverEventHandle.eventReceiver);

			if (event.receivers.isEmpty() && event.senderCount == 0) {
				events.remove(event.name);
				eventPool.free(event);
			}

			receiverEventHandle.event = null;
			receiverEventHandle.eventReceiver = null;

			receiverEventHandlePool.free(receiverEventHandle);
		}

	}

	@Test
	public void sendEventsTest() {

		EventRegistry eventRegistryImpl = new EventRegistryImpl();

		SenderEventHandle senderEventHandle = eventRegistryImpl.register("event1");

		EventReceiver myEventReceiver = new EventReceiver() {
			@Override
			public void receiveEvent(Event e, Object source) {
				System.out.println("event1 " + source.toString());
			}
		};

		ReceiverEventHandle receiverEventHandle = eventRegistryImpl.subscribe("event1", myEventReceiver);

		senderEventHandle.sendMessage("a");
		senderEventHandle.sendMessage("b");

		eventRegistryImpl.unsubscribe(receiverEventHandle);

		senderEventHandle.sendMessage("c");

		receiverEventHandle = eventRegistryImpl.subscribe("event1", myEventReceiver);

		senderEventHandle.sendMessage("d");

		eventRegistryImpl.unregister(senderEventHandle);

		senderEventHandle.sendMessage("e");
	}

}
