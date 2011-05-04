package com.gemserk.animation4j;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class FrameAnimationImplTest {
	
	@Test
	public void test() {
		
		FrameAnimationImpl frameAnimationImpl = new FrameAnimationImpl(false, 150, 1000);
		
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(0));
		frameAnimationImpl.update(149);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(0));
		frameAnimationImpl.update(1);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(1));
		frameAnimationImpl.update(999);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(1));
		frameAnimationImpl.update(1);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(1));
		
	}
	
	@Test
	public void test2() {
		
		FrameAnimationImpl frameAnimationImpl = new FrameAnimationImpl(true, 150, 1000);
		
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(0));
		frameAnimationImpl.update(149);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(0));
		frameAnimationImpl.update(1);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(1));
		frameAnimationImpl.update(999);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(1));
		frameAnimationImpl.update(1);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(0));
		
	}

}
