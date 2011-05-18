package com.gemserk.commons.gdx;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class MyGameImplTest {

	class MockScreen implements Screen {

		boolean updateCalled = false;

		boolean renderCalled = false;

		boolean initCalled = false;

		boolean disposeCalled = false;
		
		boolean resumeCalled = false;
		
		boolean pauseCalled = false;
		
		boolean showCalled = false;
		
		boolean hideCalled = false;

		@Override
		public void update(int delta) {
			updateCalled = true;
		}

		@Override
		public void render(int delta) {
			renderCalled = true;
		}

		@Override
		public void init() {
			initCalled = true;
		}

		@Override
		public void dispose() {
			disposeCalled = true;
		}

		@Override
		public void show() {
			showCalled = true;
		}

		@Override
		public void hide() {
			hideCalled = true;
		}

		@Override
		public void pause() {
			pauseCalled = true;
		}

		@Override
		public void resume() {
			resumeCalled = true;
		}

		@Override
		public void resize(int width, int height) {
			// TODO Auto-generated function stub
			
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
			screen.update(getDelta());
			screen.render(getDelta());
		}

		protected int getDelta() {
			return (int) (Gdx.graphics.getDeltaTime() * 1000f);
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
			screen.init();
			screen.resume();
			screen.show();
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


}
