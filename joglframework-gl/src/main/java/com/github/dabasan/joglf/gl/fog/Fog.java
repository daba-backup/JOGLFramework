package com.github.dabasan.joglf.gl.fog;

import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;

/**
 * Fog
 * @author Daba
 *
 */
public class Fog {
	private float start;
	private float end;
	private ColorU8 color;
	
	private List<ShaderProgram> programs;
	
	public Fog() {
		start=100.0f;
		end=200.0f;
		color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
		
		programs=new ArrayList<>();
	}
	
	public void AddProgram(ShaderProgram program) {
		programs.add(program);
	}
	public void RemoveAllPrograms() {
		programs.clear();
	}
	
	public void SetFogStartEnd(float start,float end) {
		this.start=start;
		this.end=end;
	}
	public void SetFogColor(ColorU8 color) {
		this.color=color;
	}
	
	public void Update() {
		for(ShaderProgram program:programs) {
			program.Enable();
			program.SetUniform("fog.start", start);
			program.SetUniform("fog.end", end);
			program.SetUniform("fog.color", color);
			program.Disable();
		}
	}
}
