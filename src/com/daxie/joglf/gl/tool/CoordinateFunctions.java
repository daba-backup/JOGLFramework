package com.daxie.joglf.gl.tool;

/**
 * Offers functions to handle coordinate systems.
 * @author Daba
 *
 */
public class CoordinateFunctions {
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
