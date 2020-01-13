package com.daxie.joglf.gl.front;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.joglf.gl.fog.Fog;

/**
 * Provides methods for fog.
 * @author Daba
 *
 */
public class FogFront {
	private static Fog fog=new Fog();
	
	public static void EnableFog() {
		fog.SetFogEnabledFlag(true);
	}
	public static void DisableFog() {
		fog.SetFogEnabledFlag(false);
	}
	public static void SetFogColor(ColorU8 color) {
		fog.SetFogColor(color);
	}
	public static void SetFogStartEnd(float start,float end) {
		fog.SetFogStartEnd(start, end);
	}
	
	public static void Update() {
		fog.Update();
	}
}
