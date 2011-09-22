package com.gemserk.commons.gdx.games;

public class SpatialUtils {

	/**
	 * Converts the child Spatial to local to parent Spatial coordinates.
	 * 
	 * @param parent
	 *            The parent Spatial.
	 * @param child
	 *            The child Spatial.
	 * @return The child Spatial converted to local to parent Spatial coordinates.
	 */
	public static Spatial convertToLocal(Spatial parent, Spatial child) {
		float x = child.getX();
		float y = child.getY();

		float angle = child.getAngle();

		child.setPosition(x - parent.getX(), y - parent.getY());
		child.setAngle(angle - parent.getAngle());

		return child;
	}

	/**
	 * Converts the child Spatial to be absolute to the parent Spatial coordinates.
	 * 
	 * @param parent
	 *            The parent Spatial.
	 * @param child
	 *            The child Spatial.
	 * @return The child Spatial converted to absolute to parent Spatial coordinates.
	 */
	public static Spatial convertToAbsolute(Spatial parent, Spatial child) {
		float x = child.getX();
		float y = child.getY();

		float angle = child.getAngle();

		child.setPosition(x + parent.getX(), y + parent.getY());
		child.setAngle(angle + parent.getAngle());

		return child;
	}

}
