package com.daxie.basis.coloru8;

/**
 * Offers methods to handle ColorU8.
 * @author Daba
 *
 */
public class ColorU8Functions {
	public static ColorU8 GetColorU8(float r,float g,float b,float a) {
		return new ColorU8(r,g,b,a);
	}
	public static ColorU8 GetColorU8(int r,int g,int b,int a) {
		float fr=r/255.0f;
		float fg=g/255.0f;
		float fb=b/255.0f;
		float fa=a/255.0f;
		
		return new ColorU8(fr, fg, fb, fa);
	}
}
