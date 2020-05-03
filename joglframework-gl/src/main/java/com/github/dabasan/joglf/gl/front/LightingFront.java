package com.github.dabasan.joglf.gl.front;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.lighting.Lighting;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;

/**
 * Provides methods for lighting.
 * 
 * @author Daba
 *
 */
public class LightingFront {
	private static Lighting lighting = new Lighting();

	public static void AddProgram(ShaderProgram program) {
		lighting.AddProgram(program);
	}
	public static void RemoveAllPrograms() {
		lighting.RemoveAllPrograms();
	}

	public static void SetAmbientColor(ColorU8 color) {
		lighting.SetAmbientColor(color);
	}
	public static void SetLightDirection(Vector light_direction) {
		lighting.SetDirection(light_direction);
	}
	public static void SetLightDirection(Vector position, Vector target) {
		lighting.SetDirection(position, target);
	}
	public static void SetDiffusePower(float diffuse_power) {
		lighting.SetDiffusePower(diffuse_power);
	}
	public static void SetSpecularPower(float specular_power) {
		lighting.SetSpecularPower(specular_power);
	}

	public static void Update() {
		lighting.Update();
	}
}
