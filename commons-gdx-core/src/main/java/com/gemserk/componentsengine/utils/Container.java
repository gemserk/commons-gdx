package com.gemserk.componentsengine.utils;

public class Container {

	private float current;

	private float total;

	public void setCurrent(float current) {
		if (current > total)
			current = total;
		if (current < 0f)
			current = 0f;
		this.current = current;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getCurrent() {
		return current;
	}

	public float getTotal() {
		return total;
	}

	public float getPercentage() {
		return current / total;
	}

	public Container() {
		current = total = 0;
	}

	public Container(float current, float total) {
		super();
		this.current = current;
		this.total = total;
	}
	
	public Container(Container container) {
		this.current = container.getCurrent();
		this.total = container.getTotal();
	}

	public boolean isFull() {
		return current == total;
	}

	public boolean isEmpty() {
		return current == 0;
	}

	/**
	 * Removes from the container the specified amount.
	 * 
	 * @param value
	 *            The amount to be remove.
	 */
	public void remove(float value) {
		setCurrent(current - value);
	}

	/**
	 * Fills the container the specified amount.
	 * 
	 * @param value
	 *            The amount to fill.
	 */
	public void add(float value) {
		setCurrent(current + value);
	}

	@Override
	public String toString() {
		return "[" + getCurrent() + "/" + getTotal() + "]";
	}

}
