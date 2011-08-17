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

		// Event();
		// std::string mName;
		// std::vector< MessageReceiver* > mReceivers;
		// uint32 mSenderCount;
		//
		// // to be able to unregister yourself
		// EventRegistry* mReg;

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

		// void SendMessage( Object* origin );

		// friend EventRegistry;
		// Event* mEvent;

		Event event;

		public SenderEventHandle() {
			event = null;
		}

		void sendMessage(Object source) {

			// if ( NULL != mEvent )
			// {
			// std::vector<MessageReceiver*>& rv = mEvent->mReceivers;
			// int cnt = rv.size();
			// for ( int i = 0 ; i != cnt ; ++i )
			// {
			// rv[i]->ReceiveMessage( mEvent, origin );
			// }
			// }

			if (event == null)
				return;

			for (int i = 0; i < event.receivers.size(); i++)
				event.receivers.get(i).receiveEvent(event, source);

		}

	}

	class ReceiverEventHandle {

		// friend EventRegistry;
		// Event* mEvent;
		// // to be able to auto-unregister
		// MessageReceiver* mReceiver;

		Event event;
		EventReceiver eventReceiver;

		public ReceiverEventHandle() {
			event = null;
			eventReceiver = null;
		}

	}

	interface EventReceiver {

		// virtual void ReceiveMessage( Event* e, Object* origin ) = 0;

		void receiveEvent(Event e, Object source);

	}

	class EventRegistry {

		Map<String, Event> events = new HashMap<String, Event>();

		void register(String eventName, SenderEventHandle senderEventHandle) {

			// // 1. Unreg
			// Unregister( eh );
			//
			// // 2. Re-reg
			// Event& e = mEvents[ eventName ];
			// e.mName = eventName; // if added, make sure name is set
			// e.mReg = this;
			// ++(e.mSenderCount);
			//
			// eh->mEvent = &e;

			unregister(senderEventHandle);

			Event event = getEvent(eventName);
			event.name = eventName;
			event.eventRegistry = this;
			event.senderCount++;

			senderEventHandle.event = event;
		}

		private Event getEvent(String name) {
			if (!events.containsKey(name))
				events.put(name, new Event());
			return events.get(name);
		}

		void subscribe(String eventName, ReceiverEventHandle receiverEventHandle, EventReceiver eventReceiver) {

			// // 1. Unsub
			// Unsubscribe( eh, receiver );
			//
			// // 2. Re-reg
			// Event& e = mEvents[ eventName ];
			// e.mName = eventName; // if added, make sure name is set
			// e.mReceivers.push_back( receiver );
			// e.mReg = this;
			//
			// eh->mEvent = &e;
			// eh->mReceiver = receiver;

			unsubscribe(receiverEventHandle, eventReceiver);

			Event event = getEvent(eventName);
			event.name = eventName;
			event.receivers.add(eventReceiver);
			event.eventRegistry = this;

			receiverEventHandle.event = event;
			receiverEventHandle.eventReceiver = eventReceiver;

		}

		void unregister(SenderEventHandle senderEventHandle) {

			// Event* e = eh->mEvent;
			// if ( NULL!= e )
			// {
			// std::map< std::string, Event >::iterator it = mEvents.find( e->mName );
			// --(e->mSenderCount);
			// ASSERT( 0 <= e->mSenderCount ); // non-negative
			// if ( 0 == e->mSenderCount && e->mReceivers.empty() )
			// {
			// mEvents.erase( it );
			// }
			// eh->mEvent = NULL;
			// }

			Event e = senderEventHandle.event;
			if (e == null)
				return;

			e.senderCount--;

			if (e.senderCount == 0)
				events.remove(e.name);

			senderEventHandle.event = null;
		}

		void unsubscribe(ReceiverEventHandle receiverEventHandle, EventReceiver eventReceiver) {

			// Event* e = eh->mEvent;
			// if ( NULL!= e )
			// {
			// std::map< std::string, Event >::iterator eit = mEvents.find( e->mName );
			// ASSERT( eit != mEvents.end() ); // found
			//
			// std::vector<int>::iterator it;
			// std::vector< MessageReceiver* >::iterator rit = std::find( e->mReceivers.begin(), e->mReceivers.end(), receiver );
			// ASSERT( rit != e->mReceivers.end() ); // existed
			//
			// if ( 0 == e->mSenderCount && e->mReceivers.empty() )
			// {
			// mEvents.erase( eit );
			// }
			//
			// eh->mEvent = NULL;
			// eh->mReceiver = NULL;
			// }

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

		SenderEventHandle senderEventHandle = new SenderEventHandle();
		ReceiverEventHandle receiverEventHandle = new ReceiverEventHandle();

		EventRegistry eventRegistry = new EventRegistry();

		eventRegistry.register("event1", senderEventHandle);
		eventRegistry.subscribe("event1", receiverEventHandle, new EventReceiver() {

			@Override
			public void receiveEvent(Event e, Object source) {
				System.out.println("event1");
			}
		});

		senderEventHandle.sendMessage(null);

	}

}
