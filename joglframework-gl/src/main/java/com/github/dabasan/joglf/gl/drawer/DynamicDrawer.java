package com.github.dabasan.joglf.gl.drawer;

import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.joglf.gl.shader.ShaderProgram;

/**
 * All dynamic drawers have to extend this class.
 * @author Daba
 *
 */
public abstract class DynamicDrawer {
	private List<ShaderProgram> programs;
	
	public DynamicDrawer() {
		programs=new ArrayList<>();
		this.SetDefaultProgram();
	}
	
	protected List<ShaderProgram> GetPrograms() {
		return programs;
	}
	
	public void AddProgram(ShaderProgram program) {
		programs.add(program);
	}
	public abstract void SetDefaultProgram();
	public void RemoveAllPrograms() {
		programs.clear();
	}
	
	public abstract void UpdateBuffers();
	public abstract void DeleteBuffers();
	
	public abstract void Draw();
}
