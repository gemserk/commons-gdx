package com.gemserk.commons.gdx;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.animation4j.transitions.Transition;

public class GameStateTransitionTest {

	class GameStateTransition implements Transition<GameState> {

		GameState current;

		boolean started;

		boolean finished;

		public GameStateTransition() {
			this(null);
		}

		public GameStateTransition(GameState current) {
			this.current = current;
		}

		@Override
		public GameState get() {
			return current;
		}

		@Override
		public void set(GameState t) {
			current = t;
			started = true;
			finished = true;
		}

		@Override
		public void set(GameState t, float time) {
			if (current == null || time <= 0) {
				current = t;
				started = true;
				finished = true;
			}
		}

		@Override
		public boolean isStarted() {
			return started;

		}

		@Override
		public boolean isFinished() {
			return finished;
		}
		
	}

	@Test
	public void shouldNotBeStartedWhenCreated() {
		GameStateTransition gameStateTransition = new GameStateTransition();
		assertThat(gameStateTransition.isStarted(), IsEqual.equalTo(false));
		assertThat(gameStateTransition.isFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldAllowTransitionFromNoGameStateToGameState() {
		GameState target = new MockGameState();
		GameStateTransition gameStateTransition = new GameStateTransition();
		gameStateTransition.set(target);
		assertThat(gameStateTransition.get(), IsSame.sameInstance(target));
		assertThat(gameStateTransition.isStarted(), IsEqual.equalTo(true));
		assertThat(gameStateTransition.isFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldStartTransitionOnEndValueIfCurrentStateIsNullSet() {
		GameState target = new MockGameState();
		GameStateTransition gameStateTransition = new GameStateTransition();
		assertThat(gameStateTransition.get(), IsNull.nullValue());
		gameStateTransition.set(target, 500);
		assertThat(gameStateTransition.get(), IsSame.sameInstance(target));
		assertThat(gameStateTransition.isStarted(), IsEqual.equalTo(true));
		assertThat(gameStateTransition.isFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldStartTransitionOnEndValueIfTimeIsLessThanZero() {
		GameState target = new MockGameState();
		GameState current = new MockGameState();
		GameStateTransition gameStateTransition = new GameStateTransition(current);
		assertThat(gameStateTransition.get(), IsSame.sameInstance(current));
		gameStateTransition.set(target, 0);
		assertThat(gameStateTransition.get(), IsSame.sameInstance(target));
		assertThat(gameStateTransition.isStarted(), IsEqual.equalTo(true));
		assertThat(gameStateTransition.isFinished(), IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldStartTransitionIfCurrentValueIsNotNull() {
		GameState target = new MockGameState();
		GameState current = new MockGameState();
		GameStateTransition gameStateTransition = new GameStateTransition(current);
		assertThat(gameStateTransition.get(), IsSame.sameInstance(current));
		gameStateTransition.set(target, 500);
		assertThat(gameStateTransition.get(), IsSame.sameInstance(current));
		assertThat(gameStateTransition.isStarted(), IsEqual.equalTo(false));
		assertThat(gameStateTransition.isFinished(), IsEqual.equalTo(false));
	}
	
	///
	
	class TransitionScreen extends ScreenAdapter {
		
		GameState start;
		
		GameState end;
		
		boolean isStarted() {
			return false;
		}
		
	}
	
	@Test
	public void test() {
		TransitionScreen transitionScreen = new TransitionScreen();
		assertThat(transitionScreen.isStarted(), IsEqual.equalTo(false));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
