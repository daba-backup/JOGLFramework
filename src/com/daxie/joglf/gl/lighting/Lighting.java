package com.daxie.joglf.gl.lighting;

import java.nio.FloatBuffer;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.gl4.GL4Wrapper;

/**
 * Lighting
 * @author Daba
 *
 */
public class Lighting {
	private Vector light_direction;
	private ColorU8 ambient_color;
	
	public Lighting() {
		light_direction=VectorFunctions.VGet(1.0f, -1.0f, 1.0f);
		light_direction=VectorFunctions.VNorm(light_direction);
		
		ambient_color=ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
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
	
	public void Update() {
		FloatBuffer light_direction_buf=BufferFunctions.MakeFloatBufferFromVector(light_direction);
		FloatBuffer ambient_color_buf=BufferFunctions.MakeFloatBufferFromColorU8(ambient_color);
		
		//Texture program
		int program_id;
		
		int light_direction_location;
		int ambient_color_location;
		
		GLShaderFunctions.EnableProgram("texture");
		program_id=GLShaderFunctions.GetProgramID("texture");
		
		light_direction_location=GL4Wrapper.glGetUniformLocation(program_id, "light_direction");
		ambient_color_location=GL4Wrapper.glGetUniformLocation(program_id, "ambient_color");
		
		GL4Wrapper.glUniform3fv(light_direction_location, 1, light_direction_buf);
		GL4Wrapper.glUniform4fv(ambient_color_location, 1, ambient_color_buf);
	}
}
