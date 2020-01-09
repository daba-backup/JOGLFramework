package com.daxie.joglf.gl.front;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.joglf.gl.tinter.Tinter;

/**
 * Provides methods for tinter.
 * @author Daba
 *
 */
public class TinterFront {
	private static Tinter tinter=new Tinter();
	
	public static void SetTintColor(ColorU8 color) {
		tinter.SetTintColor(color);
	}
	
	public static void Initialize() {
		tinter.Initialize();
	}
	
	public static void Tint() {
		tinter.Tint();
	}
}
