package com.gemserk.commons.gdx.games;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class SpatialUtilsTest {

	@Test
	public void testConvertToLocalSpatial() {
		Spatial parent = new SpatialImpl(20, 50, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(0f, 0f, 1f, 1f, 0f);
		
		SpatialUtils.convertToLocal(parent, child);
		
		assertThat(child.getX(), IsEqual.equalTo(-20f));
		assertThat(child.getY(), IsEqual.equalTo(-50f));
	}
	
	@Test
	public void testConvertToAbsoluteSpatial() {
		Spatial parent = new SpatialImpl(20, 50, 1f, 1f, 0f);
		Spatial child = new SpatialImpl(-20f, -50f, 1f, 1f, 0f);
		
		SpatialUtils.convertToAbsolute(parent, child);
		
		assertThat(child.getX(), IsEqual.equalTo(0f));
		assertThat(child.getY(), IsEqual.equalTo(0f));
	}

}
