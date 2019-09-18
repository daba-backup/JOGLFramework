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
	
	/**
	 * Clamps an int value between min and max.
	 * @param value Value
	 * @param min Minimum
	 * @param max Maximum
	 * @return Clamped value
	 */
	public static int Clamp(int value,int min,int max) {
		int ret;
		
		if(value<min)ret=min;
		else if(value>max)ret=max;
		else ret=value;
		
		return ret;
	}
	/**
	 * Clamps a float value between min and max.
	 * @param value Value
	 * @param min Minimum
	 * @param max Maximum
	 * @return Clamped value
	 */
	public static float Clamp(float value,float min,float max) {
		float ret;
		
		if(value<min)ret=min;
		else if(value>max)ret=max;
		else ret=value;
		
		return ret;
	}
}
