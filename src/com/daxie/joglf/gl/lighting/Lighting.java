package com.daxie.joglf.gl.lighting;

import java.nio.FloatBuffer;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;

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
	
	private boolean lighting_enabled_flag;
	
	public Lighting() {
		light_direction=VectorFunctions.VGet(1.0f, -1.0f, 1.0f);
		light_direction=VectorFunctions.VNorm(light_direction);
		ambient_color=ColorU8Functions.GetColorU8(0.6f, 0.6f, 0.6f, 0.6f);
		
		diffuse_power=0.3f;
		specular_power=0.1f;
		
		lighting_enabled_flag=true;
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
	public void SetLightingEnabledFlag(boolean lighting_enabled_flag) {
		this.lighting_enabled_flag=lighting_enabled_flag;
	}
	
	public void Update() {
		//Texture program
		int program_id;
		
		int light_direction_location;
		int ambient_color_location;
		int diffuse_power_location;
		int specular_power_location;
		
		GLShaderFunctions.EnableProgram("texture");
		program_id=GLShaderFunctions.GetProgramID("texture");
		
		light_direction_location=GLWrapper.glGetUniformLocation(program_id, "light_direction");
		ambient_color_location=GLWrapper.glGetUniformLocation(program_id, "ambient_color");
		diffuse_power_location=GLWrapper.glGetUniformLocation(program_id, "diffuse_power");
		specular_power_location=GLWrapper.glGetUniformLocation(program_id, "specular_power");
		
		FloatBuffer light_direction_buf=BufferFunctions.MakeFloatBufferFromVector(light_direction);
		FloatBuffer ambient_color_buf;
		
		GLWrapper.glUniform3fv(light_direction_location, 1, light_direction_buf);
		if(lighting_enabled_flag==true) {
			ambient_color_buf=BufferFunctions.MakeFloatBufferFromColorU8(ambient_color);
			
			GLWrapper.glUniform4fv(ambient_color_location, 1, ambient_color_buf);
			GLWrapper.glUniform1f(diffuse_power_location, diffuse_power);
			GLWrapper.glUniform1f(specular_power_location, specular_power);
		}
		else {
			ambient_color_buf=BufferFunctions.MakeFloatBufferFromColorU8(ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f));
			
			GLWrapper.glUniform4fv(ambient_color_location, 1, ambient_color_buf);
			GLWrapper.glUniform1f(diffuse_power_location, 0.0f);
			GLWrapper.glUniform1f(specular_power_location, 0.0f);
		}
	}
}
