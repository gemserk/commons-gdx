package com.gemserk.componentsengine.input;

import static org.junit.Assert.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;


public class ButtonMonitorTest {

	static class ButtonMonitorMock extends ButtonMonitor {

		public boolean down = false;

		@Override
		protected boolean isDown() {
			return down;
		}

		public String toString() {
			@SuppressWarnings("serial")
			Map<String, Boolean> actual = new LinkedHashMap<String, Boolean>() {
				{
					put("pressed", pressed);
					put("released", released);
					put("holded", holded);
				}
			};

			return actual.toString();
		}

	}

	ButtonMonitorMock monitor;

	@Before
	public void setUp() {
		monitor = new ButtonMonitorMock();
	}

	@Test
	public void nothingEverPressed() {
		monitor.down = false;
		monitor.update();
		assertThat(monitor, MonitorMatcher.monitor(false, false, false));
	}

	@Test
	public void whenFirstPresed() {
		monitor.down = false;
		monitor.update();
		monitor.down = true;
		monitor.update();
		assertThat(monitor, MonitorMatcher.monitor(true, false, false));
	}

	@Test
	public void holding() {
		monitor.down = false;
		monitor.update();
		monitor.down = true;
		monitor.update();
		monitor.down = true;
		monitor.update();
		assertThat(monitor, MonitorMatcher.monitor(false, false, true));
	}

	@Test
	public void released() {
		monitor.down = false;
		monitor.update();
		monitor.down = true;
		monitor.update();
		monitor.down = true;
		monitor.update();
		monitor.down = false;
		monitor.update();
		assertThat(monitor, MonitorMatcher.monitor(false, true, false));
	}

	@Test
	public void keepReleased() {
		monitor.down = false;
		monitor.update();
		monitor.down = true;
		monitor.update();
		monitor.down = true;
		monitor.update();
		monitor.down = false;
		monitor.update();
		monitor.down = false;
		monitor.update();
		assertThat(monitor, MonitorMatcher.monitor(false, false, false));
	}

	@Test
	public void pressReleaseWithoutHold() {
		monitor.down = false;
		monitor.update();
		monitor.down = true;
		monitor.update();
		monitor.down = false;
		monitor.update();

		assertThat(monitor, MonitorMatcher.monitor(false, true, false));

	}

	static class MonitorMatcher extends BaseMatcher<ButtonMonitor> {

		private final boolean isPressed;
		private final boolean isReleased;
		private final boolean isHolded;

		static Matcher<ButtonMonitor> monitor(boolean isPressed, boolean isReleased, boolean isHolded) {
			return new MonitorMatcher(isPressed, isReleased, isHolded);
		}

		public MonitorMatcher(boolean isPressed, boolean isReleased, boolean isHolded) {
			this.isPressed = isPressed;
			this.isReleased = isReleased;
			this.isHolded = isHolded;

		}

		@Override
		public boolean matches(Object arg0) {
			if (arg0 instanceof ButtonMonitor) {
				ButtonMonitor monitor = (ButtonMonitor) arg0;
				return !(isPressed != monitor.isPressed() || isReleased != monitor.isReleased() || isHolded != monitor.isHolded());
			} else {
				return false;
			}
		}

		@Override
		public void describeTo(Description arg0) {
			@SuppressWarnings("serial")
			Map<String, Boolean> expected = new LinkedHashMap<String, Boolean>() {
				{
					put("pressed", isPressed);
					put("released", isReleased);
					put("holded", isHolded);
				}
			};

			arg0.appendText(expected.toString());

		}

	}

}
