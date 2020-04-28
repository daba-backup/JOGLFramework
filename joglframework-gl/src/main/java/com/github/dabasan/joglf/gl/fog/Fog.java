package com.github.dabasan.joglf.gl.fog;

import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.github.dabasan.joglf.gl.shader.GLShaderFunctions;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;

/**
 * Fog
 * @author Daba
 *
 */
public class Fog {
	private float fog_start;
	private float fog_end;
	private ColorU8 fog_color;
	
	private List<String> program_names;
	
	public Fog() {
		fog_start=100.0f;
		fog_end=200.0f;
		fog_color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
		
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
	
	public void SetFogStartEnd(float start,float end) {
		fog_start=start;
		fog_end=end;
	}
	public void SetFogColor(ColorU8 color) {
		fog_color=color;
	}
	
	public void Update() {
		for(String program_name:program_names) {
			GLShaderFunctions.UseProgram(program_name);
			int program_id=GLShaderFunctions.GetProgramID(program_name);
			
			int fog_start_location=GLWrapper.glGetUniformLocation(program_id, "fog_start");
			int fog_end_location=GLWrapper.glGetUniformLocation(program_id, "fog_end");
			int fog_color_location=GLWrapper.glGetUniformLocation(program_id, "fog_color");
			
			GLWrapper.glUniform1f(fog_start_location, fog_start);
			GLWrapper.glUniform1f(fog_end_location, fog_end);
			GLWrapper.glUniform4f(fog_color_location, fog_color.GetR(), fog_color.GetG(), fog_color.GetB(), fog_color.GetA());
		}
	}
}
