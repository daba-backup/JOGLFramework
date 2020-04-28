package com.github.dabasan.joglf.gl.lighting;

import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.shader.GLShaderFunctions;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;

/**
 * Lighting
 * @author Daba
 *
 */
public class Lighting {
	private Vector light_direction;
	private ColorU8 ambient_color;
	
	private float diffuse_power;
	private float specular_power;
	
	private List<String> program_names;
	
	public Lighting() {
		light_direction=VectorFunctions.VGet(1.0f, -1.0f, 1.0f);
		light_direction=VectorFunctions.VNorm(light_direction);
		ambient_color=ColorU8Functions.GetColorU8(0.6f, 0.6f, 0.6f, 0.6f);
		
		diffuse_power=0.3f;
		specular_power=0.1f;
		
		program_names=new ArrayList<>();
	}
	
	public void AddProgram(String program_name) {
		program_names.add(program_name);
	}
	public void RemoveProgram(String program_name) {
		program_names.remove(program_name);
	}
	public void RemoveAllPrograms() {
		program_names.clear();
	}
	
	public void SetLightDirection(Vector light_direction) {
		this.light_direction=light_direction;
	}
	public void SetLightDirection(Vector position,Vector target) {
		light_direction=VectorFunctions.VSub(target, position);
		light_direction=VectorFunctions.VNorm(light_direction);
	}
	public void SetAmbientColor(ColorU8 ambient_color) {
		this.ambient_color=ambient_color;
	}
	public void SetDiffusePower(float diffuse_power) {
		this.diffuse_power=diffuse_power;
	}
	public void SetSpecularPower(float specular_power) {
		this.specular_power=specular_power;
	}
	
	public void Update() {
		for(String program_name:program_names) {
			GLShaderFunctions.UseProgram(program_name);
			int program_id=GLShaderFunctions.GetProgramID(program_name);
			
			int light_direction_location=GLWrapper.glGetUniformLocation(program_id, "light_direction");
			int ambient_color_location=GLWrapper.glGetUniformLocation(program_id, "ambient_color");
			int diffuse_power_location=GLWrapper.glGetUniformLocation(program_id, "diffuse_power");
			int specular_power_location=GLWrapper.glGetUniformLocation(program_id, "specular_power");
			
			GLWrapper.glUniform3f(light_direction_location, light_direction.GetX(), light_direction.GetY(), light_direction.GetZ());
			GLWrapper.glUniform4f(ambient_color_location, 
					ambient_color.GetR(), ambient_color.GetG(), ambient_color.GetB(), ambient_color.GetA());
			GLWrapper.glUniform1f(diffuse_power_location, diffuse_power);
			GLWrapper.glUniform1f(specular_power_location, specular_power);
		}
	}
}
