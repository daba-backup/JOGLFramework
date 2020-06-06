package com.github.dabasan.joglf.gl;

import com.github.dabasan.joglf.gl.window.JOGLFWindow;

public class ConstructorTestMain {
	public static void main(String[] args) {
		new ConstructorTestMain();
	}
	public ConstructorTestMain() {
		var window = new JOGLFWindow(1280, 720, "Window", true);
		window.SetExitProcessWhenDestroyed();
		var window2 = new JOGLFWindow(1280, 720, "Window 2", false);
		window2.ShowWindow();
	}
}
