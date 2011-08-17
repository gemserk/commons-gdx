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

	class Event {

		String name;
		ArrayList<EventReceiver> receivers;
		int senderCount;

		EventRegistry eventRegistry;

		public Event() {
			senderCount = 0;
			receivers = new ArrayList<EventReceiver>();
		}

	}

	class SenderEventHandle {

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

	class ReceiverEventHandle {

		Event event;
		EventReceiver eventReceiver;

		public ReceiverEventHandle() {
			event = null;
			eventReceiver = null;
		}

	}

	interface EventReceiver {

		void receiveEvent(Event e, Object source);

	}

	class EventRegistry {

		private Pool<Event> eventPool = new Pool<EventSystemTest.Event>(new PoolObjectFactory<Event>() {
			@Override
			public Event createObject() {
				return new Event();
			}
		}, 32);

		private Map<String, Event> events = new HashMap<String, Event>();

		SenderEventHandle register(String eventName) {

			// unregister(senderEventHandle);

			Event event = getEvent(eventName);
			event.name = eventName;
			event.eventRegistry = this;
			event.senderCount++;

			SenderEventHandle senderEventHandle = new SenderEventHandle();
			senderEventHandle.event = event;

			return senderEventHandle;
		}

		private Event getEvent(String name) {
			if (!events.containsKey(name))
				events.put(name, eventPool.newObject());
			return events.get(name);
		}

		ReceiverEventHandle subscribe(String eventName, EventReceiver eventReceiver) {

			// unsubscribe(receiverEventHandle, eventReceiver);

			Event event = getEvent(eventName);
			event.name = eventName;
			event.receivers.add(eventReceiver);
			event.eventRegistry = this;

			ReceiverEventHandle receiverEventHandle = new ReceiverEventHandle();
			receiverEventHandle.event = event;
			receiverEventHandle.eventReceiver = eventReceiver;

			return receiverEventHandle;

		}

		void unregister(SenderEventHandle senderEventHandle) {

			Event event = senderEventHandle.event;
			if (event == null)
				return;

			event.senderCount--;

			if (event.senderCount == 0) {
				events.remove(event.name);
				eventPool.free(event);
			}

			senderEventHandle.event = null;
		}

		void unsubscribe(ReceiverEventHandle receiverEventHandle) {

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

		}

	}

	@Test
	public void sendEventsTest() {

		EventRegistry eventRegistry = new EventRegistry();

		SenderEventHandle senderEventHandle = eventRegistry.register("event1");

		EventReceiver myEventReceiver = new EventReceiver() {
			@Override
			public void receiveEvent(Event e, Object source) {
				System.out.println("event1 " + source.toString());
			}
		};

		ReceiverEventHandle receiverEventHandle = eventRegistry.subscribe("event1", myEventReceiver);

		senderEventHandle.sendMessage("a");
		senderEventHandle.sendMessage("b");

		eventRegistry.unsubscribe(receiverEventHandle);

		senderEventHandle.sendMessage("c");

		receiverEventHandle = eventRegistry.subscribe("event1", myEventReceiver);

		senderEventHandle.sendMessage("d");

		eventRegistry.unregister(senderEventHandle);

		senderEventHandle.sendMessage("e");
	}

}
