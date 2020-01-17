package com.daxie.joglf.gl.drawer;

import com.daxie.joglf.gl.window.WindowCommonInfoStock;

/**
 * All 2D drawers have to extend this class.
 * @author Daba
 *
 */
public abstract class Dynamic2DDrawer extends DynamicDrawer{
	private int window_width;
	private int window_height;
	
	public Dynamic2DDrawer() {
		window_width=WindowCommonInfoStock.DEFAULT_WIDTH;
		window_height=WindowCommonInfoStock.DEFAULT_HEIGHT;
	}
	
	protected int GetWindowWidth() {
		return window_width;
	}
	protected int GetWindowHeight() {
		return window_height;
	}
	
	public void SetWindowSize(int width,int height) {
		window_width=width;
		window_height=height;
	}
}
