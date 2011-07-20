package com.gemserk.commons.gdx.camera;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.GLU;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;


public class Libgdx2dCameraTransformImplTest {
	
	public static class MockGraphics implements Graphics {
		@Override
		public boolean isGL11Available() {
			return false;
			
		}

		@Override
		public boolean isGL20Available() {
			return false;
			
		}

		@Override
		public GLCommon getGLCommon() {
			return null;
			
		}

		@Override
		public GL10 getGL10() {
			return null;
			
		}

		@Override
		public GL11 getGL11() {
			return null;
			
		}

		@Override
		public GL20 getGL20() {
			return null;
			
		}

		@Override
		public GLU getGLU() {
			return null;
			
		}

		@Override
		public int getWidth() {
			return 0;
			
		}

		@Override
		public int getHeight() {
			return 0;
			
		}

		@Override
		public float getDeltaTime() {
			return 0;
			
		}

		@Override
		public int getFramesPerSecond() {
			return 0;
			
		}

		@Override
		public GraphicsType getType() {
			return null;
			
		}

		@Override
		public float getPpiX() {
			return 0;
			
		}

		@Override
		public float getPpiY() {
			return 0;
			
		}

		@Override
		public float getPpcX() {
			return 0;
			
		}

		@Override
		public float getPpcY() {
			return 0;
			
		}

		@Override
		public boolean supportsDisplayModeChange() {
			return false;
			
		}

		@Override
		public DisplayMode[] getDisplayModes() {
			return null;
			
		}

		@Override
		public DisplayMode getDesktopDisplayMode() {
			return null;
			
		}

		@Override
		public boolean setDisplayMode(DisplayMode displayMode) {
			return false;
			
		}

		@Override
		public boolean setDisplayMode(int width, int height, boolean fullscreen) {
			return false;
			
		}

		@Override
		public void setTitle(String title) {
			
		}

		@Override
		public void setIcon(Pixmap pixmap) {
			
		}

		@Override
		public void setVSync(boolean vsync) {
		}

		@Override
		public BufferFormat getBufferFormat() {
			return null;
			
		}

		@Override
		public boolean supportsExtension(String extension) {
			return false;
			
		}
	}

	@Test
	public void shouldReturnProjectedVectorWhenCameraNotMoved() {
		Gdx.graphics = new MockGraphics();
		
		Libgdx2dCameraTransformImpl camera = new Libgdx2dCameraTransformImpl();
		
		camera.move(0, 0);
		camera.zoom(32f);
		
		Vector2 position = new Vector2(10f, 10f);
		
		camera.project(position);
		
		assertThat(position.x, IsEqual.equalTo(320f));
		assertThat(position.y, IsEqual.equalTo(320f));
	}
	
	@Test
	public void shouldReturnProjectedVectorWhenCameraMoved() {
		Libgdx2dCameraTransformImpl camera = new Libgdx2dCameraTransformImpl();
		
		camera.move(100, 0);
		camera.zoom(32f);
		
		Vector2 position = new Vector2(10f, 10f);
		
		camera.project(position);
		
		assertThat(position.x, IsEqual.equalTo(-2880f));
		assertThat(position.y, IsEqual.equalTo(320f));
	}


}
