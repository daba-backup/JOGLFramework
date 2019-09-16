package com.daxie.tool;

/**
 * Methods to handle mathematical operations.
 * @author Daba
 *
 */
public class MathFunctions {
	/**
	 * Converts degree to radian.
	 * @param deg Degree
	 * @return Radian
	 */
	public static float DegToRad(float deg) {
		return (float)Math.PI/180.0f*deg;
	}
	/**
	 * Converts radian to degree.
	 * @param rad Radian
	 * @return Degree
	 */
	public static float RadToDeg(float rad) {
		return 180.0f*rad/(float)Math.PI;
	}
}
