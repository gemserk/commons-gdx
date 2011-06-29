package com.gemserk.commons.gdx;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

public class GameImplTest {

	@Test
	public void shouldStartWithNoScreen() {
		Game game = new Game();
		assertThat(game.getScreen(), IsNull.nullValue());
	}

	@Test
	public void shouldNotFailWhenNullScreenSet() {
		Game game = new Game();
		game.setScreen(null);
		assertThat(game.getScreen(), IsNull.nullValue());
	}

	@Test
	public void shouldCallResumeAndShowOnNewScreen() {
		MockScreen screen = new MockScreen();

		Game game = new Game();
		game.setScreen(screen);

		assertThat(game.getScreen(), IsSame.sameInstance((Screen) screen));
		assertThat(screen.pauseCalled, IsEqual.equalTo(false));
		assertThat(screen.showCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldInitScreenOnSetScreenIfNotInited() {
		MockScreen screen = new MockScreen();
		Game game = new Game();
		game.setScreen(screen);
		assertThat(screen.initCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldCallPauseAndHideToPreviousScreen() {
		MockScreen screen1 = new MockScreen();
		MockScreen screen2 = new MockScreen();

		Game game = new Game();
		game.setScreen(screen1);
		game.setScreen(screen2);

		assertThat(game.getScreen(), IsSame.sameInstance((Screen) screen2));
		assertThat(screen1.pauseCalled, IsEqual.equalTo(true));
		assertThat(screen1.hideCalled, IsEqual.equalTo(true));
		assertThat(screen2.resumeCalled, IsEqual.equalTo(true));
		assertThat(screen2.showCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldTransitionFromOneScreenToAnother() {
		MockScreen screenA = new MockScreen();
		MockScreen screenB = new MockScreen();
		
		Game game = new Game();
		game.setScreen(screenA);
		
		assertThat(screenA.initCalled, IsEqual.equalTo(true));
		
		game.setScreen(screenB, true);
		
		assertThat(screenA.pauseCalled, IsEqual.equalTo(true));
		assertThat(screenA.hideCalled, IsEqual.equalTo(true));
		assertThat(screenA.disposeCalled, IsEqual.equalTo(true));
		
		assertThat(screenB.initCalled, IsEqual.equalTo(true));
		assertThat(screenB.showCalled, IsEqual.equalTo(true));
		assertThat(screenB.resumeCalled, IsEqual.equalTo(true));
		
		assertThat(screenB, IsSame.sameInstance(game.getScreen()));
	}

}
