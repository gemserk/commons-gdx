package com.gemserk.commons.gdx.graphics;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteUtilsTest {

	private Texture texture512x512;

	@Before
	public void setup() {
		texture512x512 = org.easymock.EasyMock.createMock(Texture.class);
		expect(texture512x512.getWidth()).andReturn(512).anyTimes();
		expect(texture512x512.getHeight()).andReturn(512).anyTimes();
		replay(texture512x512);
	}
	
	@Test
	public void shouldReturnFalseIfSpriteNull() {
		TextureRegion region1 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		Sprite sprite1 = new Sprite(region1);
		assertFalse(SpriteUtils.isAliasSprite(sprite1, null));
		assertFalse(SpriteUtils.isAliasSprite(null, sprite1));
	}

	@Test
	public void shouldReturnIsAliasWhenSameSprite() {
		TextureRegion region1 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		Sprite sprite1 = new Sprite(region1);
		assertTrue(SpriteUtils.isAliasSprite(sprite1, sprite1));
	}

	@Test
	public void shouldReturnIsAliasWhenSameRegion() {
		TextureRegion region1 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		Sprite sprite1 = new Sprite(region1);
		Sprite sprite2 = new Sprite(region1);
		assertTrue(SpriteUtils.isAliasSprite(sprite1, sprite2));
	}
	
	@Test
	public void shouldReturnIsAliasWhenRegionEquals() {
		TextureRegion region1 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		TextureRegion region2 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		Sprite sprite1 = new Sprite(region1);
		Sprite sprite2 = new Sprite(region2);
		assertTrue(SpriteUtils.isAliasSprite(sprite1, sprite2));
	}

	@Test
	public void shouldReturnNotAliasWhenSpriteAndAtlasSprite() {
		TextureRegion region1 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		Sprite sprite1 = new Sprite(region1);
		Sprite sprite2 = new AtlasSprite(new AtlasRegion(texture512x512, 50, 30, 40, 40));
		assertFalse(SpriteUtils.isAliasSprite(sprite1, sprite2));
	}
	
	@Test
	public void shouldReturnNotAliasWhenRegionAreNotEqual() {
		TextureRegion region1 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		TextureRegion region2 = new TextureRegion(texture512x512, 50, 30, 10f, 40f);
		Sprite sprite1 = new Sprite(region1);
		Sprite sprite2 = new Sprite(region2);
		assertFalse(SpriteUtils.isAliasSprite(sprite1, sprite2));
	}
	
	@Test
	public void shouldReturnNotAliasWhenAtlasRegionsAreNotEqual() {
		Sprite sprite1 = new AtlasSprite(new AtlasRegion(texture512x512, 50, 30, 40, 40));
		Sprite sprite2 = new AtlasSprite(new AtlasRegion(texture512x512, 50, 30, 20, 40));
		assertFalse(SpriteUtils.isAliasSprite(sprite1, sprite2));
	}

	@Test
	public void shouldReturnAliasWhenAtlasRegionsAreEqual() {
		AtlasRegion atlasRegion1 = new AtlasRegion(texture512x512, 50, 30, 40, 40);
		AtlasRegion atlasRegion2 = new AtlasRegion(texture512x512, 50, 30, 40, 40);
		Sprite sprite1 = new AtlasSprite(atlasRegion1);
		Sprite sprite2 = new AtlasSprite(atlasRegion2);
		assertTrue(SpriteUtils.isAliasSprite(sprite1, sprite2));
	}

}
