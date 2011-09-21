package com.gemserk.componentsengine.utils;

/**
 * Provides common utilities when working with angles.
 */
public class AngleUtils {

	/**
	 * Returns the difference between two angles in degrees between -180 and 180
	 */
	public static double minimumDifference(double a, double b) {
		double diffAngle = b - a;
		if (diffAngle > 180)
			diffAngle -= 360;
		else if (diffAngle < -180)
			diffAngle += 360;
		return diffAngle;
	}

	/**
	 * Increments currentAngle by angle, truncating at desiredAngle.
	 * 
	 * @param angle
	 *            The increment.
	 * @param currentAngle
	 *            The angle to be incremented by angle.
	 * @param desiredAngle
	 *            The limit angle, used to truncate the currentAngle increment.
	 * @return currentAngle incremented by angle and limited by desiredAngle.
	 */
	public static double calculateTruncatedNextAngle(double angle, double currentAngle, double desiredAngle) {
		double theta = currentAngle;

		double diffAngle = minimumDifference(currentAngle, desiredAngle);

		if (diffAngle > 0)
			// turn left
			theta = currentAngle + angle;
		else
			// turn right
			theta = currentAngle - angle;

		// truncate to desiredAngle
		if (desiredAngle > currentAngle && desiredAngle < theta)
			theta = desiredAngle;
		else if (desiredAngle < currentAngle && desiredAngle > theta)
			theta = desiredAngle;

		return theta;
	}

	/**
	 * Returns true whenever the angle is between minAngle and maxAngle, false otherwise.
	 * 
	 * @param angle
	 *            The angle to evaluate.
	 * @param minAngle
	 *            The minimum angle.
	 * @param maxAngle
	 *            the maximum angle.
	 */
	public static boolean between(float angle, float minAngle, float maxAngle) {
		return (AngleUtils.minimumDifference(angle, minAngle) < 0) && (AngleUtils.minimumDifference(angle, maxAngle) > 0);
	}

}