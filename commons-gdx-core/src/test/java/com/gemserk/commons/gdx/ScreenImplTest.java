package com.gemserk.commons.gdx;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class ScreenImplTest {

	@Test
	public void shouldNotCallRenderWhenScreenNotVisible() {
		MockGameState gameState = new MockGameState();
		ScreenImpl screen = new ScreenImpl(gameState);
		screen.hide();
		screen.render();
		assertThat(gameState.renderCalled, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldCallRenderWhenScreenVisible() {
		MockGameState gameState = new MockGameState();
		ScreenImpl screen = new ScreenImpl(gameState);
		screen.show();
		screen.render();
		assertThat(gameState.renderCalled, IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldNotCallUpdateWhenScreenPaused() {
		MockGameState gameState = new MockGameState();
		ScreenImpl screen = new ScreenImpl(gameState);
		screen.pause();
		screen.update();
		assertThat(gameState.updateCalled, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldCallUpdateWhenScreenNotPaused() {
		MockGameState gameState = new MockGameState();
		ScreenImpl screen = new ScreenImpl(gameState);
		screen.resume();
		screen.update();
		assertThat(gameState.updateCalled, IsEqual.equalTo(true));
	}

}
