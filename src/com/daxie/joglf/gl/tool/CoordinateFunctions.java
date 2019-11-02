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
	 * Converts a window y-coordinate to an OpenGL y-coordinate.
	 * @param y Window y-coordinate
	 * @param height Window height
	 * @return OpenGL y-coordinate
	 */
	public static int ConvertWindowCoordinateToOpenGLCoordinate_Y(int y,int height) {
		return height-y;
	}
}
