package com.github.dabasan.joglf.gl.lighting;

import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;

/**
 * Lighting
 * 
 * @author Daba
 *
 */
public class Lighting {
	private Vector direction;
	private ColorU8 ambient_color;
	private ColorU8 diffuse_color;
	private ColorU8 specular_color;
	private float ambient_power;
	private float diffuse_power;
	private float specular_power;

	private final List<ShaderProgram> programs;

	public Lighting() {
		direction = VectorFunctions.VGet(1.0f, -1.0f, 1.0f);
		direction = VectorFunctions.VNorm(direction);
		ambient_color = ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
		diffuse_color = ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
		specular_color = ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
		ambient_power = 0.6f;
		diffuse_power = 0.3f;
		specular_power = 0.1f;

		programs = new ArrayList<>();
	}

	public void AddProgram(ShaderProgram program) {
		programs.add(program);
	}
	public void RemoveProgram(ShaderProgram program) {
		programs.remove(program);
	}
	public void RemoveAllPrograms() {
		programs.clear();
	}

	public void SetDirection(Vector direction) {
		this.direction = direction;
	}
	public void SetDirection(Vector position, Vector target) {
		direction = VectorFunctions.VSub(target, position);
		direction = VectorFunctions.VNorm(direction);
	}
	public void SetAmbientColor(ColorU8 ambient_color) {
		this.ambient_color = ambient_color;
	}
	public void SetDiffuseColor(ColorU8 diffuse_color) {
		this.diffuse_color = diffuse_color;
	}
	public void SetSpecularColor(ColorU8 specular_color) {
		this.specular_color = specular_color;
	}
	public void SetAmbientPower(float ambient_power) {
		this.ambient_power = ambient_power;
	}
	public void SetDiffusePower(float diffuse_power) {
		this.diffuse_power = diffuse_power;
	}
	public void SetSpecularPower(float specular_power) {
		this.specular_power = specular_power;
	}

	/**
	 * Transfers data to the programs.
	 */
	public void Update() {
		for (final ShaderProgram program : programs) {
			program.Enable();
			program.SetUniform("lighting.direction", direction);
			program.SetUniform("lighting.ambient_color", ambient_color);
			program.SetUniform("lighting.diffuse_color", diffuse_color);
			program.SetUniform("lighting.specular_color", specular_color);
			program.SetUniform("lighting.ambient_power", ambient_power);
			program.SetUniform("lighting.diffuse_power", diffuse_power);
			program.SetUniform("lighting.specular_power", specular_power);
			program.Disable();
		}
	}
}
