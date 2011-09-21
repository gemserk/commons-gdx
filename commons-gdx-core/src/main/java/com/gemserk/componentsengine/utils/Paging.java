package com.gemserk.componentsengine.utils;

import java.util.Arrays;
import java.util.List;

public class Paging<T> {

	private List<T> items;

	private int itemsPerPage;

	private int totalPages;

	private int currentPage = 0;

	public Paging(List<T> items, int itemsPerPage) {
		this.items = items;
		this.itemsPerPage = itemsPerPage;
		totalPages = (int) Math.ceil(((float) items.size()) / (float) itemsPerPage);
	}

	public Paging(T[] items, int itemsPerPage) {
		this(Arrays.asList(items), itemsPerPage);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalItems() {
		return items.size();
	}

	public Object getItem(int index) {
		return items.get(getCurrentPage() * itemsPerPage + index);
	}

	public void nextPage() {
		currentPage++;
		if (currentPage >= totalPages)
			currentPage = getLastPage();
	}

	private int getLastPage() {
		return totalPages - 1;
	}

	public void previousPage() {
		currentPage--;
		if (currentPage < 0)
			currentPage = 0;
	}

	public boolean isFirstPage() {
		return getCurrentPage() == 0;
	}

	public boolean isLastPage() {
		return getCurrentPage() == getLastPage();
	}

	public boolean onPageHasItem(int index) {
		return index < getItemsCountOnCurrentPage();
	}

	private int getTotalItemsFromPages() {
		return getTotalPages() * itemsPerPage;
	}

	public int getItemsCountOnCurrentPage() {
		if (currentPage < getLastPage())
			return itemsPerPage;
		return itemsPerPage - (getTotalItemsFromPages() - getTotalItems());
	}

}
