package com.gemserk.commons.gdx;


public class GameTransitions {

	public static class TransitionHandler {
		public void onBegin() {
		}

		public void onEnd() {
		}
	}

	public static interface InternalScreenTransition {

		/**
		 * Called when the transition begins.
		 */
		void init();

		/**
		 * Called when the transition ends.
		 */
		void dispose();

		/**
		 * Called before the screen.render() was called.
		 * 
		 * @param delta
		 */
		void preRender(float delta);

		/**
		 * Called after the screen.render() was called.
		 * 
		 * @param delta
		 *            the delta time in ms
		 */
		void postRender(float delta);

		void update(float delta);

		/**
		 * Returns true if the transition ended, false otherwise.
		 */
		boolean isFinished();

		Screen getScreen();

	}

	private static abstract class InternalScreenTransitionImpl implements InternalScreenTransition {

		private final Screen screen;
		private final TransitionHandler transitionHandler;

		private boolean finished;
		private boolean started;
		
		private float totalTime;

		@Override
		public void init() {
			started = true;
		}

		public Screen getScreen() {
			return screen;
		}

		protected TransitionHandler getTransitionHandler() {
			return transitionHandler;
		}

		public InternalScreenTransitionImpl(Screen screen, float time) {
			this(screen, time, new TransitionHandler());
		}

		public InternalScreenTransitionImpl(Screen screen, float time, TransitionHandler transitionHandler) {
			this.screen = screen;
			this.transitionHandler = transitionHandler;
			this.totalTime = time;
		}

		public void preRender(float delta) {

		}

		public void postRender(float delta) {

		}

		public void update(float delta) {
			if (!started)
				return;
			internalUpdate(delta);
		}

		protected void internalUpdate(float delta) {
			if (isFinished())
				return;
			totalTime -= delta;
			finished = totalTime <= 0f;
			getScreen().update();
		}

		public boolean isFinished() {
			return finished;
		}

	}

	public static class LeaveTransition extends InternalScreenTransitionImpl {

		public LeaveTransition(Screen screen, float time) {
			super(screen, time);
		}

		public LeaveTransition(Screen screen, float time, TransitionHandler transitionHandler) {
			super(screen, time, transitionHandler);
		}

		public void init() {
			super.init();
			getScreen().init();
			getScreen().show();
			// getScreen().pause();
			getTransitionHandler().onBegin();
		}

		public void dispose() {
			getScreen().pause();
			getScreen().hide();
			getTransitionHandler().onEnd();
		}

	}

	public static class EnterTransition extends InternalScreenTransitionImpl {

		public EnterTransition(Screen screen, float time) {
			super(screen, time);
		}

		public EnterTransition(Screen screen, float time, TransitionHandler transitionHandler) {
			super(screen, time, transitionHandler);
		}

		public void init() {
			super.init();
			getScreen().init();
			getScreen().show();
			getScreen().resume();
			// getScreen().pause();
			getTransitionHandler().onBegin();
		}

		public void dispose() {
			// getScreen().resume();
			getTransitionHandler().onEnd();
		}

	}

	public static class ScreenTransition {

		private GameTransitions.InternalScreenTransition currentTransition;

		private GameTransitions.InternalScreenTransition leaveTransition;

		private GameTransitions.InternalScreenTransition enterTransition;

		public boolean isFinished() {
			return enterTransition.isFinished();
		}

		public Screen getCurrentScreen() {
			return currentTransition.getScreen();
		}

		public GameTransitions.InternalScreenTransition getCurrentTransition() {
			return currentTransition;
		}

		public ScreenTransition(Screen start, Screen end, float timeStart, float timeEnd) {
			this(new LeaveTransition(start, timeStart), new EnterTransition(end, timeEnd));
		}

		public ScreenTransition(InternalScreenTransition leave, InternalScreenTransition enter) {
			this.leaveTransition = leave;
			this.enterTransition = enter;
			currentTransition = leaveTransition;
		}

		public void start() {
			leaveTransition.init();
		}

		public void update(float delta) {
			if (isFinished())
				return;
			updateEnterTransition(delta);
			updateLeaveTransition(delta);
		}

		private void updateLeaveTransition(float delta) {
			if (leaveTransition.isFinished())
				return;

			leaveTransition.update(delta);
			if (leaveTransition.isFinished()) {
				currentTransition = enterTransition;
				leaveTransition.dispose();
				enterTransition.init();
			}
		}

		private void updateEnterTransition(float delta) {
			if (!leaveTransition.isFinished())
				return;

			if (enterTransition.isFinished())
				return;

			enterTransition.update(delta);
			if (enterTransition.isFinished())
				enterTransition.dispose();
		}

	}

	public static class TransitionScreen extends ScreenImpl {

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
		public void update() {
			super.update();
			screenTransition.update(getDelta());
		}

		@Override
		public void render() {
			super.render();
			InternalScreenTransition transition = screenTransition.getCurrentTransition();
			transition.preRender(getDelta());
			screenTransition.getCurrentScreen().render();
			transition.postRender(getDelta());
		}

	}

}