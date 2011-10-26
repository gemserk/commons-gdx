package com.gemserk.commons.signals;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.commons.signals.pollable.PollableSignalHandler;

/**
 * This test provides some classes for events system copied from the article <a href=http://altdevblogaday.com/2011/08/17/scripting-with-no-scripts/>scripting with no scripts</a>
 * 
 * To avoid problems with the other event system I changed the concept Event to Signal.
 * 
 */
public class SignalRegistryImplTest {

	class MySignalHandler implements SignalHandler {

		boolean wasCalled = false;

		@Override
		public void onSignal(Signal e, Object source) {
			wasCalled = true;
		}
	}

	@Test
	public void shouldCallHandlerIfRegisteredAndSubscribed() {
		SignalRegistry signalRegistry = new SignalRegistryImpl();

		SignalSender signalSender = signalRegistry.register("event1");

		MySignalHandler mySignalHandler = new MySignalHandler();

		signalRegistry.subscribe("event1", mySignalHandler);

		signalSender.signal("a");
		assertThat(mySignalHandler.wasCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotCallHandlerIfSubscribedAndUnsubscribed() {
		SignalRegistry signalRegistry = new SignalRegistryImpl();

		SignalSender signalSender = signalRegistry.register("event1");

		MySignalHandler mySignalHandler = new MySignalHandler();

		SignalReceiver signalReceiver = signalRegistry.subscribe("event1", mySignalHandler);
		signalRegistry.unsubscribe(signalReceiver);

		signalSender.signal("a");
		assertThat(mySignalHandler.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotCallHandlerIfSignalSenderUnregistered() {
		SignalRegistry signalRegistry = new SignalRegistryImpl();

		SignalSender signalSender = signalRegistry.register("event1");

		MySignalHandler mySignalHandler = new MySignalHandler();

		signalRegistry.subscribe("event1", mySignalHandler);

		signalRegistry.unregister(signalSender);

		signalSender.signal("a");
		assertThat(mySignalHandler.wasCalled, IsEqual.equalTo(false));
	}

	@Test
	public void testPollableSignalHandlerOnlyOneSignal() {
		SignalRegistry signalRegistry = new SignalRegistryImpl();

		SignalSender signalSender = signalRegistry.register("event1");

		PollableSignalHandler pollableSignalHandler = new PollableSignalHandler();

		signalRegistry.subscribe("event1", pollableSignalHandler);

		assertThat(pollableSignalHandler.signalSent(), IsEqual.equalTo(false));
		signalSender.signal("source");
		assertThat(pollableSignalHandler.signalSent(), IsEqual.equalTo(true));
		assertThat(pollableSignalHandler.signalSent(), IsEqual.equalTo(false));
	}

	// @Test
	// public void testPollableSignalHandlerMoreThanOneSignal() {
	// SignalRegistry signalRegistry = new SignalRegistryImpl();
	//
	// SignalSender signalSender = signalRegistry.register("event1");
	//
	// PollableSignalHandler pollableSignalHandler = new PollableSignalHandler();
	//
	// signalRegistry.subscribe("event1", pollableSignalHandler);
	//
	// assertThat(pollableSignalHandler.signalSent(), IsEqual.equalTo(false));
	// signalSender.signal("source");
	// signalSender.signal("source");
	// assertThat(pollableSignalHandler.signalSent(), IsEqual.equalTo(true));
	// assertThat(pollableSignalHandler.signalSent(), IsEqual.equalTo(true));
	// assertThat(pollableSignalHandler.signalSent(), IsEqual.equalTo(false));
	// }

}
