package com.gemserk.commons.gdx;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	
	@Test
	public void shouldNotCallRenderIfDisposed() {
		MockGameState mockGameState = new MockGameState();
		mockGameState.initialized = false;
		
		GameStateDelegateWithInternalStateImpl gameState = new GameStateDelegateWithInternalStateImpl(mockGameState);
		gameState.init();
		gameState.show();
		gameState.render();
		assertTrue(mockGameState.renderCalled);
		
		mockGameState.renderCalled = false;
		gameState.dispose();
		gameState.render();
		
		assertFalse(mockGameState.renderCalled);
	}

}
