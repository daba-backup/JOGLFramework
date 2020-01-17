package com.daxie.joglf.gl.drawer;

import java.util.ArrayList;
import java.util.List;

/**
 * All dynamic drawers have to extend this class.
 * @author Daba
 *
 */
public abstract class DynamicDrawer {
	private List<String> program_names;
	
	public DynamicDrawer() {
		program_names=new ArrayList<>();
		
		this.SetDefaultProgram();
	}
	
	protected List<String> GetProgramNames() {
		return program_names;
	}
	
	public void AddProgram(String program_name) {
		program_names.add(program_name);
	}
	public void RemoveProgram(String program_name) {
		program_names.remove(program_name);
	}
	public abstract void SetDefaultProgram();
	public void RemoveAllShaders() {
		program_names.clear();
	}
	
	public abstract void UpdateBuffers();
	public abstract void DeleteBuffers();
	
	public abstract void Draw();
}
