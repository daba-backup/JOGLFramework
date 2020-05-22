package com.github.dabasan.joglf.gl.front;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.joglf.gl.fog.Fog;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;

/**
 * Provides methods for fog.
 * 
 * @author Daba
 *
 */
public class FogFront {
	private static Fog fog = new Fog();

	public static void AddProgram(ShaderProgram program) {
		fog.AddProgram(program);
	}
	public static void RemoveAllPrograms() {
		fog.RemoveAllPrograms();
	}

	public static void SetFogColor(ColorU8 color) {
		fog.SetFogColor(color);
	}
	public static void SetFogStartEnd(float start, float end) {
		fog.SetFogStartEnd(start, end);
	}

	/**
	 * Transfers data to the programs.
	 */
	public static void Update() {
		fog.Update();
	}
}
