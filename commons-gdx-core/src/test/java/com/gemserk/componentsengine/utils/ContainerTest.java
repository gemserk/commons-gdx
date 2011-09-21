package com.gemserk.componentsengine.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class ContainerTest {

	@Test
	public void shouldNotSetAboveTotal() {
		Container container = new Container(50f, 50f);
		container.setCurrent(200f);
		assertEquals(container.getCurrent(), container.getTotal(), 0.01f);
	}

	@Test
	public void shouldNotSetBelowZero() {
		Container container = new Container(50f, 50f);
		container.setCurrent(-200f);
		assertEquals(container.getCurrent(), 0f, 0.01f);
	}

	@Test
	public void shouldNotAddAboveTotal() {
		Container container = new Container(50f, 50f);
		container.add(50f);
		assertThat(container.getCurrent(), IsEqual.equalTo(container.getTotal()));
	}
	
	@Test
	public void shouldNotAddNegativeBelowZero() {
		Container container = new Container(50f, 50f);
		container.add(-70f);
		assertThat(container.getCurrent(), IsEqual.equalTo(0f));
	}
	
	@Test
	public void shouldNotRemoveBelowZero() {
		Container container = new Container(50f, 50f);
		container.remove(70f);
		assertThat(container.getCurrent(), IsEqual.equalTo(0f));
	}
	
	@Test
	public void shouldNotRemoveNegativeAboveTotal() {
		Container container = new Container(40f, 50f);
		container.remove(-50f);
		assertThat(container.getCurrent(), IsEqual.equalTo(container.getTotal()));
	}
	
}
