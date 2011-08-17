package com.gemserk.commons.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

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

		Map<String, Event> events = new HashMap<String, Event>();

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
				events.put(name, new Event());
			return events.get(name);
		}

		void subscribe(String eventName, ReceiverEventHandle receiverEventHandle, EventReceiver eventReceiver) {

			unsubscribe(receiverEventHandle, eventReceiver);

			Event event = getEvent(eventName);
			event.name = eventName;
			event.receivers.add(eventReceiver);
			event.eventRegistry = this;

			receiverEventHandle.event = event;
			receiverEventHandle.eventReceiver = eventReceiver;

		}

		void unregister(SenderEventHandle senderEventHandle) {

			Event e = senderEventHandle.event;
			if (e == null)
				return;

			e.senderCount--;

			if (e.senderCount == 0)
				events.remove(e.name);

			senderEventHandle.event = null;
		}

		void unsubscribe(ReceiverEventHandle receiverEventHandle, EventReceiver eventReceiver) {

			Event event = receiverEventHandle.event;
			if (event == null)
				return;

			event.receivers.remove(eventReceiver);

			if (event.receivers.isEmpty() && event.senderCount == 0)
				events.remove(event.name);

			// eventPool.free(event);

			receiverEventHandle.event = null;
			receiverEventHandle.eventReceiver = null;

		}

	}

	@Test
	public void sendEventsTest() {

		ReceiverEventHandle receiverEventHandle = new ReceiverEventHandle();

		EventRegistry eventRegistry = new EventRegistry();

		SenderEventHandle senderEventHandle = eventRegistry.register("event1");
		eventRegistry.subscribe("event1", receiverEventHandle, new EventReceiver() {
			@Override
			public void receiveEvent(Event e, Object source) {
				System.out.println("event1 " + source.toString());
			}
		});

		senderEventHandle.sendMessage("a");
		senderEventHandle.sendMessage("b");

	}

}
