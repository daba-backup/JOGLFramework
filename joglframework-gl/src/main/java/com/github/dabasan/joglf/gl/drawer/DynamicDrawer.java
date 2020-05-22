package com.github.dabasan.joglf.gl.drawer;

import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.joglf.gl.shader.ShaderProgram;

/**
 * All dynamic drawers have to extend this class.<br>
 * Getter methods for the shapes (GetSegment(), GetTriangle(), etc.) return a
 * ref to a stored shape.<br>
 * e.g. After you add a triangle (id = 100),
 * drawer.GetTriangle(100).GetVertex(0).SetPos(...) updates a vertex position in
 * the triangle.<br>
 * This is true for all dynamic drawers as of v11.4.0.
 * 
 * @author Daba
 *
 */
public abstract class DynamicDrawer {
	private final List<ShaderProgram> programs;

	public DynamicDrawer() {
		programs = new ArrayList<>();
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

	/**
	 * Updates the buffer.
	 */
	public abstract void UpdateBuffers();
	/**
	 * Deletes the buffer.
	 */
	public abstract void DeleteBuffers();

	public abstract void Draw();
}
