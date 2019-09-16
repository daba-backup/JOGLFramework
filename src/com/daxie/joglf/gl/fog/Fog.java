package com.daxie.joglf.gl.fog;

import java.nio.FloatBuffer;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.gl4.GL4Wrapper;

/**
 * Fog
 * @author Daba
 *
 */
public class Fog {
	private float fog_start;
	private float fog_end;
	private ColorU8 fog_color;
	
	public Fog() {
		fog_start=100.0f;
		fog_end=200.0f;
		fog_color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public void SetFogStartEnd(float start,float end) {
		fog_start=start;
		fog_end=end;
	}
	public void SetFogColor(ColorU8 color) {
		fog_color=color;
	}
	
	public void Update() {
		FloatBuffer fog_color_buf=BufferFunctions.MakeFloatBufferFromColorU8(fog_color);
		
		//Texture program
		int program_id;
		
		int fog_start_location;
		int fog_end_location;
		int fog_color_location;
		
		GLShaderFunctions.EnableProgram("texture");
		program_id=GLShaderFunctions.GetProgramID("texture");
		
		fog_start_location=GL4Wrapper.glGetUniformLocation(program_id, "fog_start");
		fog_end_location=GL4Wrapper.glGetUniformLocation(program_id, "fog_end");
		fog_color_location=GL4Wrapper.glGetUniformLocation(program_id, "fog_color");
		
		GL4Wrapper.glUniform1f(fog_start_location, fog_start);
		GL4Wrapper.glUniform1f(fog_end_location, fog_end);
		GL4Wrapper.glUniform4fv(fog_color_location, 1, fog_color_buf);
	}
}
