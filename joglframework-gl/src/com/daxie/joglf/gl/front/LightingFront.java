package com.daxie.joglf.gl.front;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.vector.Vector;
import com.daxie.joglf.gl.lighting.Lighting;

/**
 * Provides methods for lighting.
 * @author Daba
 *
 */
public class LightingFront {
	private static Lighting lighting=new Lighting();
	
	public static void AddProgram(String program_name) {
		lighting.AddProgram(program_name);
	}
	public static void RemoveProgram(String program_name) {
		lighting.RemoveProgram(program_name);
	}
	public static void RemoveAllPrograms() {
		lighting.RemoveAllPrograms();
	}
	
	public static void SetAmbientColor(ColorU8 color) {
		lighting.SetAmbientColor(color);
	}
	public static void SetLightDirection(Vector light_direction) {
		lighting.SetLightDirection(light_direction);
	}
	public static void SetLightDirection(Vector position,Vector target) {
		lighting.SetLightDirection(position, target);
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
