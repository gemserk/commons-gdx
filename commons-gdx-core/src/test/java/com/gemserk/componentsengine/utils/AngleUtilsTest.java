package com.gemserk.componentsengine.utils;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class AngleUtilsTest {
	
	@Test
	public void test() {
		
		assertThat(AngleUtils.minimumDifference(0, 0), IsEqual.equalTo(0.0));
		assertThat(AngleUtils.minimumDifference(0, 180), IsEqual.equalTo(180.0));
		assertThat(AngleUtils.minimumDifference(0, 181), IsEqual.equalTo(-179.0));
		assertThat(AngleUtils.minimumDifference(0, 359), IsEqual.equalTo(-1.0));
		
	}

}
