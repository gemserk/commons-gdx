package com.gemserk.commons.gdx.graphics;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRegionUtilsTest {

	private Texture texture512x512;

	@Before
	public void setup() {
		texture512x512 = org.easymock.EasyMock.createMock(Texture.class);
		expect(texture512x512.getWidth()).andReturn(512).anyTimes();
		expect(texture512x512.getHeight()).andReturn(512).anyTimes();
		replay(texture512x512);
	}
	
	@Test
	public void testRegionEquals() {
		TextureRegion region1 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		TextureRegion region2 = new TextureRegion(texture512x512, 50, 30, 40f, 40f);
		TextureRegion region3 = new TextureRegion(texture512x512, 60, 30, 40f, 40f);
		TextureRegion region4 = new TextureRegion(texture512x512, 50, 40, 40f, 40f);
		TextureRegion region5 = new TextureRegion(texture512x512, 50, 30, 50f, 40f);
		TextureRegion region6 = new TextureRegion(texture512x512, 50, 30, 40f, 20f);
		assertTrue(TextureRegionUtils.textureRegionEquals(region1, region1));
		assertTrue(TextureRegionUtils.textureRegionEquals(region1, region2));
		assertFalse(TextureRegionUtils.textureRegionEquals(region1, region3));
		assertFalse(TextureRegionUtils.textureRegionEquals(region1, region4));
		assertFalse(TextureRegionUtils.textureRegionEquals(region1, region5));
		assertFalse(TextureRegionUtils.textureRegionEquals(region1, region6));
	}

}
