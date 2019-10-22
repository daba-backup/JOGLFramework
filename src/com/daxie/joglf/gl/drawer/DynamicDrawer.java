package com.daxie.joglf.gl.drawer;

/**
 * All dynamic drawers have to implement these methods.
 * @author Daba
 *
 */
public interface DynamicDrawer {
	public void UpdateBuffers();
	public void DeleteBuffers();
	
	public void Draw();
}
