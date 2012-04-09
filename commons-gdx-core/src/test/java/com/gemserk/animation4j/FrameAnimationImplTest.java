package com.gemserk.animation4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class FrameAnimationImplTest {
	
	@Test
	public void testFrameAnimationImplConstructor1() {
		
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
	public void testFrameAnimationImplConstructor2() {
		
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
	
	@Test
	public void bugNotStartingFromFirstFrameWhenLoop() {
		
		float[] frameTimes = new float [] {1f, 1f};
		
		FrameAnimationImpl frameAnimationImpl = new FrameAnimationImpl(1f, frameTimes);
		frameAnimationImpl.setLoop(true);
		
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(0));
		frameAnimationImpl.update(3f);
		assertThat(frameAnimationImpl.getCurrentFrame(), IsEqual.equalTo(0));
		
	}
	
	@Test
	public void testClone() {
		FrameAnimationImpl source = new FrameAnimationImpl(true, 10f, 50f, 40f);
		source.update(5f);
		FrameAnimationImpl clone = source.clone();
		assertThat(clone.getFramesCount(), IsEqual.equalTo(source.getFramesCount()));
		assertThat(clone.getCurrentFrame(), IsEqual.equalTo(source.getCurrentFrame()));
		assertThat(clone.getCurrentFrame(), IsEqual.equalTo(0));
		clone.update(6f);
		assertThat(clone.getCurrentFrame(), IsEqual.equalTo(1));
	}
	
	@Test
	public void testIterationCount() {
		FrameAnimationImpl source = new FrameAnimationImpl(true, 10f);
		source.update(5f);
		assertEquals(1, source.getIteration());
		source.update(5.1f);
		assertEquals(2, source.getIteration());
	}
	
	@Test
	public void testIterationCountForNoLoopAnimation() {
		FrameAnimationImpl source = new FrameAnimationImpl(false, 10f);
		source.update(5f);
		assertEquals(1, source.getIteration());
		source.update(5.1f);
		assertEquals(1, source.getIteration());
		source.restart();
		assertEquals(2, source.getIteration());
	}

}
