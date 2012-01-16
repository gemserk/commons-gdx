package com.gemserk.commons.gdx.graphics;

public interface ConvexHull2d {

	/**
	 * Adds a point to prepare for the recalculation.
	 * 
	 * @param x
	 *            The x coordinate of the point.
	 * @param y
	 *            The y coordinate of the point.
	 */
	void add(float x, float y);

	/**
	 * Recalculates the ConvexHull for the all the given points specified with the add method and resets the calculation to prepare for the next calculation.
	 * 
	 * @return true if the the Convex Hull was calculated correctly, false otherwise.
	 */
	boolean recalculate();

	/**
	 * Returns the quantity of points of the last calculated Convex Hull.
	 */
	int getPointsCount();

	/**
	 * Returns the x coordinate of the Convex Hull point specified by index;
	 */
	float getX(int index);

	/**
	 * Returns the y coordinate of the Convex Hull point specified by index;
	 */
	float getY(int index);

	/**
	 * Returns true if the point is inside in the Convex Hull and false otherwise.
	 * 
	 * @param x
	 *            The x coordinate of the point.
	 * @param y
	 *            The y coordinate of the point.
	 */
	boolean inside(float x, float y);

}