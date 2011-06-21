package com.gemserk.commons.gdx;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.componentsengine.utils.timers.CountDownTimer;
import com.gemserk.componentsengine.utils.timers.Timer;

public class TransitionScreenTest {
	
	static class TransitionHandler {
		
		void onBegin() {}

		void onEnd() {}

	}

	static interface InternalScreenTransition {

		/**
		 * Called when the transition begins.
		 */
		void begin();

		/**
		 * Called when the transition ends.
		 */
		void end();
		
		/**
		 * Called before the screen.render() was called.
		 * @param delta
		 */
		void preRender(int delta);

		/**
		 * Called after the screen.render() was called.
		 * @param delta the delta time in ms
		 */
		void postRender(int delta);
		
		void update(int delta);

		/**
		 * Returns true if the transition ended, false otherwise.
		 */
		boolean isFinished();

		Screen getScreen();

	}
	
	static abstract class InternalScreenTransitionImpl implements InternalScreenTransition {
		
		private final Screen screen;

		private final TransitionHandler transitionHandler;

		private Timer timer;

		public Screen getScreen() {
			return screen;
		}
		
		protected TransitionHandler getTransitionHandler() {
			return transitionHandler;
		}

		public InternalScreenTransitionImpl(Screen screen, int time) {
			this(screen, time, new TransitionHandler());
		}
		
		public InternalScreenTransitionImpl(Screen screen, int time, TransitionHandler transitionHandler) {
			this.screen = screen;
			this.transitionHandler = transitionHandler;
			this.timer = new CountDownTimer(time, true);
		}
		
		public void preRender(int delta) {
			
		}

		public void postRender(int delta) {
			
		}

		public void update(int delta) {
			timer.update(delta);
		}

		public boolean isFinished() {
			return !timer.isRunning();
		}
		
	}

	static class LeaveTransition extends InternalScreenTransitionImpl {

		public LeaveTransition(Screen screen, int time) {
			super(screen, time);
		}
		
		public LeaveTransition(Screen screen, int time, TransitionHandler transitionHandler) {
			super(screen, time, transitionHandler);
		}

		public void begin() {
			getScreen().init();
			getScreen().show();
			getScreen().pause();
			getTransitionHandler().onBegin();
		}

		public void end() {
			getScreen().hide();
			getTransitionHandler().onEnd();
		}

	}

	static class EnterTransition extends InternalScreenTransitionImpl {

		public EnterTransition(Screen screen, int time) {
			super(screen, time);
		}

		public EnterTransition(Screen screen, int time, TransitionHandler transitionHandler) {
			super(screen, time, transitionHandler);
		}

		public void begin() {
			getScreen().init();
			getScreen().show();
			getScreen().pause();
			getTransitionHandler().onBegin();
		}

		public void end() {
			getScreen().resume();
			getTransitionHandler().onEnd();
		}

	}

	static class ScreenTransition {
		
		private InternalScreenTransition currentTransition;

		private InternalScreenTransition leaveTransition;

		private InternalScreenTransition enterTransition;
		
		public boolean isFinished() {
			return enterTransition.isFinished();
		}

		public Screen getCurrentScreen() {
			return currentTransition.getScreen();
		}
		
		public InternalScreenTransition getCurrentTransition() {
			return currentTransition;
		}

		public ScreenTransition(Screen start, Screen end, int timeStart, int timeEnd) {
			leaveTransition = new LeaveTransition(start, timeStart);
			enterTransition = new EnterTransition(end, timeEnd);
			currentTransition = leaveTransition;
		}

		public void start() {
			leaveTransition.begin();
		}

		public void update(int delta) {
			if (isFinished())
				return;
			updateLeaveTransition(delta);
			updateEnterTransition(delta);
		}

		private void updateEnterTransition(int delta) {
			if (!leaveTransition.isFinished())
				return;
			
			if (enterTransition.isFinished())
				return;
			
			enterTransition.update(delta);
			if (enterTransition.isFinished()) 
				enterTransition.end();
		}

		private void updateLeaveTransition(int delta) {
			if (leaveTransition.isFinished())
				return;

			leaveTransition.update(delta);
			if (leaveTransition.isFinished()) {
				currentTransition = enterTransition;
				leaveTransition.end();
				enterTransition.begin();
			}
		}

	}
	
	static class TransitionScreen extends ScreenImpl {

		protected final ScreenTransition screenTransition;

		public TransitionScreen(ScreenTransition screenTransition) {
			super(new GameStateImpl());
			this.screenTransition = screenTransition;
		}

		@Override
		public void init() {
			super.init();
			screenTransition.start();
		}

		@Override
		public void update(int delta) {
			super.update(delta);
			screenTransition.update(delta);
		}

		@Override
		public void render(int delta) {
			super.render(delta);
			
			InternalScreenTransition transition = screenTransition.getCurrentTransition();
			
			transition.preRender(delta);
			screenTransition.getCurrentScreen().render(delta);
			transition.postRender(delta);
		}

	}

	@Test
	public void shouldInitFirstScreenOnStart() {
		MockScreen screenA = new MockScreen();
		MockScreen screenB = new MockScreen();

		ScreenTransition screenTransition = new ScreenTransition(screenA, screenB, 1000, 500);

		screenTransition.start();

		assertThat(screenA.initCalled, IsEqual.equalTo(true));
		assertThat(screenA.showCalled, IsEqual.equalTo(true));
		assertThat(screenA.pauseCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldDoNothingIfTimeNotFinishedScreensOnUpdate() {
		MockScreen screenA = new MockScreen();
		MockScreen screenB = new MockScreen();

		ScreenTransition screenTransition = new ScreenTransition(screenA, screenB, 200, 500);

		screenTransition.update(50);

		assertThat(screenA.hideCalled, IsEqual.equalTo(false));
		assertThat(screenB.initCalled, IsEqual.equalTo(false));

		assertThat(screenTransition.getCurrentScreen(), IsSame.sameInstance((Screen) screenA));
		assertThat(screenTransition.isFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldSwitchScreensOnUpdate() {
		MockScreen screenA = new MockScreen();
		MockScreen screenB = new MockScreen();

		ScreenTransition screenTransition = new ScreenTransition(screenA, screenB, 200, 500);

		screenTransition.update(210);

		assertThat(screenA.hideCalled, IsEqual.equalTo(true));

		assertThat(screenB.initCalled, IsEqual.equalTo(true));
		assertThat(screenB.showCalled, IsEqual.equalTo(true));
		assertThat(screenB.pauseCalled, IsEqual.equalTo(true));

		assertThat(screenTransition.getCurrentScreen(), IsSame.sameInstance((Screen) screenB));
		assertThat(screenTransition.isFinished(), IsEqual.equalTo(false));
	}

	/*
	@Test
	public void shouldResumeEndScreenOnTimeFinished() {
		MockScreen screenA = new MockScreen();
		MockScreen screenB = new MockScreen();

		ScreenTransition screenTransition = new ScreenTransition(screenA, screenB, 200, 500);

		screenTransition.update(200);
		screenTransition.update(500);

		assertThat(screenB.resumeCalled, IsEqual.equalTo(true));

		assertThat(screenTransition.getCurrentScreen(), IsSame.sameInstance((Screen) screenB));
		assertThat(screenTransition.isFinished(), IsEqual.equalTo(true));
	}
	*/

}
