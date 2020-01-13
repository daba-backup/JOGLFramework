package com.daxie.joglf.gl.drawer;

import java.util.ArrayList;
import java.util.List;

/**
 * All dynamic drawers have to extend these methods.
 * @author Daba
 *
 */
public abstract class DynamicDrawer {
	private List<String> shader_names;
	
	public DynamicDrawer() {
		shader_names=new ArrayList<>();
		
		this.SetDefaultShader();
	}
	
	protected List<String> GetShaderNames() {
		return shader_names;
	}
	
	public void AddShader(String shader_name) {
		shader_names.add(shader_name);
	}
	public void RemoveShader(String shader_name) {
		shader_names.remove(shader_name);
	}
	public abstract void SetDefaultShader();
	public void RemoveAllShaders() {
		shader_names.clear();
	}
	
	public abstract void UpdateBuffers();
	public abstract void DeleteBuffers();
	
	public abstract void Draw();
}
