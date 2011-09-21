package com.gemserk.componentsengine.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RandomAccessSetTest {

	private RandomAccessSet<String> set;


	@Before
	public void setUp(){
		set = new RandomAccessSet<String>(); 
	}
	
	
	@Test
	public void aNewSetShouldBeEmpty(){
		Assert.assertTrue(set.isEmpty());
	}
	
	@Test
	public void aSetShouldContainAnAddedValue(){
		set.add("hola");
		
		Assert.assertTrue(set.contains("hola"));
		Assert.assertEquals("hola", set.get(0));
		Assert.assertFalse(set.contains("chau"));
		Assert.assertEquals(1, set.size());
	}
	
	@Test
	public void whenRemovingAValueTheSetDoesntBreak(){
		set.add("hola");
		set.add("chau");
		Assert.assertTrue(set.contains("hola"));
		Assert.assertTrue(set.contains("chau"));
		Assert.assertEquals(2, set.size());
		set.remove("hola");
		Assert.assertFalse(set.contains("hola"));
		Assert.assertTrue(set.contains("chau"));
		Assert.assertEquals("chau", set.get(0));
		Assert.assertEquals(1, set.size());
	}
	
	@Test
	public void whenRemovingTheLastValueTheSetDoesntBreak(){
		set.add("hola");
		set.add("chau");
		Assert.assertTrue(set.contains("hola"));
		Assert.assertTrue(set.contains("chau"));
		Assert.assertEquals(2, set.size());
		set.remove("chau");
		Assert.assertFalse(set.contains("chau"));
		Assert.assertTrue(set.contains("hola"));
		Assert.assertEquals("hola", set.get(0));
		Assert.assertEquals(1, set.size());
	}
	
	@Test
	public void whenAddingDuplicateEntriesTheSetRemainsTheSame(){
		set.add("hola");
		set.add("chau");
		Assert.assertTrue(set.contains("hola"));
		Assert.assertTrue(set.contains("chau"));
		Assert.assertEquals(2, set.size());
		set.add("hola");
		Assert.assertTrue(set.contains("hola"));
		Assert.assertTrue(set.contains("chau"));
		Assert.assertEquals(2, set.size());
	}
	
}
