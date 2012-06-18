package com.gemserk.commons.utils;

import static org.junit.Assert.*;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class AnimationUtilsTest {

	@Test
	public void test() {
		assertThat(AnimationUtils.secondsToFrame(0, 30), IsEqual.equalTo(0));
		assertThat(AnimationUtils.secondsToFrame(0.03333333f, 30), IsEqual.equalTo(1));
		assertThat(AnimationUtils.secondsToFrame(1f, 30), IsEqual.equalTo(30));
	}

}
