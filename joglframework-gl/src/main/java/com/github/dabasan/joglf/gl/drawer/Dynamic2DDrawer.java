package com.github.dabasan.joglf.gl.drawer;

import com.github.dabasan.joglf.gl.window.WindowCommonInfo;

/**
 * All 2D drawers have to extend this class.
 * 
 * @author Daba
 *
 */
public abstract class Dynamic2DDrawer extends DynamicDrawer {
	private int window_width;
	private int window_height;

	public Dynamic2DDrawer() {
		window_width = WindowCommonInfo.DEFAULT_WIDTH;
		window_height = WindowCommonInfo.DEFAULT_HEIGHT;
	}

	protected int GetWindowWidth() {
		return window_width;
	}
	protected int GetWindowHeight() {
		return window_height;
	}

	public void SetWindowSize(int width, int height) {
		window_width = width;
		window_height = height;
	}
}
