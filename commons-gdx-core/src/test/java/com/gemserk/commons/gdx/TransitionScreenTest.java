package com.gemserk.commons.gdx;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.commons.gdx.GameTransitions.ScreenTransition;

public class TransitionScreenTest {

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

		screenTransition.start();
		screenTransition.update(210);

		assertThat(screenA.hideCalled, IsEqual.equalTo(true));

		assertThat(screenB.initCalled, IsEqual.equalTo(true));
		assertThat(screenB.showCalled, IsEqual.equalTo(true));
		assertThat(screenB.pauseCalled, IsEqual.equalTo(true));

		assertThat(screenTransition.getCurrentScreen(), IsSame.sameInstance((Screen) screenB));
		assertThat(screenTransition.isFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldFinishWhenInternalFinish() {
		MockScreen screenA = new MockScreen();
		MockScreen screenB = new MockScreen();

		ScreenTransition screenTransition = new ScreenTransition(screenA, screenB, 200, 500);

		screenTransition.start();
		screenTransition.update(201);
		
		assertThat(screenTransition.getCurrentScreen(), IsSame.sameInstance((Screen) screenB));
		assertThat(screenTransition.isFinished(), IsEqual.equalTo(false));
		
		screenTransition.update(490);
		assertThat(screenTransition.isFinished(), IsEqual.equalTo(false));
		
		screenTransition.update(11);
		assertThat(screenTransition.isFinished(), IsEqual.equalTo(true));
	}

	/*
	 * @Test public void shouldResumeEndScreenOnTimeFinished() { MockScreen screenA = new MockScreen(); MockScreen screenB = new MockScreen();
	 * 
	 * ScreenTransition screenTransition = new ScreenTransition(screenA, screenB, 200, 500);
	 * 
	 * screenTransition.update(200); screenTransition.update(500);
	 * 
	 * assertThat(screenB.resumeCalled, IsEqual.equalTo(true));
	 * 
	 * assertThat(screenTransition.getCurrentScreen(), IsSame.sameInstance((Screen) screenB)); assertThat(screenTransition.isFinished(), IsEqual.equalTo(true)); }
	 */

}
