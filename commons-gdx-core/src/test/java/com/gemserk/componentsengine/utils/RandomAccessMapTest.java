package com.gemserk.componentsengine.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RandomAccessMapTest {

	private RandomAccessMap<String, String> map;


	@Before
	public void setUp(){
		map = new RandomAccessMap<String,String>(); 
	}
	
	
	@Test
	public void aNewMapShouldBeEmpty(){
		Assert.assertTrue(map.isEmpty());
	}
	
	@Test
	public void aMapShouldContainAnAddedKeyValue(){
		map.put("hola","hello");
		
		Assert.assertTrue(map.containsKey("hola"));
		Assert.assertTrue(map.containsValue("hello"));
		Assert.assertEquals("hello", map.get(0));
		Assert.assertFalse(map.containsKey("chau"));
		Assert.assertEquals(1, map.size());
	}
	
	@Test
	public void whenRemovingAKeyTheMapDoesntBreak(){
		map.put("hola","hello");
		map.put("chau","goodbye");
		Assert.assertTrue(map.containsKey("hola"));
		Assert.assertTrue(map.containsKey("chau"));
		Assert.assertEquals(2, map.size());
		map.remove("hola");
		Assert.assertFalse(map.containsKey("hola"));
		Assert.assertTrue(map.containsKey("chau"));
		Assert.assertEquals("goodbye", map.get(0));
		Assert.assertEquals(1, map.size());
	}
	
	@Test
	public void whenRemovingTheLastKeyTheMapDoesntBreak(){
		map.put("hola","hello");
		map.put("chau","goodbye");
		Assert.assertTrue(map.containsKey("hola"));
		Assert.assertTrue(map.containsKey("chau"));
		Assert.assertEquals(2, map.size());
		map.remove("chau");
		Assert.assertFalse(map.containsKey("chau"));
		Assert.assertTrue(map.containsKey("hola"));
		Assert.assertEquals("hello", map.get(0));
		Assert.assertEquals(1, map.size());
	}
	
	
	@Test
	public void whenAddingDuplicateEntriesTheSetRemainsTheSame(){
		map.put("hola","hello");
		map.put("chau","goodbye");
		Assert.assertTrue(map.containsKey("hola"));
		Assert.assertTrue(map.containsKey("chau"));
		Assert.assertEquals(2, map.size());
		map.put("hola","bonjour");
		Assert.assertTrue(map.containsKey("hola"));
		Assert.assertTrue(map.containsKey("chau"));
		Assert.assertTrue(map.containsValue("goodbye"));
		Assert.assertTrue(map.containsValue("bonjour"));
		Assert.assertEquals(2, map.size());
	}
	
}
