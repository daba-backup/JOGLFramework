package com.daxie.joglf.gl.tool;

/**
 * Offers functions to handle coordinate systems.
 * @author Daba
 *
 */
public class CoordinateFunctions {
	/**
	 * Returns a normalized value ranged between -1.0 and 1.0.
	 * @param value Value
	 * @param max Maximum
	 * @return Normalized value
	 */
	public static float NormalizeCoordinate(int value,int max) {
		return 2.0f*value/max-1.0f;
	}
	/**
	 * Returns a normalized value ranged between -1.0 and 1.0.
	 * @param value Value
	 * @param max Maximum
	 * @return Normalized value
	 */
	public static float NormalizeCoordinate(float value,int max) {
		return 2.0f*value/max-1.0f;
	}
	
	/**
	 * Converts a normalized value to a expanded value.
	 * @param value Normalized value
	 * @param max Maximum
	 * @return Expanded value
	 */
	public static int ExpandNormalizedCoordinate(float value,int max) {
		return (int)Math.round(max*(value+1.0)*0.5f);
	}
	
	/**
	 * Mutual conversion between a window y-coordinate and an OpenGL y-coordinate.
	 * @param y Window y-coordinate
	 * @param height Window height
	 * @return OpenGL y-coordinate
	 */
	public static int ConvertWindowCoordinateAndOpenGLCoordinate_Y(int y,int height) {
		return height-y;
	}
}
