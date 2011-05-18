package com.gemserk.commons.gdx;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class MyGameImplTest {

	private class MockGame extends Game {
		@Override
		protected float getDelta() {
			return 1f;
		}
	}

	interface Screen extends com.badlogic.gdx.Screen {

		void update(float delta);

		boolean isPaused();

		boolean isVisible();

	}

	class ScreenAdapter implements Screen {

		boolean paused = false;

		boolean visible = false;
		
		public boolean isPaused() {
			return paused;
		}

		public boolean isVisible() {
			return visible;
		}

		@Override
		public void render(float delta) {
			
		}

		@Override
		public void update(float delta) {
		}

		@Override
		public void resize(int width, int height) {
		}

		@Override
		public void show() {
			visible = true;
		}

		@Override
		public void hide() {
			visible = false;
		}

		@Override
		public void pause() {
			paused = true;
		}

		@Override
		public void resume() {
			paused = false;
		}

		@Override
		public void dispose() {

		}

	}

	class MockScreen extends ScreenAdapter {

		boolean updateCalled = false;

		boolean renderCalled = false;

		@Override
		public void update(float delta) {
			updateCalled = true;
		}

		@Override
		public void render(float delta) {
			renderCalled = true;
		}

	}

	class Game implements ApplicationListener {

		private Screen screen;

		/**
		 * @return the currently active {@link Screen}.
		 */
		public Screen getScreen() {
			return screen;
		}

		@Override
		public void dispose() {
			if (screen == null)
				return;
			screen.hide();
			screen.pause();
			screen.dispose();
		}

		@Override
		public void pause() {
			if (screen != null)
				screen.pause();
		}

		@Override
		public void resume() {
			if (screen != null)
				screen.resume();
		}

		@Override
		public void render() {
			if (screen == null)
				return;
			if (!screen.isPaused())
				screen.update(getDelta());
			if (screen.isVisible())
				screen.render(getDelta());
		}

		protected float getDelta() {
			return Gdx.graphics.getDeltaTime();
		}

		@Override
		public void resize(int width, int height) {
			if (screen != null)
				screen.resize(width, height);
		}

		public void setScreen(Screen screen) {
			if (screen == null)
				return;

			if (this.screen != null) {
				this.screen.pause();
				this.screen.hide();
			}

			this.screen = screen;
			this.screen.resume();
			this.screen.show();
		}

		@Override
		public void create() {

		}

	}

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
		screen.paused = true;
		screen.visible = false;

		Game game = new Game();
		game.setScreen(screen);

		assertThat(game.getScreen(), IsSame.sameInstance((Screen) screen));
		assertThat(screen.isPaused(), IsEqual.equalTo(false));
		assertThat(screen.isVisible(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldCallPauseAndHideToPreviousScreen() {
		MockScreen screen1 = new MockScreen();
		MockScreen screen2 = new MockScreen();

		screen1.paused = true;
		screen1.visible = false;

		screen2.paused = true;
		screen2.visible = false;

		Game game = new Game();
		game.setScreen(screen1);
		game.setScreen(screen2);

		assertThat(game.getScreen(), IsSame.sameInstance((Screen) screen2));
		assertThat(screen1.isPaused(), IsEqual.equalTo(true));
		assertThat(screen1.isVisible(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotCallUpdateIfPaused() {
		MockScreen screen = new MockScreen();
		Game game = new MockGame();
		game.setScreen(screen);
		screen.paused = true;
		screen.updateCalled = false;
		game.render();
		assertThat(screen.updateCalled, IsEqual.equalTo(false));
	}

	@Test
	public void shouldCallUpdateIfNotPaused() {
		MockScreen screen = new MockScreen();
		Game game = new MockGame();
		game.setScreen(screen);
		screen.paused = false;
		screen.updateCalled = false;
		game.render();
		assertThat(screen.updateCalled, IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotCallRenderIfNotVisible() {
		MockScreen screen = new MockScreen();
		Game game = new MockGame();
		game.setScreen(screen);
		screen.visible = false;
		screen.renderCalled = false;
		game.render();
		assertThat(screen.renderCalled, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldCallRenderIfVisible() {
		MockScreen screen = new MockScreen();
		Game game = new MockGame();
		game.setScreen(screen);
		screen.visible = true;
		screen.renderCalled = false;
		game.render();
		assertThat(screen.renderCalled, IsEqual.equalTo(true));
	}

}
