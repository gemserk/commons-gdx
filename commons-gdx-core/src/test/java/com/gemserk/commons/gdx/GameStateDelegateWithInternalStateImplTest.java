package com.gemserk.commons.gdx;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class GameStateDelegateWithInternalStateImplTest {

	@Test
	public void shouldNotCallRenderIfNotInitialized() {
		MockGameState mockGameState = new MockGameState();
		mockGameState.initialized = false;
		
		GameStateDelegateWithInternalStateImpl gameState = new GameStateDelegateWithInternalStateImpl(mockGameState);
		gameState.show();
		gameState.render();
		
		assertFalse(mockGameState.renderCalled);
	}

}
