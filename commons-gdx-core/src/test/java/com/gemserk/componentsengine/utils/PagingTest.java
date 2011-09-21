package com.gemserk.componentsengine.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class PagingTest {

	@Test
	public void shouldHaveTotalPages() {
		Paging paging = new Paging(new Object[] {}, 2);
		assertEquals(0, paging.getTotalPages());

		paging = new Paging(new Object[] { 0, 1 }, 2);
		assertEquals(1, paging.getTotalPages());

		paging = new Paging(new Object[] { 0, 1, 2 }, 2);
		assertEquals(2, paging.getTotalPages());

		paging = new Paging(new Object[] { 0, 1, 2, 3 }, 2);
		assertEquals(2, paging.getTotalPages());

		paging = new Paging(new Object[] { 0, 1, 2, 3, 4 }, 2);
		assertEquals(3, paging.getTotalPages());
	}

	@Test
	public void shouldInitWithValues() {
		Object[] items = {};

		int itemsPerPage = 4;

		Paging paging = new Paging(items, itemsPerPage);

		assertThat(paging.getCurrentPage(), IsEqual.equalTo(0));
		assertThat(paging.getTotalItems(), IsEqual.equalTo(items.length));
	}

	@Test
	public void shouldReturnCorrectValueFromCurrentPage() {
		Object[] items = { 1, 2, 3, 4 };

		int itemsPerPage = 2;

		Paging paging = new Paging(items, itemsPerPage);

		assertEquals(4, paging.getTotalItems());

		Integer item = (Integer) paging.getItem(0);
		assertEquals(1, item.intValue());

		paging.nextPage();
		item = (Integer) paging.getItem(0);
		assertEquals(3, item.intValue());

		item = (Integer) paging.getItem(1);
		assertEquals(4, item.intValue());

		paging.previousPage();

		item = (Integer) paging.getItem(0);
		assertEquals(1, item.intValue());
	}

	@Test
	public void shouldNotIncrementCurrentPageWhenIsInLastPage() {
		Object[] items = { 1, 2, 3, 4 };

		int itemsPerPage = 2;

		Paging paging = new Paging(items, itemsPerPage);
		assertEquals(0, paging.getCurrentPage());
		paging.nextPage();
		assertEquals(1, paging.getCurrentPage());
		paging.nextPage();
		assertEquals(1, paging.getCurrentPage());

	}

	@Test
	public void shouldNotDecrementCurrentPageWhenIsInFirstPage() {
		Object[] items = { 1, 2, 3, 4 };

		int itemsPerPage = 2;

		Paging paging = new Paging(items, itemsPerPage);
		assertEquals(0, paging.getCurrentPage());
		paging.previousPage();
		assertEquals(0, paging.getCurrentPage());

	}

	@Test
	public void shouldNotReturnItemFromPageWhenNotExist() {
		Object[] items = { 1, 2, 3 };

		int itemsPerPage = 2;

		Paging paging = new Paging(items, itemsPerPage);
		assertEquals(true, paging.onPageHasItem(0));
		assertEquals(true, paging.onPageHasItem(1));
		paging.nextPage();
		assertEquals(true, paging.onPageHasItem(0));
		assertEquals(false, paging.onPageHasItem(1));
	}

}
