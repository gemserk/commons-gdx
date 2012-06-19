package com.gemserk.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class StoreTest {

	static class IntegerStoreFactory implements StoreFactory<Integer> {
		int number = 0;
		@Override
		public Integer createObject() {
			return new Integer(number++);
		}
	}

	@Test
	public void shouldReturnNewInstanceIfNoInstancesStored() {
		Store<Integer> integers = new Store<Integer>(new IntegerStoreFactory());

		assertEquals(0, integers.size());

		integers.get();

		assertEquals(0, integers.size());
	}

	@Test
	public void shouldReturnNewInstanceIfNoInstancesStored2() {
		Store<Integer> integers = new Store<Integer>(new IntegerStoreFactory());

		assertEquals(0, integers.size());

		integers.get();
		integers.get();

		assertEquals(0, integers.size());
	}

	@Test
	public void shouldReuseAlreadyCreatedInstance() {
		Store<Integer> integers = new Store<Integer>(new IntegerStoreFactory());

		Integer integer1 = integers.get();
		integers.free(integer1);

		assertEquals(1, integers.size());

		integers.get();

		assertEquals(0, integers.size());
	}

	@Test
	public void shouldAcceptAnInstanceCreatedOutside() {
		Store<Integer> integers = new Store<Integer>(new IntegerStoreFactory());

		Integer integer1 = new Integer(50);
		assertEquals(0, integers.size());

		integers.free(integer1);

		assertEquals(1, integers.size());
	}

	@Test
	public void shouldPreCreateObjectsAndPutThemAsFree() {
		Store<Integer> integers = new Store<Integer>(new IntegerStoreFactory());

		assertEquals(0, integers.size());
		
		integers.preCreate(5);

		assertEquals(5, integers.size());
	}

	@Test
	public void bugShouldNotFreeSameElementTwice() {
		Store<Integer> integers = new Store<Integer>(new IntegerStoreFactory());

		Integer integer1 = integers.get();

		integers.free(integer1);
		integers.free(integer1);

		Integer integer2 = integers.get();
		Integer integer3 = integers.get();

		assertFalse(integer2 == integer3);
	}
	
	@Test
	public void bugShouldNotFreeSameElementByEqualsTwice() {
		Store<Integer> integers = new Store<Integer>(new IntegerStoreFactory());

		Integer integer1 = integers.get();

		integers.free(integer1);
		integers.free(new Integer(integer1.intValue()));

		Integer integer2 = integers.get();
		Integer integer3 = integers.get();

		assertFalse(integer2.equals(integer3));
	}

}
